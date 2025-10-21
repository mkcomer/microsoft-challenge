# Use Microsoft's Java development container as base
FROM mcr.microsoft.com/devcontainers/java:1-17-bullseye

# Switch to root to install system packages
USER root

# Update package lists and install essential tools
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    git \
    vim \
    nano \
    htop \
    tree \
    jq \
    zip \
    unzip \
    postgresql-client \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Install Docker CLI (for Docker-in-Docker)
RUN curl -fsSL https://get.docker.com | sh

# Install Docker Compose
RUN curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose \
    && chmod +x /usr/local/bin/docker-compose

# Switch back to the non-root user
USER vscode

# Set up workspace directory
WORKDIR /workspace

# Expose ports for the application
EXPOSE 8080 5432

# Set environment variables
ENV JAVA_HOME=/usr/local/sdkman/candidates/java/current
ENV MAVEN_HOME=/usr/local/sdkman/candidates/maven/current
ENV PATH="${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"