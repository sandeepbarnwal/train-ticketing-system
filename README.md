# Train ticket booking System

A springboot backend ticket booking application. 

## Features

- Book a train ticket
- Cancel a train ticket
- View receipt/ticket details
- View passengers and seats arrangement in a given section of a train
- Modify passenger's seat on request

## Languages and frameworks

- Java 17
- Spring Boot 3.3.0
- MySQl/H2

## Prerequisites

- Java 17
- Gradle (wrapped with project)
- MySQL/H2 (H2 is embedded)

## API Endpoints

### Book Ticket

- **URL:** `/api/bookings/`
- **Method:** `POST`
- **Description:** create a ticket booking.
- **Request Body:**
  ```json
  {
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

- **Response Body:**
  ```json
  {
    "id": 3,
    "bookingTime": "2024-07-18T00:26:38.47342",
    "pricePaid": 20.0,
    "tickets": [
        {
            "id": 3,
            "passengerFirstName": "Sandeep",
            "passengerLastName": "Kumar",
            "departureTime": "2024-07-17T08:30:00",
            "pnr": "9BMQKUX7",
            "trainCode": "fln-expr",
            "sectionName": "fln-A",
            "seatNumber": "A3",
            "startStop": "London",
            "endStop": "France"
        }
    ]
  } 

### Cancel Ticket
- **URL:** `/api/bookings/cancel/{pnr}`
- **Method:** `POST`
- **Description:** cancel a ticket booking.
- **Request Body:**
- **Response Body:**
  ```json
  {
  "pnr" : "7SBDMTI5",
  "status" : "cancelled"
  }


### View Receipt Details for a single ticket
- **URL:** `/api/tickets/{pnr}`
- **Method:** `POST`
- **Description:** create a ticket booking.
- **Request Body:**
- **Response Body:**
  ```json
  {
    "id": 2,
    "passengerFirstName": "Sandeep",
    "passengerLastName": "Kumar",
    "departureTime": "2024-07-17T08:30:00",
    "pnr": "7SBDMTI5",
    "trainCode": "fln-expr",
    "sectionName": "fln-A",
    "seatNumber": "A2",
    "startStop": "London",
    "endStop": "France"
  }


### View Passengers by Section
- **URL:** `/api/sections/view/{sectionId}`
- **Method:** `POST`
- **Description:** View passengers and seats arrangement in a given section.
- **Request Body:** 
- **Response Body:**
  ```json
  [
    {
        "seatNumber": "A1",
        "passengerName": ""
    },
    {
        "seatNumber": "A2",
        "passengerName": "Sandeep Kumar"
    },
    {
        "seatNumber": "A3",
        "passengerName": "Sandeep Kumar"
    }
  ]


### Modify seats of a passenger
- **URL:** `/api/tickets/{pnr}/seat/{newSeatNumber}`
- **Method:** `POST`
- **Description:** Changes seat of a passenger if available.
- **Request Body:**
- 
- **Response Body:**
  ```json
  {
    "id": 1,
    "passengerFirstName": "Sandeep",
    "passengerLastName": "Kumar",
    "departureTime": "2024-07-17T08:30:00",
    "pnr": "5SAIKCF2",
    "trainCode": "fln-expr",
    "sectionName": "fln-A",
    "seatNumber": "A1",
    "startStop": "London",
    "endStop": "France"
  }


### TODO
- ** add test cases
- ** add schema.sql for seed data
- ** Integrate swagger for documentation

## Author

- Sandeep Kumar