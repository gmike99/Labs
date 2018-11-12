'use strict';

function getNews(news) {
    if (isOnline()) {
        if (arguments.length === 0) {
            news = getItem('news', function (callbackNews) {
                getNews(callbackNews);
                deleteItem('news');
            });
            if (news == undefined) {
                return
            } else {
                createNewsInstance(news);
                deleteItem('news');
            }
        } else {
            createNewsInstance(news);
        }
    }
}

let newsContainer = document.getElementsByClassName('news-container')[0];
getNews();

function createNewsInstance(news) {
  newsContainer.innerHTML += " <div class=\"col-lg-3 col-md-4 col-sm-6 col-12\">\n" +
        "<div class=\"news-instance\">\n" +
        "<div class=\"news-image\">\n" +
        "<img src=\"img/history.jpg\" alt=\"news illustration\">\n" +
        "</div>\n" +
        "<h2>"+ news.titleText + "</h2>\n" +
        "<p>" + news.descText +"</p>\n" +
        "</div>\n" +
        "</div>";
}
