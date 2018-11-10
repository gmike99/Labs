
// newsTitle, newsDescription, newsText

window.indexedDB = window.indexedDB || window.mozIndexedDB || 
    window.webkitIndexedDB || window.msIndexedDB;
/*
if (!window.indexedDB) {
    alert();
}*/

let         db,
tx,
store,
index;

function openIndexedDB() {

    let request = window.indexedDB.open("OGDatabase", 1);


    request.onupgradeneeded = function(e) {
        let db = request.result,
            store = db.createObjectStore("NewsStore", {
                autoIncrement: true 
            }),
            index = store.createIndex("newsTitle", "newsTitle", {unique: true});
    };

    request.onerror= function(e) {
        console.log("There was an error:  " + e.target.errorCode);
    };

    request.onsuccess = function(e) {
        db = request.result;
        tx = db.transaction("NewsStore", "readwrite");
        store = tx.objectStore("NewsStore");
        index = store.index("newsTitle");

        db.onerror = function(e) {
            console.log("ERROR" + e.target.errorCode);
        }

        // store.put({newsTitle: 'OG won again', 
        // newsDescription: 'That was not an easy victory',
        // newsText: 'hijofhjejfokefkvhjeoksikjhdkoksfljrehfhijkl'
        // });

        // store.put({newsTitle: 'OG won', 
        // newsDescription: 'That was an easy victory',
        // newsText: 'hijofhjejfokefknoboljhdkoksfljrehfhijkl'
        // });

        // let n1 = store.get(1);
        // let ns = index.get('OG won');

        // n1.onsuccess = function() {
        //     console.log(n1.result);
        //     console.log(n1.result.newsTitle);
        // };

        // ns.onsuccess = function() {
        //     console.log(ns.result.newsTitle);
        // };

        tx.oncomplete = function() {
            db.close();
        };
    };

};

function saveDataToIDB(news) {
    tx = db.transaction("NewsStore", "readwrite");
    store = tx.objectStore("NewsStore");
    console.log(news.newsTitle);
    store.put({
        newsTitle: news.newsTitle,
        newsDescription: news.newsDescription,
        newsText: news.newsText
    });

    tx.oncomplete = function() {
        db.close();
    };
};

function retrieveData(key) {
    return store.get(1);
};
