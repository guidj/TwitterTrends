#Twitter Storm

An app to analyse trending topics (hashtags) on twitter


#Execution

First, we need to configure our twitter API connection parameters in a properties file, `config.properties`:

```
ApiKey=secretApiKey
ApiSecret=secretApiSecret
Token=secretToken
TokenSecret=tokenSecret
StreamEndpoint=https://stream.twitter.com/1.1/statuses/sample.json
```

We also config app specific parameters:

```
waitTime=1000

```

Once you have done that, make the `startTwitterApp.sh` shell script executable:

```sh
chmod +x ${PROJECT_PATH}/appassembler/bin/startTwitterApp.sh
```

Then, just call the script with the required parameters:

```
${PROJECT_PATH}/appassembler/bin/startTwitterApp.sh mode apiKey apiSecret tokenValue tokenSecret kafkaBroker filepath
```

#Twitter Stream
##JSON
A sample JSON of a created tweet looks like this:

```
{
"created_at": "Tue Dec 08 13:06:08 +0000 2015",
  "id": 674213371353370600,
  "id_str": "674213371353370624",
  "text": "RT @kathdesal: KATHNIEL FANS RIGHT NOW: \n\n* hindi na yun panaginip huhu *\n\nThankYouForTheLove KATHNIEL // #PSYAwkward https://t.co/q6fzC77b…",

...

"entities": {
      "hashtags": [
        {
          "text": "PSYAwkward",
          "indices": [
            91,
            102
          ]
        }

},

...

"lang": "tl",
"timestamp_ms": "1449579968666"

}
```

A sample of a deleted Tweet looks like this:

```
{
  "delete": {
    "status": {
      "id": 672384545740705800,
      "id_str": "672384545740705792",
      "user_id": 3315307177,
      "user_id_str": "3315307177"
    },
    "timestamp_ms": "1449579971787"
  }
}
```

##CSV


