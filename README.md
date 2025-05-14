# Chess Game Application

## Overview
A sophisticated multiplayer chess application featuring real-time gameplay, implemented as a full-stack solution with a Java Spring Boot backend and React/TypeScript frontend.

## Features
- Real-time multiplayer chess gameplay
- Advanced move validation system
- Sophisticated king safety checking mechanism
- Piece promotion functionality
- En passant move support
- Real-time move suggestions
- Game state persistence
- Captured pieces tracking
- Player session management

## Tech Stack

### Backend
- Java 21
- Spring Boot
- WebSocket (STOMP)
- JUnit testing framework
- Maven build system

### Frontend
- React 19.1.0
- TypeScript 5.8.3
- SockJS/STOMP for WebSocket communication
- Zustand for state management
- Vite 6.3.5 build tool

## Project Architecture

### Backend Structure
- **API Layer** 
  - REST Controllers
  - WebSocket Controllers
  - DTO Models
  - Session Management
  
- **Core Game Engine** 
  - Board Management
  - Move Validation
  - Game State Handling
  - King Safety Checking

- **Testing** 
  - Unit Tests
  - Controller Tests
  - Game Logic Tests

### Frontend Structure
- **API Integration** 
  - REST Client
  - WebSocket Client
  
- **Game Components**
  - Board Visualization
  - Piece Movement
  - Game State Management
  - Player Interface

## Technical Features
- Reactive WebSocket communication
- Session-based multiplayer support
- Comprehensive move validation system
- Object-oriented chess engine design
- Modular component architecture
- Type-safe communication with DTOs

## License
This project is licensed under the MIT License.
