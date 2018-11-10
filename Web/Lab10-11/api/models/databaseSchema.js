'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var FansSchema = new Schema({
    fanCommentText: {
        type: String
    }
});
var NewsSchema = new Schema({
    titleText: {
        type: String
    },
    descText: {
        type: String
    },
    newsText: {
        type: String
    }
});

module.exports = mongoose.model('Fans', FansSchema);
module.exports = mongoose.model('News', NewsSchema);
