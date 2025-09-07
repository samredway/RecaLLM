# RecaLLM

RecaLLM is a Java/Spring Boot application that augments large language models with short-term memory (STM) and long-term memory (LTM) to improve conversational context. It uses Elasticsearch to store all chat history:  

- **STM**: the full session history is injected into each request along with a summary of the previous session, giving the model complete context of the ongoing conversation. Sessions are managed by the backend and a new session will be created with a compressed summary of the last once the total prompt size reaches a preset limit.

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

Before you can run the app you will need to:
- have a .env file with your OPENAI_API_KEY set in it - required to access the openai api.
- make sure you elasticsearch instance is running and accessable on localhost:9200 (see below).

If the above conditions are satisfied you can navigate to project root and run:

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

## API Usage

### Chat Endpoint

The `/chat` endpoint handles all conversation logic and session management automatically. 

**Request format:**
```bash
curl localhost:8080/chat \
  -X POST \
  -H "Content-type: application/json" \
  -d '{"message": "Hello this is a test message", "userId": "myusername", "sessionId": null}'
```

**Session Management:**
- **First message**: Send `sessionId: null` - the backend will generate a new session ID and return it in the response
- **Subsequent messages**: Use the `sessionId` returned from the previous response
- **Automatic session rotation**: When a session becomes too large (context limit reached), the backend automatically creates a new session and includes the previous session's summary for continuity

**Response format:**
```json
{
  "answer": "AI response text",
  "sessionId": "generated-or-current-session-id"
}
```

The client is not responsible for managing session limits or rotation - the backend handles this transparently to prevent context bloat while maintaining conversation continuity.

## Next steps

- The current STM implementation works well but will likely be refined more as I do more user testing.
- The next feature is a first iteration of LTM, which will require experimentation and more testing.

For more granular detail you can see the Github issues which this project currently uses  as a loose ticket system.
