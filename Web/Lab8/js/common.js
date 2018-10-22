'use strict'
function isOnline() {
    return window.navigator.onLine;
};

var input = document.getElementById('comment-text-input');
var commentSection = document.querySelector('.main .col-12');

function populateComment(commentTxt) {
    console.log('on click happened');   
    input = document.getElementById('comment-text-input');
    if (commentTxt === null) {
        console.log('commentText == null');
        fanCommentText = input.value;
    } else {
        console.log('commentText != null');
        fanCommentText = commentTxt
    };
    
    if (fanCommentText === '') {
        console.log('invalid text');
        input.style.border = '1px solid red';
    } else {

        if (isOnline()) {
            console.log('is online');

            //selecting root element
            var newDiv = document.createElement('div');
            newDiv.className = 'fan-comment';

            //creating comment text
            var commentText = document.createElement('p');
            commentText.className = 'comment-text';
            var textNode = document.createTextNode(fanCommentText);
            commentText.appendChild(textNode);
            newDiv.appendChild(commentText);

            //creating username
            var username = document.createElement('p');
            username.className = 'username'
            textNode = document.createTextNode('-Username');
            username.appendChild(textNode);
            newDiv.appendChild(username);

            // creating date
            var commentDate = document.createElement('p')
            commentDate.className = 'comment-date';
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1;
            var yyyy = today.getFullYear();

            var hours = today.getHours();
            var minutes = today.getMinutes();

            if (dd < 10) {
                dd = '0' + dd
            }

            if (mm < 10) {
                mm = '0' + mm
            }

            today = mm + '.' + dd + '.' + yyyy + ' ' + hours + ':' + minutes;
            textNode = document.createTextNode(today);
            commentDate.appendChild(textNode);
            newDiv.appendChild(commentDate);

            commentSection.appendChild(newDiv);
        } else {
            console.log('save data');
            saveData(fanCommentText);
        };
        
        console.log('simple else');
        

        input.value = '';
        input.style.border = '1px solid black';
    };
};
// var clickButton = document.getElementById('sbm-button');
// clickButton.addEventListener("click", populateComment(), fals);

function saveData(obj) {
    console.log('save data inside');
    localStorage.setItem('comments', JSON.stringify(obj));
    var retrievedObject = JSON.parse(localStorage.getItem('comments'));
    console.log('saved to local storage');
    console.log('comments: ', retrievedObject);
};  

function sendToServer(obj) {
    console.log(obj + ' saved in server!');
};

window.addEventListener('load', function() {

    function updateOnlineStatus(event) {
        // add logic
        if (event.type === 'online') {
            if (localStorage.getItem('comments') === null) {
                console.log('no item to load')
            } else {
                var unsavedItem = localStorage.getItem('comments');
                populateComment(unsavedItem);
                sendToServer(unsavedItem)
                localStorage.removeItem('comments')
            }
        } else {
            console.log('You are offline now!');
        };
    }
  
    window.addEventListener('online',  updateOnlineStatus);
    window.addEventListener('offline', updateOnlineStatus);
});


var burger = document.getElementById('burger');
var navigation = document.getElementsByTagName('nav')[0];

burger.onclick = function () {
    if (navigation.classList.contains('hidden')) {
        navigation.classList.remove('hidden');
    } else {
        navigation.classList.add('hidden');
    }
};





var fanCommentText = null;



