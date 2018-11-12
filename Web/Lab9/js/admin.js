'use strict';

console.log('this is admin');

function saveNews(obj) {
    if(isOnline()) {

    } else {
        addItem('news', obj);
    }
};  


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
            saveNews(newsObj);
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