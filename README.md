# Chess

A real-time multiplayer chess application where two players can play live matches via a shareable invite link, with full chess rule support and move validation.

## Features

- Real-time online multiplayer via WebSockets (STOMP)
- Full rule enforcement: legal move validation, check/checkmate/stalemate detection, castling, en passant, pawn promotion, pin detection
- Color selection when creating a game; second player auto-assigned the opposite color; additional players join as spectators
- Shareable invite links — open a link to join a game directly
- Session persistence — rejoin a game as the same color after closing the browser
- Move hints, captured piece tracking, and last-move highlighting
- Supports multiple concurrent games simultaneously

## Tech Stack

**Frontend:** React, TypeScript, Zustand, dnd-kit, CSS Modules, Vite

**Backend:** Java, Spring Boot, WebSocket (STOMP over SockJS)

