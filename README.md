# product-services-prototype

## Running

With [sbt](http://www.scala-sbt.org/) installed:

```
sbt run
```

### Running Tests

Run the unit tests with
```
sbt test
```
...and the acceptance tests with
```
sbt it:test
```
Both test suites can be executed with
```
sbt test-all
```

To get coverage reports run
```
sbt clean coverage test
```
or
```
sbt clean coverage it:test
```

Run performance tests with [Taurus](https://gettaurus.org/install/Installation/):
```
bzt performance/taurus.yml
```

### Check style

```
sbt scalastyle
```
...or...
```
sbt test:scalastyle
```
...to run scalastyle on the test code.

## Deployment

## Infrastructure
The CloudFormation infrastructure templates can be found in /infrastructure and created using AWS CLI

## Deployment

### Logging
Logging is configured with [SLF4J](https://www.slf4j.org/) using a [Logback](https://logback.qos.ch) backend. The [logback configuration file for the deployed application](src/main/resources/logback.deploy.xml) uses a [RollingFileAppender](https://logback.qos.ch/manual/appenders.html#RollingFileAppender) which zips up logs every time the log file reaches a certain size and deletes older zip files if too many are created.
