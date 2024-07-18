 ```console
curl --location 'http://localhost:8080/api/trains' \
--header 'Content-Type: application/json' \
--data '{
    "name": "florence-london-express",
    "code": "fln-expr",
    "departureTime": "08:30:00",
    "arrivalTime": "12:45:00"

}'
```
 
```console
curl --location 'http://localhost:8080/api/sections' \
--header 'Content-Type: application/json' \
--data '{
    "name": "fln-A",
    "trainId": "1"
}'
```

```console
curl --location 'http://localhost:8080/api/seats' \
--header 'Content-Type: application/json' \
--data '{
    "number": "A1",
    "sectionId": "1"
}'
```

```console
curl --location 'http://localhost:8080/api/seats' \
--header 'Content-Type: application/json' \
--data '{
    "number": "A2",
    "sectionId": "1"
}'
```


```console
curl --location 'http://localhost:8080/api/bookings' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email": "sandeep.barnwal@outlook.com",
  "firstName": "Sandeep",
  "lastName": "Barnwal",
  "price": 20.0,
  "trainCode": "fln-expr",
  "fromStation": "London",
  "toStation": "France",
  "travelDate": "2024-07-17",
  "passengers": [
    {
      "firstName": "Sandeep",
      "lastName": "Kumar",
      "age": 30
    }
  ]
}
'
```

```console
curl --location 'http://localhost:8080/api/sections/view/1' 
```

```console
curl --location 'http://localhost:8080/api/tickets/7SBDMTI5'
```

```console
curl --location --request PATCH 'http://localhost:8080/api/tickets/5SAIKCF2/seat/A2' \
--data ''
```

```console
curl --location --request DELETE 'http://localhost:8080/api/bookings/cancel/7SBDMTI5' \
--header 'Content-Type: application/json' \
--data '{
    "pnr" : "7SBDMTI5",
    "status" : "cancelled"
}'
```
