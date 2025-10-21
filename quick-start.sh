#!/bin/bash

echo "🚀 Microsoft Challenge - Quick Start Guide"
echo ""
echo "Available commands:"
echo ""
echo "1. Build the Taxi API:"
echo "   mvn clean install -P build-docker -pl taxi-api"
echo ""
echo "2. Start services (Postgres + API):"
echo "   docker-compose up"
echo ""
echo "3. Check if API is running:"
echo "   curl http://localhost:8080/taxi-api/_healthcheck"
echo ""
echo "4. Populate test data (in another terminal):"
echo "   cd testdata && python3 populateDb.py"
echo ""
echo "5. Run API tests:"
echo "   cd testdata && python3 testAPI.py"
echo ""
echo "6. View Docker images:"
echo "   docker images"
echo ""
echo "7. View running containers:"
echo "   docker ps"
echo ""

case "$1" in
    "build")
        echo "🔨 Building Taxi API..."
        mvn clean install -P build-docker -pl taxi-api
        ;;
    "start")
        echo "🚀 Starting services..."
        docker-compose up
        ;;
    "test")
        echo "🧪 Running tests..."
        cd testdata && python3 testAPI.py
        ;;
    "populate")
        echo "📊 Populating database..."
        cd testdata && python3 populateDb.py
        ;;
    "health")
        echo "🏥 Checking API health..."
        curl -s http://localhost:8080/taxi-api/_healthcheck || echo "❌ API not responding"
        ;;
    *)
        echo "Usage: ./quick-start.sh [build|start|test|populate|health]"
        ;;
esac
