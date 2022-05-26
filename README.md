# Pectus Expanses APIs

---

## Technical specifications

* Java 17
* spring boot 2.7.0
* H2 DB
* lombok

---

## APIs

#### request to get Filtered and or sorted set of fields:

    localhost:8090/api/v1/expanses?filter=memberName=Matt,amount>=1400,date>2021-05-23,id!=12&sort=id:desc

* the previous request will retrieve all expanses with:
    * member name equals Matt
    * amount greater than or equal 1400
    * date after 2021-05-23
    * id not equal 12
* empty or null filter retrieves all expanses.
* empty or null sort retrieves unsorted data.
* filtering with invalid field throws: InvalidFilterException with https status code 400
* sorting with invalid field throws: PropertyReferenceException with https status code 400

### request to get an expanse with a specific Id with a sparse field set

    localhost:8090/api/v1/expanses/2?fields=memberName,department

* the previous request retrieves an expanse with id 2 showing only memeberName and department fields
* Empty or null fields, retrieves an expanse with all fields
* invalid fields retrieves empty response
* invalid id throws: ExpanseNotFoundException with https status code 400

### request to get sum of amount grouping by a specific field

    localhost:8090/api/v1/expanses/amount-sum?groupBy=memberName

* the previous request shows each unique member name and the som of its amounts
* invalid groupBy Field throws: InvalidGroupByFieldException with https status code 400

## Docker

the following commands could be used to containerize and run the API using Docker
-please consider running them on the same directory of the Dockerfile-

* mvn clean install
* docker build -t expanses-api-image .
* docker run -p 8090:8090 expanses-api-image
* open  http://localhost:8090/api/v1/expanses on a browser to call the APIs!
