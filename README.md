# Chess

A real-time multiplayer chess application where users can play live matches with full rule support, move validation, and game state persistence—built with a modern full-stack architecture.

<img width="1705" alt="chess" src="https://github.com/user-attachments/assets/f11ff81f-e0da-4097-86f5-b35e837df8e6" />


## Features

- ♟️ Real-time online multiplayer via **WebSockets (STOMP)**
- ✅ Full rule enforcement: move validation, king safety, promotion, en passant
- 💾 Persistent game state with session tracking
- 🔄 Live move suggestions and captured piece tracking
- ⚙️ Object-oriented chess engine with modular design

## Tech Stack

| Frontend              | Backend              | Communication     |
|-----------------------|----------------------|--------------------|
| React 19 + TypeScript | Spring Boot (Java 21) | WebSocket (STOMP)  |
| Zustand + Vite        | JUnit + Maven         | SockJS             |
