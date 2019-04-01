# axon-replenishment-demo
A Spring Boot application for showing the basic usage of Axon 4.1 for a demo showcase in the domain of Cloud-based Mobile Payment.

### Project Structure
#### [api package](src/main/java/com/netcetera/demo/replenishment/api)
Contains the commands, queries and events used by the application.

#### [domain package](src/main/java/com/netcetera/demo/replenishment/domain)
Contains the domain models for a Token & Replenishment.

#### [infrastructure package](src/main/java/com/netcetera/demo/replenishment/infrastructure)
###### [web](src/main/java/com/netcetera/demo/replenishment/infrastructure/web)
Contains the API controllers for the SDK, Token Service Integrator & Reporting.

###### [persistence](src/main/java/com/netcetera/demo/replenishment/infrastructure/persistence)
Contains the query models for the SDK & Reporting.

###### [logging](src/main/java/com/netcetera/demo/replenishment/infrastructure/logging)
Contains Axon MessageDispatchInterceptor's for logging commands, queries and events dispatched in the by the application.

### Demo
Import the [Axon Replenishment Demo.postman_collection.json](postman/Axon%20Replenishment%20Demo.postman_collection.json) in Postman and execute it via the Collection Runner.

### Additional notes
* Does not use Axon Server
* No data is persisted: InMemoryEventStorageEngine is configured for Axon; query models cache data.
