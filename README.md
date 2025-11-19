# Waterball Challenge

learning platform web application.

## Prerequisites

- Docker & Docker Compose

## Quick Start

```bash
docker-compose up --build
```

## Access

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:5000
- **MinIO Console**: http://localhost:9001 (minioadmin/minioadmin)

## Local Development

```bash
# Start database & storage
docker-compose up postgresql storage

# Backend
cd backend
./gradlew bootRun

# Frontend
cd frontend
npm install
npm run dev
```
