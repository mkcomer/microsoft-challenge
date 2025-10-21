#!/bin/bash

echo "🚀 Setting up Microsoft Challenge development environment..."

# Make sure we're in the workspace directory
cd /workspaces/microsoft-challenge || cd /workspace || { echo "Failed to navigate to workspace"; exit 1; }

# Install Python packages if pip is available
if command -v pip3 >/dev/null 2>&1; then
    echo "📦 Installing Python packages..."
    pip3 install --user pandas psycopg2-binary sqlalchemy requests pytest black flake8
elif command -v pip >/dev/null 2>&1; then
    echo "📦 Installing Python packages..."
    pip install --user pandas psycopg2-binary sqlalchemy requests pytest black flake8
else
    echo "⚠️  pip not found, Python packages not installed"
fi

# Display project information
echo "📋 Project: Microsoft Challenge - Taxi API"
echo "🛠️  Tools: Java 17, Maven, Docker, Python, PostgreSQL"
echo ""

# Check Java and Maven versions
echo "☕ Java version:"
java -version
echo ""

echo "📦 Maven version:"
mvn -version
echo ""

echo "🐍 Python version and packages:"
python3 --version
pip list | grep -E "(pandas|psycopg2|sqlalchemy|requests)"
echo ""

echo "🐳 Docker version:"
docker --version
docker-compose --version
echo ""

# Set up git configuration if not already set
if [ -z "$(git config --global user.name)" ]; then
    echo "⚠️  Git user not configured. You may want to run:"
    echo "   git config --global user.name 'Your Name'"
    echo "   git config --global user.email 'your.email@example.com'"
    echo ""
fi

# Make scripts executable
if [ -f "testdata/populateDb.py" ]; then
    chmod +x testdata/populateDb.py
fi

if [ -f "testdata/testAPI.py" ]; then
    chmod +x testdata/testAPI.py
fi

# Create a helpful script for common tasks
cat > quick-start.sh << 'EOF'
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
EOF

chmod +x quick-start.sh

echo "✅ Development environment setup complete!"
echo ""
echo "📚 Next steps:"
echo "   1. Run './quick-start.sh build' to build the API"
echo "   2. Run './quick-start.sh start' to start services"
echo "   3. Run './quick-start.sh populate' to add test data"
echo "   4. Run './quick-start.sh test' to run tests"
echo ""
echo "   Or run './quick-start.sh' to see all available commands"
echo ""
echo "🌐 The API will be available at: http://localhost:8080/taxi-api/"
echo "🗄️  PostgreSQL will be available at: localhost:5432"
echo ""