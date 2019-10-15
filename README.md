# Explorea-server

Funkcje:

  -  komunikacja z bazą
  -  komunikacja z Routes API
  -  obróbka danych
  -  zwracanie danych przez rest api w json
  -  odbieranie danych z apki klienckiej

# API

* Podstawowa komunikacja klient-serwer (WIP):

`GET https://explorea-server.azurewebsites.net/greeting?name=PARAM`
PARAM - string name

   Response:
```
{
   "message": "Hello PARAM! Server time is: Tue Oct 15 19:07:50 GMT 2019!"
}
```
