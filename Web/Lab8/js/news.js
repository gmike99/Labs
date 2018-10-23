'use strict'
var news = null
console.log('this is news');
window.addEventListener('load', function() {
    if (this.localStorage.getItem('news') === null) {
        console.log('local storage is empty');
    } else {
        console.log('local storage is not empty');
        news = JSON.parse(localStorage.getItem('news'));
        var parent = this.document.querySelector('.last-news .row');
        var newArticle = createNewsInstance(news);
        parent.appendChild(newArticle)

    }
});

function createNewsInstance(news) {

    var bootstrapWrapper = document.createElement('div');
    bootstrapWrapper.classList.add('col-lg-3', 'col-md-4', 'col-sm-6', 'col-12');

    // creating root element
    var newDiv = document.createElement('div');
    newDiv.className = 'news-instance';

    // creating div for the image
    var imgDiv = document.createElement('div');
    imgDiv.className = 'news-image';
    // creating image
    var img = document.createElement('img');
    img.src = 'img/history.jpg';
    // adding img to img-div
    imgDiv.appendChild(img);
    newDiv.appendChild(imgDiv);
    // creating header
    var header = document.createElement('h2');
    var txtNode = document.createTextNode(news.titleText);
    header.appendChild(txtNode);
    newDiv.appendChild(header);
    // creating news txt
    var newsText = document.createElement('p');
    txtNode = document.createTextNode(news.newsText);
    newsText.appendChild(txtNode);
    newDiv.appendChild(newsText);

    bootstrapWrapper.appendChild(newDiv);

    return bootstrapWrapper;
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