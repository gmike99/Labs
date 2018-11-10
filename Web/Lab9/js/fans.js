function isOnline() {
    return window.navigator.onLine;
};

function sendToServer(obj) {
    console.log(obj + ' saved in server!');
};

window.addEventListener('load', function() {
    console.log('window event listener');
    function updateOnlineStatus(event) {
        console.log('updateOnline Status');
        // add logic
        if (event.type == 'online') {
            if (localStorage.getItem('comments') == null) {
                console.log('no item to load');
            } else {
                console.log('inside online: about to load')
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





var input = document.getElementById('comment-text-input');
var commentSection = document.querySelector('.main .col-12');

function populateComment(commentTxt) {
    input = document.getElementById('comment-text-input');

    // --------------- fill commentTxt with data ----------------
    if (commentTxt === null) {
        var fanCommentText = input.value;
    } else {
        var fanCommentText = commentTxt
    };
    // ------------------- end fill commentTxt with data -------------------
    

    // ------------------- manipulate saved data -----------------------
    if (fanCommentText.trim() === '') {
        console.log('invalid text');
        input.style.border = '1px solid red';
    } else {
        // ----- show to user if online -------------
        if (window.navigator.onLine) {
            console.log('is online');
            var newDiv = createNewFanComment(fanCommentText);
            commentSection.appendChild(newDiv);
        } else { // save to local storage if offline
            console.log('save data');
            saveData(fanCommentText);
        };
            
        input.value = '';
        input.style.border = '1px solid black';
    };
};

function createNewFanComment(fanCommentText) {
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

    return newDiv;
}

function saveData(obj) {
    localStorage.setItem('comments', JSON.stringify(obj));
    var retrievedObject = JSON.parse(localStorage.getItem('comments'));
    console.log('comments: ', retrievedObject);
};  

