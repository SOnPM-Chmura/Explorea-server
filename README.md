# Explorea-server

# Dokumentacja API

# Routes:


* get all routes:

`GET https://explorea-server.azurewebsites.net/routes`



* or filtered routes:

`GET https://explorea-server.azurewebsites.net/routes?city=CITY&time=CZAS&transport=TRANSPORT`

CITY - string nazwa miasta

CZAS - int czas w minutach

TRANSPORT - string “foot” albo “bike”

(żeby przefiltrowało po czasie muszą być oba parametry czas i transport, jeśli nie ma jednego z nich, albo jest w złym formacie to zapytanie zwraca wszystkie rekordy bez filtrowania )


Response:

Http Status Code 200

```
[
   {
       "id": 1,
       "codedRoute": "7366737d487b727c7e427665416f6343746f43727140",
       "avgRating": 4.5,
       "lengthByFoot": 10000,
       "lengthByBike": 11000,
       "timeByFoot": 100,
       "timeByBike": 120,
    “city”: “Łódź” 
   },
   {
       "id": 2,
       "codedRoute": "7366737d487b727c7e427665416f6343746f43727041",
       "avgRating": 3.8,
       "lengthByFoot": 9000,
       "lengthByBike": 7000,
       "timeByFoot": 102,
       "timeByBike": 77,
    “city”: “Łódź” 
   }
]
```



* get route with given id:

`GET https://explorea-server.azurewebsites.net/routes/ID`

ID - int 

Response:

Http Status Code 200

```
{
   "id": 1,
   "codedRoute": "7366737d487b727c7e427665416f6343746f43727140",
   "avgRating": null,
   "lengthByFoot": 10000,
   "lengthByBike": 11000,
   "timeByFoot": 100,
   "timeByBike": 120,
   “city”: “Łódź” 
}
```



* add route 

`POST https://explorea-server.azurewebsites.net/routes`

Header: authentication -  string “Bearer <token>”

Body:

```
{
    "codedRoute":"7366737d487b727c7e427665416f6343746f43727140",
    "lengthByFoot":6666,
    "lengthByBike":6006,
    "timeByFoot":21,
    "timeByBike":3,
    "city": "Łódź" 
}
```
Response:

Http Status Code 200

Body:

```
{
    "id": 7
}
```



* get routes created by user:

`GET https://explorea-server.azurewebsites.net/routes/created`

Header: authentication -  string “Bearer <token>”

Response:

Http Status Code 200

```
[
   {
       "id": 1,
       "codedRoute": "7366737d487b727c7e427665416f6343746f43727140",
       "avgRating": 4.5,
       "lengthByFoot": 10000,
       "lengthByBike": 11000,
       "timeByFoot": 100,
       "timeByBike": 120,
    “city”: “Łódź”
   },
   {
       "id": 2,
       "codedRoute": "7366737d487b727c7e427665416f6343746f43727140",
       "avgRating": 3.8,
       "lengthByFoot": 9000,
       "lengthByBike": 7000,
       "timeByFoot": 102,
       "timeByBike": 77,
    “city”: “Łódź”
   }
]
```



* get user’s favorite routes:

`GET https://explorea-server.azurewebsites.net/routes/favorite`

Header: authentication -  string “Bearer <token>”
 
 Response:
 
 jak dla /routes/created



# Users:


* add user

`POST https://explorea-server.azurewebsites.net/users`

Header: authentication -  string “Bearer <token>”

Response: 

Http Status Code 200

Body:

```
{
    "id": 7
}
```



# Ratings:


* add rating:

`POST https://explorea-server.azurewebsites.net/ratings`

Header: authentication -  string “Bearer <token>”
   
Body:

```
{
    "routeId":1,
    "rating":3
}
```
Response:

Http Status Code 200

Body:

```
{
    "id": 7
}
```



* get user’s rating for given route:

`GET https://explorea-server.azurewebsites.net/ratings?routeId=ROUTEID`

ROUTEID - int

Header: authentication -  string “Bearer <token>”

Response:

Http Status Code 200

```
{
    "routeId":1,
    "rating":3
}
```



# Directions Api:

`GET https://explorea-server.azurewebsites.net/routes/directionsApi?encodedRoute=ENCODED-ROUTE`

ENCODED-ROUTE - trasa zakodowana w hex

Response:

Http Status Code 200

```
{
    "encodedRoute": "5f736470485f676179425f7460423f3f5f7460427e7360423f",
    "queryTime": 1576441220793,
    "encodedDirectionsFoot": "6b736470485f656179424e76496d49634d6f4973515f41615a794e737d40795e776640714b7b57754e73415f4b644179407d4e61417958654c61566f4a695379467454795b7746736c4071...e65694070604177704174764071406277426740786541675f40747c415f53686e406363407e5c675a64467640785064734070517e406457665b607140666e41607d40606a407c61416869416c5c645b744c7276406c6a406e75426662417e7c426a5b7660407858627140726340786f406c50685b70586c4b704a6458",
    "encodedDirectionsBike": "6b736470485f656179424e76496d4...705d68466078407a6940606241724f5f4a606a406f7b40685e5f6a406052785a605b7e4c7a5b6e507668407b407a4c7748605966664078526f40767940605f4064577c6940765e6e556a6c417045666140785f407a634078724172407e5f407e496053744c66557e537a73407a4e7a526265405f417e5378496c4b76556e476a546a7840695376694066417c586e50767d407a6a406c526c56705073477a5a6c6c407c4a78576e53606240784266536e5560406e7a407964406a4f765b",
    "distanceFoot": 182541,
    "distanceBike": 193061,
    "timeFoot": 2232,
    "timeBike": 617,
    "bounds": {
        "northEast": {
            "lat": 49.9969778,
            "lng": 19.9915761
        },
        "southWest": {
            "lat": 50.5322141,
            "lng": 20.6311855
        }
    }
}
```
