import express from "express";
import feed from "./feed-response.json";

const app = express();

const port = process.env.PORT;

app.get('/api/v2/accounts', ( req, res ) => {
    const accounts = {
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
  + '2eb42e49-f275-4019-8707-81a0637e7206', ( req, res ) => {
  res.json(feed);
});

app.listen( port, () => {
  console.log( `server started at http://localhost:${ port }` );
});
