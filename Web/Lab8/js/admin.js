'use strict'
function isOnline() {
    return window.navigator.onLine;
};

function saveData(obj) {
    localStorage.setItem('news', JSON.stringify(obj));
    var retrievedObject = JSON.parse(localStorage.getItem('news'));
    console.log('saved to local storage');
    console.log('news: ', retrievedObject);
};  

function sendToServer(obj) {
    console.log(obj + ' saved in server!');
};

window.addEventListener('load', function() {

    function updateOnlineStatus(event) {
        // add logic
        if (event.type === 'online') {
            if (localStorage.getItem('news') === null) {
                console.log('no item to load')
            } else {
                var unsavedItem = localStorage.getItem('news');
                sendToServer(unsavedItem)
                localStorage.removeItem('news')
            }
        } else {

        };
    }
  
    window.addEventListener('online',  updateOnlineStatus);
    window.addEventListener('offline', updateOnlineStatus);
});


// burger stuff -------------------------------------------------
var burger = document.getElementById('burger');
var navigation = document.getElementsByTagName('nav')[0];

burger.onclick = function () {
    if (navigation.classList.contains('hidden')) {
        navigation.classList.remove('hidden');
    } else {
        navigation.classList.add('hidden');
    }
};
// --------------------------------------------------------------


// news submission -------------------------------------------------------
var btn = document.querySelector('.form-div button');
var title = document.getElementById('title');
var description = document.getElementById('description');
let newsBody = document.getElementById('news_body');

btn.onclick = function submitNews() {

    if (title.value.trim() != '' &&
        description.value.trim() != '' &&
        newsBody.value.trim() != '') {

        var titleText = title.value;
        var descText = description.value;
        var newsText = newsBody.value;

    
        var newsObj = {
            'titleText': titleText,
            'descText': descText,
            'newsText': newsText
        };

        if(isOnline()) {
            console.log('online!');
            sendToServer(newsObj);
        } else {
            saveData(newsObj);
            console.log('offline!');
        };
        

        title.value = '';
        description.value = '';
        newsBody.value = '';
        
        title.style.border = '1px solid grey';
        description.style.border = '1px solid grey';
        newsBody.style.border = '1px solid grey'

        alert('News submitted!')
    } else {
        if (title.value.trim() == '') {
            title.style.border = '1px solid red'
        };
        if (description.value.trim() == '') {
            description.style.border = '1px solid red'
        };
        if (newsBody.value.trim() == '') {
            newsBody.style.border = '1px solid red'
        };
    };

};
// ----------------------------------------------------------------------


// garbage

// var input = document.getElementById('comment-text-input');
// var clickButton = document.getElementById('sbm-button');
// const commentSection = document.querySelector('.main .col-12');

