# Flight Scheduler
A flight scheduler app.  
Takes a flight code and returns an available gate.  

Clone the repo  
Run the app


#REST API DOCUMENTATION

##baseURI 
```text
http://localhost:8080/flightScheduler
```
## /GET /flights
Returns the list of all flights.  
### Example response
```json
[
    {
        "id": 1,
        "flight_code": "23323t"
    }
]
```

## POST /flights
Get landing gate for flight_code provided in the request body.
### Example request
```json
{
    "flight_code" : "23323t"
}
```  
### Example response
```json
{
    "id": 1,
    "occupied": "OCCUPIED",
    "time_available": "23:59:00",
    "flight_code": "23323t"
}
```
If there is no gate available it will return an error message.

##/GET /gates  
Returns the list of all gates.

###Example response
```json
[
    {
        "id": 1,
        "occupied": "FREE",
        "time_available": "09:00:00",
        "flight_code": ""
    }
]
```

## /GET /gates/{id}  
Returns the gate for the id specified  
### Example response
```json
{
    "id": 3,
    "occupied": "OCCUPIED",
    "time_available": "12:00:00",
    "flight_code": "123ABC"
}
```
## PUT /gates
Update the gate availability time.
Time should be provided in format "HH:MM:SS".  
Maximum value supported "23:59:59".
##Example request
```json
{
    "id" : 1,
    "time_available" : "23:59:59"
}
```
##Example response
```json
{
    "id": 1,
    "occupied": "FREE",
    "time_available": "23:59:59",
    "flight_code": ""
}
```

## /PUT /gates/freeGate
Make specified gate free.
Provide the gate id in the request body.
###Example request
```json
{
    "id" : "1"
}  
```
###Example response
```json
{
    "id": 1,
    "occupied": "FREE",
    "time_available": "23:59:00",
    "flight_code": ""
}
```


gg :)