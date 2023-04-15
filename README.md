# Agreed Purchase API

This API was developed to assist in the account payment split process.

It is possible to add the participants of a purchase, apply payment rate and discount and then generate the ideal payment amount for each participant.

## Dependencies
* Java 11
* Maven (Build)

## Test

To test the project:

```
mvn test
```

## Run
```
mvn spring-boot:run
```

## Run with jar file
```
mvn clean install 

java -jar ./target/${PROJECT_NAME}.jar
```

## Swagger
```
http://localhost:8082/swagger-ui/
```


To run the project locally with front-end, you need to have [agreed-purchase-ui](https://github.com/isacaguiar/agreed-purchase-ui).

