var imgur = require('imgur');
import config from '../config/config';
var exec = require('child_process').exec;
var rb = require('request-promise');
var fs = require('fs');
var logger = require('../logger/logger.js');

var createImgurLinkForDotString = function (request, response) {
    const TEMPORARY_GRAPHVIZ_FILE_PATH = config.general.TEMPORARY_GRAPH_FILE_DIRECTORY + Date.now() + '.png';

    var inputDotString = request.body.text;
    var inputToken = request.body.token;
    var responseURL = request.body.response_url;

    if (inputToken !== config.slack.SLACK_AUTHENTICATION_TOKEN) {
        response.send({
            text: config.messages.SystemMessages.BadTokenFromSlack,
            response_type: config.slack.EPHEMERAL_RESPONSE_TYPE
        });
        return;
    }else if (inputDotString.length > config.general.MAXIMUM_DOT_STRING_LENGTH_NOT_INCLUDING_NEW_LINES) {
        response.send({
            text: config.messages.BusinessMessages.BadDOTLengthFromSlack,
            response_type: config.slack.EPHEMERAL_RESPONSE_TYPE
        });
        return;
    }else {
        response.send({
            text: config.messages.BusinessMessages.ProcessingYourRequest + '\n>>>' + inputDotString,
            response_type: config.slack.EPHEMERAL_RESPONSE_TYPE
        });
    }

    var command = 'echo "' + request.body.text + '" | dot -Tpng -o ' + TEMPORARY_GRAPHVIZ_FILE_PATH;

    exec(command, function (error, stdout, stderr) {
        if (error) {
            logger.error(error, 'Error processing the DOT file');
            rb({
                method: 'POST',
                uri: responseURL,
                body: {
                    text: config.messages.BusinessMessages.BadDOTFormatFromSlack,
                    response_type: config.slack.EPHEMERAL_RESPONSE_TYPE
                },
                json: true
            });
        }else {
            imgur.uploadFile(TEMPORARY_GRAPHVIZ_FILE_PATH)
                .then(function (response) {
                    var link = response.data.link;
                    logger.info('Upload to imgur Succeeded, link: ' + link);
                    rb({
                        method: 'POST',
                        uri: responseURL,
                        body: {
                            text: link,
                            response_type: config.slack.CHANNEL_RESPONSE_TYPE
                        },
                        json: true
                    });
                })
                .catch(function (err) {
                    logger.error(err, 'Error uploading dot file to imgur');
                    rb({
                        method: 'POST',
                        uri: responseURL,
                        body: {
                            text: config.messages.SystemMessages.UnexpectedSystemError,
                            response_type: config.slack.EPHEMERAL_RESPONSE_TYPE
                        },
                        json: true
                    });
                })
                .done(function () {
                    fs.unlink(TEMPORARY_GRAPHVIZ_FILE_PATH, function () {});
                });
        }
    });
};

module.exports = createImgurLinkForDotString;