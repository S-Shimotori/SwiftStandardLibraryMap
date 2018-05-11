# Swift Standard Library Map

Draw type relations in Swift Standard Library

## Challenge

* Use languages and libraries on JVM
    * [Gradle](https://gradle.org/)
        * DO NOT use IntelliJ's auto generating
    * [Mockito](http://site.mockito.org/)
    * [~~Lombok~~](https://projectlombok.org/)
        * cannot be used with Kotlin
    * [SLF4J](https://www.slf4j.org/)
        * [kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
        * [Logback](https://logback.qos.ch/)
* Use Graph DB
    * [Neo4j](https://neo4j.com/)
        * [library/neo4j - Docker Hub](https://hub.docker.com/_/neo4j/)
* Use cache
    * [Store](https://github.com/NYTimes/Store)

### Neo4j

```sh
docker run \
    --publish=7474:7474 --publish=7687:7687 \
    --volume=$HOME/neo4j/data:/data \
    --volume=$HOME/neo4j/logs:/logs \
    neo4j
```

then access

* http://localhost:7474/
* bolt://localhost:7687/
