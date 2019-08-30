# SleepMood - Back-End Documentaion

This is the full list of endpoints to connect to the Sleep Mood api.

## Base Url

All endpoints listed below in this documentation require this baseURL:
"

## Login and Registration

### Login

To login with OAuth2, you _must_ include the proper authentication token containing the `CLIENT_ID` and `CLIENT_SECRET` along with your username and password.
The res.body must also be sent as a string. (content-type: "x-www-form-urlencoded")

**Endpoint:** "/login"
**Method:** POST

**Request body:**
If data looks like:

```
user = {
    username: "email", 
    password: "newpassword"
}
```

Then, request body should be a string formatted like:
```
`grant_type=password&username=${user.username}&password=${user.password}`
```

**Response:** `access_token` and `token_type`

### Register

To add a new user, you must have _no_ authentication token in your server request.

**Endpoint:** "/createnewuser"
**Method:** POST

**Request body:**
```
{
    firstname: "John",
    lastname: "Doe",
    email: "john.doe@gmail.com",
    password: "iThinkiAm"
}
```
**Response:** status of 201.
**_Note:_** Creating a user will not automatically login.

## Sleep Data

All the GET, POST, PUT, and DELETE requests revolved around the Sleep Data in the server

**_Note:_**
* The server will only respond with sleep data belonging to the currently logged-in user.
* When given a value associated with a day of the month, the server will compare that value to the `wakedate`.(not the `sleepdate`)

Example of formatted Sleep Data:
```
{
    id: 39,
    sleepdate: [2019, 7, 4, 23, 30],
    wakedate: [2019, 7, 5, 6, 05],
    sleepmood: 5,
    wakemood: 2,
    daymood: 4
}
```

### Endpoints

#### Get all Sleep Data

**Endpoint:** "/sleep/all"
**Method:** GET

**Response:** Returns all sleep data for that user.

#### Get Sleep Data by _Month_

**Endpoint:** "/sleep/month/:year/:month"
**Method:** GET
**Request Params:** year(int), month(int)

**Response:** Returns all sleep data for that user for the requested month.

#### Get Sleep Data by _Week_

**Endpoint:** "/sleep/week/:year/:month/:firstDayOfWeek"
**Method:** GET
**Request Params:** year(int), month(int), firstDayoftheWeek(int)

**Response:** Returns all sleep data for that user- Server returns seven days of data, including the day requested.

#### Get Sleep Data by _Day_

**Endpoint:** "/sleep/day/:year/:month/:day"
**Method:** GET
**Request Params:** year(int), month(int), day(int)

**Response:** Returns the sleep data for that user where the wakedate matches the requested year, month, and day.

#### Get Sleep Data by _Id_

**Endpoint:** "/sleep/id/:sleepDataId"
**Method:** GET
**Request Params:** sleepDataId(long)

**Response:** Returns all sleep data for that user- Server returns seven days of data, including the day requested.

#### Create new Sleep Data

**Endpoint:** "/sleep/new"
**Method:** POST
**Request Body:** 
```
{
    sleepdate: [Year, month, day, hour, minute],
    wakedate: [Year, month, day, hour, minute],
    sleepmood: 5,
    wakemood: 2,
    daymood: 4
}
```

**Response:** Returns a status of 201, with no body.

#### Edit existing Sleep Data

**Endpoint:** "/sleep/update/:sleepDataId"
**Method:** PUT
**Request Params:** sleepDataId(long)
**Request Body:** Any of these values can be missing. As long as there is at least one value, the server will update.
```
{
    sleepdate: [Year, month, day, hour, minute],
    wakedate: [Year, month, day, hour, minute],
    sleepmood: 5,
    wakemood: 2,
    daymood: 4
}
```

**Response:** Returns a status of 200, with no body.

#### Delete Sleep Data

**Endpoint:** "/sleep/delete/:sleepDataId"
**Method:** DELETE
**Request Params:** sleepDataId(long)
**Request Body:** 
```
{
    sleepdate: [Year, month, day, hour, minute],
    wakedate: [Year, month, day, hour, minute],
    sleepmood: 5,
    wakemood: 2,
    daymood: 4
}
```

**Response:** Returns a status of 200, with no body.

