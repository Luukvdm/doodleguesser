# DoodleGuesser

I created this game for a school assignment. The goal of the project was to learn more about JavaFX and to split up the application into multiple components that communicate in different ways.

The words used in the game are taken from Googles Quickdraw dataset. So that its possible to train a machine learning model to recognise the drawings. 

## Screenshots

![alt text](/screenshots/image1.png)     
     
![alt text](/screenshots/image2.png)

## Note

The rest auth server is in no way secure and is only here because for I needed a REST Api for the assignment. It also needs a MSSQL database to function. So if you want to actually use this, you should probably either remove the auth server or improve it.

## Usage

You can build the project using Maven. It will generate a .jar file for every module. You only need the RestAuthServer, SocketGameServer and the client jars to run and play the game.   
Doing a clean install might take a while sinds it needs to download a bunch of dependencies. It will also generate a report wich can be used by Jenkins.    


```
mvn clean install
```

**Before you build** make sure you add a config.properties file in the resource folder in the RestAuthServer with you connection string. It should look something like this:
```
ConnectionString=jdbc:sqlserver://server_url; database=db_name; user=db_user; password=user_password
```
