'use strict';
module.exports = function(app) {
    var controllers = require('./controllers/controller');

    const cors = require('cors');
    const express = require('express');
    app.use(cors());
    app.options('*', cors());

    app.route('/fans')
        .get(controllers.getAllFans)
        .post(controllers.addFans);

    app.route('/news')
        .get(controllers.getAllNews)
        .post(controllers.addNews);

    app.route('/reset_all')
        .delete(controllers.deleteAll);

};