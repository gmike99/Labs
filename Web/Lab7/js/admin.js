'use strict'


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



var btn = document.querySelector('.form-div button');
console.log(btn);
var title = document.getElementById('title');
console.log(title);
var description = document.getElementById('description');
console.log(title);
let newsBody = document.getElementById('news_body');
console.log(newsBody);

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