# Jersey-Security-Basic

Written by Philip A Senger

[philip.a.senger@cngrgroup.com](mailto:philip.a.senger@cngrgroup.com) |
mobile: 0406770664 |
[CV/Resume](http://www.visualcv.com/philipsenger) |
[blog](http://www.apachecommonstipsandtricks.blogspot.com/) |
[LinkedIn](http://au.linkedin.com/in/philipsenger) |
[twitter](http://twitter.com/PSengerDownUndr)

### About

[![Build Status](https://travis-ci.org/psenger/Jersey-Security-Basic.svg?branch=master)](https://travis-ci.org/psenger/Jersey-Security-Basic)

There are many ways you can implement Authentication and Authorization with Jersey. This is the most simplest example I could demo with [Basic access authentication](https://en.wikipedia.org/wiki/Basic_access_authentication). By no means should you drop this code into production.

Additionaly, I have added tomcat with a plugin to the maven pom and created a war goal so this can be deployed as a war to a 2.5 servlet speced container.

### Why

If you are reading this, you may be wondering why I built this project. It is because someone in my Linkedin network asked me how to do this. This was the simplest example I could think of and is a collection of examples I gathered from other resources on the web.

### How

First you will need Java 1.8 installed on your computer.
 
[Install Java 1.8 ](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
 
Next you will need to install Apache Maven. You will NOT need tomcat, as I have installed a Tomcat pluggin.

[Installing Apache Maven](https://maven.apache.org/install.html)
[videos for Apache Maven](https://www.youtube.com/playlist?list=PLTgRMOcmRb3OGBIfqPSZFk0Nn0B4xGZqs)

Then you can run the whole project with Tomcat ( as a plugin ) via the following command line. I have included tomcat 7 in this build so you dont have to download it and install it. This should run with tomcat 8 with some minor tweaks to the web.xml More information on the goals can be found here [tomcat 7 maven goals](http://tomcat.apache.org/maven-plugin-trunk/tomcat7-maven-plugin/plugin-info.html)

```
mvn clean tomcat6:run
```

To get the books a http transaction would look like this. The string aXJvbm1hbjpwYXNzd29yZA== is a base64 encoded ASCII string containing the userid a colon and password. In this case it is "ironman:password"

```
GET http://localhost:8080/books
Accept: application/json
Authorization: Basic aXJvbm1hbjpwYXNzd29yZA==
```
