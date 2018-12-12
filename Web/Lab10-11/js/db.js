'use strict';
var useLocalStorage = true;

function isOnline() {
    return window.navigator.onLine;
}

var InitLS = function () {};
var localStorageProvider = new InitLS();

InitLS.prototype.addObj = function (key, value) {
    localStorage.setItem(key, JSON.stringify(value));
};

InitLS.prototype.getObj = function (key) {
    let item = localStorage.getItem(key);
    item = JSON.parse(item);
    return item;
};

InitLS.prototype.deleteObj = function (key) {
    localStorage.removeItem(key);
};


function InitIDB() {}
var indexedDBProvider = new InitIDB();

function openIndexedDB() {
    var openRequest = indexedDB.open('myDB', 4);
    openRequest.onupgradeneeded = function (event) {
        console.log("Upgrading...");
        var db = event.target.result;
        var objectStore = db.createObjectStore('data', {keyPath: 'id'});
    };
    // openRequest.createObjectStore('fans', {keyPath: 'fans'});
    return openRequest;


}


InitIDB.prototype.addObj = function (key, value) {

    var openDB = openIndexedDB();
    openDB.onsuccess = function() {
        var db = {};
        db.result = openDB.result;
        db.tx = db.result.transaction('data', "readwrite");
        db.store = db.tx.objectStore('data');

        db.store.put({id: key, data: value});
    };

    return true;

};

InitIDB.prototype.getObj = function (key, callback) {

    var openDB = openIndexedDB();
    var db = {};
    openDB.onsuccess = function () {
        db.result = openDB.result;
        db.tx = db.result.transaction("data", "readonly");
        db.store = db.tx.objectStore("data");

        var getData = db.store.get(key);
        getData.onsuccess = function () {
            // console.log(getData.result.data);
            callback(getData.result.data);
        }
    };
};

InitIDB.prototype.deleteObj = function (key) {
    var openDB = openIndexedDB();
    var db = {};
    openDB.onsuccess = function () {
        db.result = openDB.result;
        db.tx = db.result.transaction("data", "readwrite");
        db.store = db.tx.objectStore("data");

        var delData = db.store.delete(key);
        delData.onsuccess = function () {
            console.log("data deleted");
        }
    };
};


function handleConnectionChange(event) {
    if (event.type == "offline") {
        console.log("- connection.");
    }
    if (event.type == "online") {
        console.log("+ connection.");
    }
}

window.addEventListener('online', handleConnectionChange);
window.addEventListener('offline', handleConnectionChange);


function addItem(key, value) {
    if(isOnline()) {
        if(key === 'fans') {
            value = {fanCommentText: value};
        }
        //send to server
        return fetch('http://localhost:3000/'+key, {
            credentials: 'same-origin', // 'include', default: 'omit'
            method: 'POST', // 'GET', 'PUT', 'DELETE', etc.
            body: JSON.stringify(value), // Coordinate the body type with 'Content-Type'
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
        })
            .then(response => response.json())



    } else {
        if (useLocalStorage) {
            localStorageProvider.addObj(key, value);
        } else {
            indexedDBProvider.addObj(key, value);
        }
    }
}

function getItem(key, callback) {
    if (useLocalStorage) {
        return localStorageProvider.getObj(key);
    } else {
        return indexedDBProvider.getObj(key, callback);
    }
}

function deleteItem(key) {

    if (useLocalStorage) {
        localStorageProvider.deleteObj(key);
    } else {
        return indexedDBProvider.deleteObj(key);
    }
}

function fromLocalToServer(key) {
    if(useLocalStorage) {
        var data = localStorageProvider.getObj(key);
        //send
        if(data !== null) {
            addItem(key, data);
        }
        localStorageProvider.deleteObj(key);
    } else {
        indexedDBProvider.getObj(key, function (callbackData) {
            var data = callbackData;
            //send
            addItem(key, data);

            indexedDBProvider.deleteObj(key);
        })
    }
}

function getFromServer(key, callback) {

    fetch('http://localhost:3000/' + key)
        .then(response => response.json())
        .then(data => {
            console.log(data); // Prints result from `response.json()` in getRequest
            callback(data);
        })
        .catch(error => console.error(error))
}