# CDI Camel Example
A simple project that shows how to use **Apache Camel** *transactional* routes with **CDI** and **JPA**.  
In addition it contains a rest service that puts messages to the queue which the camel route consumes.
The project's setup is for **Wildfly 8.2** and **JDK 8**.

It contains one route,that is transacted and consumes from the queue *"myQueue"*. It does a dublication with a Memory Idempotent and if the message is not duplicate it calls a processor that creates and persists a simple domain object.

The rest interfaces exposes a PUT method at: "/CdiCamelExample/rest/msg" and expects a message body and a UNIQUE_ID header param.