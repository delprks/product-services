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

### Check style

```
sbt scalastyle
```
...or...
```
sbt test:scalastyle
```
...to run scalastyle on the test code.

### Logging
Logging is configured with [SLF4J](https://www.slf4j.org/) using a [Logback](https://logback.qos.ch) backend.
