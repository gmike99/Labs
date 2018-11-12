'use strict';
var useLocalStorage = false;

function isOnline() {
    return window.navigator.onLine;
}

var LocalStorageConstructor = function () {};
var localStorageProvider = new LocalStorageConstructor();

LocalStorageConstructor.prototype.addObj = function (key, value) {
    localStorage.setItem(key, JSON.stringify(value));
};

LocalStorageConstructor.prototype.getObj = function (key) {
    let item = localStorage.getItem(key);
    item = JSON.parse(item);
    return item;
};

LocalStorageConstructor.prototype.deleteObj = function (key) {
    localStorage.removeItem(key);
};


function IndexedDBConstructor() {}
var indexedDBProvider = new IndexedDBConstructor();

function openIndexedDB() {
    var openRequest = indexedDB.open('Database', 4);
    openRequest.onupgradeneeded = function (event) {
        console.log("Upgrading...");
        var db = event.target.result;
        var objectStore = db.createObjectStore('data', {keyPath: 'id'});
    };
    return openRequest;


}


IndexedDBConstructor.prototype.addObj = function (key, value) {

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

IndexedDBConstructor.prototype.getObj = function (key, callback) {

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

IndexedDBConstructor.prototype.deleteObj = function (key) {
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
        console.log("Now you are offline");
    }
    if (event.type == "online") {
        console.log("Now you are online");
    }
}

window.addEventListener('online', handleConnectionChange);
window.addEventListener('offline', handleConnectionChange);


function addItem(key, value) {
    if (useLocalStorage) {
        localStorageProvider.addObj(key, value);
    } else {
        indexedDBProvider.addObj(key, value);
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

function sendToServer(obj) {
    console.log(obj + ' saved in server!');
};