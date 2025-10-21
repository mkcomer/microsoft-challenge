# Dev Container Setup for Microsoft Challenge

This repository now includes a complete dev container configuration that provides a consistent development environment with all the necessary tools pre-installed.

## What's Included

The dev container provides:
- ☕ **Java 17** (upgraded from Java 8)
- 📦 **Maven** (latest version)
- 🐍 **Python 3.11** with required packages:
  - pandas
  - psycopg2-binary
  - sqlalchemy  
  - requests
  - pytest, black, flake8 (development tools)
- 🐳 **Docker & Docker Compose** (Docker-in-Docker enabled)
- 🗄️ **PostgreSQL client tools**
- 🛠️ **VS Code extensions** for Java, Python, Docker, and XML

## Quick Start

### Option 1: Using VS Code (Recommended)

1. **Prerequisites**: Install VS Code and the "Dev Containers" extension
2. **Open in Container**: 
   - Open the project in VS Code
   - When prompted, click "Reopen in Container"
   - Or use Command Palette: `Dev Containers: Reopen in Container`

3. **Build and Run**:
   ```bash
   # The environment will be set up automatically
   # Use the provided quick-start script:
   ./quick-start.sh build    # Build the API
   ./quick-start.sh start    # Start services
   ./quick-start.sh populate # Add test data
   ./quick-start.sh test     # Run tests
   ```

### Option 2: Manual Docker Build

If you prefer to build the dev container manually:

```bash
# Build the dev container
docker build -t microsoft-challenge-dev .devcontainer/

# Run the container
docker run -it --privileged -v /var/run/docker.sock:/var/run/docker.sock \
  -v $(pwd):/workspace -w /workspace \
  -p 8080:8080 -p 5432:5432 \
  microsoft-challenge-dev bash
```

## Available Commands

The dev container includes a `quick-start.sh` script with these commands:

- `./quick-start.sh build` - Build the Taxi API Docker image
- `./quick-start.sh start` - Start PostgreSQL and API services  
- `./quick-start.sh populate` - Populate database with test data
- `./quick-start.sh test` - Run API tests
- `./quick-start.sh health` - Check API health status

## Modernization Notes

This dev container setup includes several modernizations:

### Updated Dependencies
- **PostgreSQL**: Upgraded from 9.6 to 16 (latest stable)
- **Python packages**: Updated to modern versions with security fixes
- **Java**: Upgraded to Java 17 (LTS) from Java 8

### Development Tools Added
- **Testing**: pytest for Python testing
- **Code Quality**: black (formatter) and flake8 (linting)
- **Database Management**: Optional PgAdmin (use `--profile tools`)

### Optional Modern Stack

For new projects, consider using the modern alternatives:
```bash
# Use the modern docker-compose file
docker-compose -f docker-compose.modern.yml up

# Start with PgAdmin for database management
docker-compose -f docker-compose.modern.yml --profile tools up
```

## Port Forwarding

The dev container automatically forwards these ports:
- **8080**: Taxi API service
- **5432**: PostgreSQL database
- **5050**: PgAdmin (when using modern compose with tools profile)

## Accessing Services

- **Taxi API**: http://localhost:8080/taxi-api/
- **Health Check**: http://localhost:8080/taxi-api/_healthcheck
- **PgAdmin**: http://localhost:5050/ (admin@taxi-api.com / admin)

## Troubleshooting

### Container Won't Start
- Ensure Docker is running
- Check that ports 8080 and 5432 aren't already in use
- Verify you have the Dev Containers extension installed

### Build Failures
- Try rebuilding without cache: `Dev Containers: Rebuild Container`
- Check Docker daemon is accessible
- Ensure you have sufficient disk space

### Database Connection Issues
- Wait for PostgreSQL to fully start (check with `docker ps`)
- Verify connection with: `psql -h localhost -U postgres -d taxidatabase`

## Next Steps

1. **Modernize the Java Stack**: Consider upgrading to Spring Boot
2. **Update Dependencies**: Use the latest versions in `pom.xml`
3. **Add Testing**: Implement unit tests with JUnit 5
4. **CI/CD**: Add GitHub Actions workflows
5. **Security**: Run dependency vulnerability scans

The dev container provides a solid foundation for both using the current code and modernizing it step by step.