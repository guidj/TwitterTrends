#Twitter Storm

An app to analyse trending topics (hashtags) on twitter


#Execution

First, make the `startTwitterApp.sh` shell script executable:

```sh
chmod +x ${PROJECT_PATH}/appassembler/bin/startTwitterApp.sh
```

Then, just call the script with the required parameters:

```
${PROJECT_PATH}/appassembler/bin/startTwitterApp.sh mode apiKey apiSecret tokenValue tokenSecret kafkaBroker filepath
```
