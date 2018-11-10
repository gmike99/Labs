'use strict';


console.log('this is common');

var input = document.getElementById('comment-text-input');
var commentSection = document.querySelector('.main .col-12');

function createFansInstance(fanCommentText) {
    if(fanCommentText instanceof Object) {
        fanCommentText = fanCommentText.fanCommentText;
    }
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
    username.className = 'username';
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
}

function submitFans() {
    console.log('on click happened');
    input = document.getElementById('comment-text-input');
    var fanCommentText = input.value;
    if (fanCommentText === null) {
        console.log('commentText == null');
        return;
    } else {
        console.log('commentText != null');
    }

    if (fanCommentText === '') {
        console.log('invalid text');
        input.style.border = '1px solid red';
    } else {
        saveFans(fanCommentText);
        getFans(fanCommentText);

        input.value = '';
        input.style.border = '1px solid black';
    }
    ;
};

function getFans(fansToShow) {
    if (isOnline()) {

        fromLocalToServer('fans');
        if (arguments.length === 0) {
            getFromServer('fans', function (fansArr) {
                if (fansArr == undefined) {
                    return
                } else {
                    for(let i = 0; i < fansArr.length; i++) {
                        createFansInstance(fansArr[i]);
                    }
                }
            });

        } else {
            addItem('fans',fansToShow);
            createFansInstance(fansToShow);
        }

    } else {
        addItem('fans', fansToShow);
    }

}

function saveFans(fansToSend) {
    if (isOnline()) {

    } else {
        addItem('fans', fansToSend);
    }
}

getFans();

