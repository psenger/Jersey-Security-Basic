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

### Why

If you are reading this, you may be wondering why I built this project. It is because someone in my Linkedin network asked me how to do this. This was the simplest example I could think of and is a collection of examples I gathered from other resources on the web.

### How

To get the books a http transaction would look like this. The string aXJvbm1hbjpwYXNzd29yZA== is a base64 encoded ASCII string containing the userid a colon and password. In this case it is "ironman:password"

```
GET http://localhost:8080/books
Accept: application/json
Authorization: Basic aXJvbm1hbjpwYXNzd29yZA==
```
