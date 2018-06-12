# xml-weather-client
A client application that will connect to the National Weather Service web site using HTTP and XML and/or SOAP and display current weather conditions. The client process will connect to the server over a socket connection and request weather information for a certain location. The National Weather Service specifies the location using latitude and longitude instead of zip code. The user can enter coordinates into the client program and get a current update for that location. This service is only updated hourly, so one should not request updates at short intervals. We have a manual refresh button that will reconnect and retrieve the information again.
We the following variables on display: 
-	Temperature
-	Humidity
-	12 Hour Probability of Precipitation
-	Cloud Cover Amount
-	Wind Speed
-	Wind Direction
-	Wave Height (output will generate only for coastal cities)

A. Testing & Output
•	Upon running the code, the GUI must ask you to enter the longitudes and latitudes.
•	After inserting the coordinates, GUI must show the latest/updated weather condition.
•	Ex: Arlington, TX coordinates (32.700708, -97.124691) will show everything apart from Wave Height, as it is not a coastal city. For that case, we can consider Corpus Christi, TX coordinates (27.800583, -97.396378). 
 
B. Limitations
•	The service is only updated hourly, so does not provide updates at short intervals. 
•	The application is Web service oriented, hence needs an active internet connection always.
