# kotlin-jetty-jersey-example

A minimal working example of a RESTful web service, written in Kotlin, using Jersey and an embedded Jetty server.

## running

    ./gradlew run

or

    ./gradlew installDist
    build/install/kotlin-jetty-jersey-example/bin/kotlin-jetty-jersey-example

## usage

    $ curl localhost:8080/myresource
    {
      "id" : 1,
      "msg" : "Got it!",
      "date" : "2016-06-15T18:28:30.621+0000"
    }

    $ curl localhost:8080/myresource/reverse -d'{"id": 42, "msg": "panama", "date": "1984-04-14T20:42:00.000+0500"}' -H'Content-Type:application/json'
    {
      "id" : 42,
      "msg" : "amanap",
      "date" : "1984-04-14T15:42:00.000+0000"
    }

## gradle

The default build file is currently build.gradle.kts, written in Kotlin. To use the groovy file, add `-b build.gradle`
to gradle commands.
