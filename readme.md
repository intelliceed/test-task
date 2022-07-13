# Hello dear interviewer
This is interview task application.

> **_NOTE:_**
Before start please ensure you have Maven and Docker installed on your computer.

To run application please use script
```shell script
start.sh
```
Script will do everything for you.


Please open your browser and navigate to 
```
http://localhost:8080/swagger-ui.html
```
You will find there all documentation about:
 - end points 
 - data model

To connect to WebSocket please connect to
```
ws://localhost:8080/events
```
In case you want to receive updates for concrete event please connect to 
```
ws://localhost:8080/events/{id} (ex. ws://localhost:8080/events/1)
```

You can use application with any tool supporting HTTP requests (e.g. POSTMAN)