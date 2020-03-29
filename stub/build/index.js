"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var express_1 = __importDefault(require("express"));
var feed_response_json_1 = __importDefault(require("./feed-response.json"));
var app = express_1.default();
var port = process.env.PORT;
app.get('/api/v2/accounts', function (req, res) {
    var accounts = {
        "accounts": [
            {
                "accountUid": "ac82f660-5442-4b78-9038-2b72b1206390",
                "defaultCategory": "2eb42e49-f275-4019-8707-81a0637e7206",
                "currency": "GBP",
                "createdAt": "2020-03-22T16:21:00.156Z"
            }
        ]
    };
    res.json(accounts);
});
app.get('/api/v2/feed/account/ac82f660-5442-4b78-9038-2b72b1206390/category/'
    + '2eb42e49-f275-4019-8707-81a0637e7206', function (req, res) {
    res.json(feed_response_json_1.default);
});
app.listen(port, function () {
    console.log("server started at http://localhost:" + port);
});
//# sourceMappingURL=index.js.map