# RecaLLM

RecaLLM is a Java/Spring Boot application that augments large language models with short-term memory (STM) and long-term memory (LTM) to improve conversational context. It uses Elasticsearch to store all chat history:  

- **STM**: the full session history is injected into each request, giving the model complete context of the ongoing conversation.  
- **LTM**: older memories are semantically searched and the most relevant entries are added to the prompt.  

This project began as coursework for my postgraduate module in Software Development (M813, Open University), but I’m continuing it as a personal project because I see room to improve how chat systems manage memory and this is something that I would like to use myself!

## Tech stack

- Java 17  
- Spring Boot  
- Elasticsearch  
- Docker / Docker Compose  
- Maven

## Architecture

The app follows a standard layered Spring architecture (controllers, services, repositories).  
It also applies the **strategy pattern** via a common `ChatService` interface injected at runtime, making it easy to swap different LLM providers (currently a GPT-4o implementation).

## Run the app

To start the backend app:

```bash
./mvnw spring-boot:run
```

## Start Elasticsearch

You will need to have Docker installed and running, then you can simply do:

```bash
docker compose up elasticsearch
```

## Run the tests

To run the tests:

```bash
./mvnw test
```

The test strategy is layered: services and controllers are tested individually with dependencies mocked. There is no full integration test yet; the closest is the `MemoryService` test, which runs against a live Elasticsearch instance. For that test, you must have Elasticsearch running as shown above.

## Test with curl

To fully integration-test you can curl from the terminal, for example:

```bash
curl localhost:8080/chat \
  -X POST \
  -H "Content-type: application/json" \
  -d '{"message": "Hello this is a test message", "userId": "myusername", "sessionId": "session-01"}'
```

You’ll need to handle incrementing `sessionId`s yourself, but this lets you quickly see how the memory behaves.

## Next steps

- The current STM implementation works but will need refining as I do more user testing.
- The next feature is a first iteration of LTM, which will require experimentation and more testing.
- I plan to add a simple front end (likely a React app) to make testing easier and to support personal use.

