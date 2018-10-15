'use strict'

var title = document.getElementById('title');
console.log(title);
var description = document.getElementById('description');
console.log(title);
let newsBody = document.getElementById('news_body');
console.log(newsBody);

var btn = document.querySelector('.form-div button');
console.log(btn);

btn.onclick = function submitNews() {
    if (title.value != '' &&
        description.value != '' &&
        newsBody.value != '') {
        title.value = '';
        description.value = '';
        newsBody.value = '';

        title.style.border = '1px solid grey';
        description.style.border = '1px solid grey';
        newsBody.style.border = '1px solid grey'

        alert('News submitted!')
    } else {
        if (title.value == '') {
            title.style.border = '1px solid red'
        };
        if (description.value == '') {
            description.style.border = '1px solid red'
        };
        if (newsBody.value == '') {
            newsBody.style.border = '1px solid red'
        };
    };

};






var burger = document.getElementById('burger');
var navigation = document.getElementsByTagName('nav')[0];

burger.onclick = function () {
    if (navigation.classList.contains('hidden')) {
        navigation.classList.remove('hidden');
    } else {
        navigation.classList.add('hidden');
    }
};


var input = document.getElementById('comment-text-input');
var clickButton = document.getElementById('sbm-button');
const commentSection = document.querySelector('.main .col-12');

var fanCommentText = null
clickButton.onclick = function getText() {
    fanCommentText = input.value;
    if (fanCommentText == '') {
        input.style.border = '1px solid red'

    } else {

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

        input.value = '';
        input.style.border = '1px solid black'
    };
};