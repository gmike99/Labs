'use strict';

function getNews(news) {
    if (isOnline()) {

        fromLocalToServer('news');
        if (arguments.length === 0) {
            getFromServer('news', function (newsArr) {
                if (newsArr == undefined) {
                    return
                } else {
                    for (let i = 0; i < newsArr.length; i++) {
                        createNewsInstance(newsArr[i]);
                    }
                }
            });
        } else {
            addItem('news', news);
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
