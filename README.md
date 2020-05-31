# CSV to SQLite DB using Spring Batch

CSV to SQLite batch ingestion leveraging Spring Batch framework.
## Getting Started

There are many ways to implement batch processing, however Spring has the complete toolkit with relates to batch processing. In this context we have one spring batch job that will run the ingestion. 
In the job, we can create multiple steps which are managed by Execution Context.

![Spring Batch Design](https://terasoluna-batch.github.io/guideline/5.0.0.RELEASE/en/Ch02_SpringBatchArchitecture.html)

Inside the step there is the ItemReader, ItemProcessor and ItemWriter interface that you need to implement in your program.

Here are the breakdown:
1. ```ItemReader``` implementation reads the records found in the data.csv.
2. ```ItemProcessor``` implementation contains the business logic for processing before the insertion to database takes place.
3. ```ItemWriter``` implementation contains the logic for inserting to database.

For the Spring application to communicate with the SQLite DB, I added an implementation of the SQL Dialect which can be found in this package ```com.ms3.spring.batch.dialect```.

For static files and ```application.properties``` configuration, please refer to the ````resources```` folder.
### Prerequisites

What things you need to install the software and how to install them

```
JDK 1.8
Maven
SQLite Studio
```

### Setup

In the application.properties file, change the ```path.config``` to where the project is located.
1. Open the project either Eclipse or IntelliJ.
2. Run ```mvn clean install``` to download dependencies
3. Right click on the BatchApplication.java and select Run as Spring Boot Application



### Running the application

Once the setup is done and the application is up and running. Do the following steps:

1. Copy/paste this URL to the browser/Rest Client to run the batch ingestion.
GET ```http://localhost:8080/load```
2. Wait for several minutes to complete the batch job.
3. Once the job is complete, check and verify in the browser/Rest Client this message:
```
// 20200531211053
// http://localhost:8080/load

"COMPLETED"
```

4. You can verify the records either by REST API or using SQLiteStudio.

REST API: 
    GET ```http://localhost:8080/record```

## SQLiteStudio:
1. Open SQLiteStudio.
2. Select Database Menu, then click "Select Add a database".
3. Select browse for existing database file on local computer. 
4. Click OK.  
## Running the tests

In the terminal, run ```mvnw clean test```.

## Assumptions (CSV file)
1. All records must have non empty columns before inserting to database, those records that have empty columns were treated as bad data and will be written to data-bad.csv.
2. Columns H and I were treated as string boolean values for easier implementation.
3. Alphanumeric characters only, characters such as â,€,˜, etc. will be treated as bad data.
## Built With
* [Spring Batch](https://github.com/spring-projects/spring-batch) - Framework for batch processing
* [Spring Data](https://github.com/spring-projects/spring-data-commons) - Spring Data commons
* [Hibernate Validator](https://github.com/hibernate/hibernate-validator) - Hibernate Bean Validation
* [Lombok](http://www.dropwizard.io/1.0.2/docs/) - Used for common data utilities
* [Maven](https://maven.apache.org/) - Dependency Management
* [MapStruct](https://github.com/mapstruct/mapstruct) - An annotation processor for generating type-safe bean mappers 

## Authors

* **David Ramirez** - *Initial work* - [swingfox](https://github.com/swingfox)

