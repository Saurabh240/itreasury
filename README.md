# Treasury Automation Project

This project is a Treasury Automation system built using Java, Spring Boot, and Gradle. It includes various modules for handling fund positions, cash-flows and compliance trackers, etc.
Figma design : [refer](https://www.figma.com/proto/smSKk9KdqktPmV9h9bEMdq/i-Treasury-by-VITIRA---automating-treasury-operations?node-id=6-1609&p=f&t=PwKyXgkyCPQBzfD3-1&scaling=min-zoom&content-scaling=fixed&page-id=6%3A1096&starting-point-node-id=6%3A1609))

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

## Prerequisites

- Java 17 or higher
- Gradle
- [Docker](https://www.docker.com/products/docker-desktop/) or [Podman](https://podman-desktop.io/) (for running PostgreSQL database)

## Installation

1. Clone the repository:
    ```sh
    git clone git@github.com:Pranjal-Vitira/TreasuryAutomation.git
    cd TreasuryAutomation
    cd itreasury
    ```

2. Build the project using Gradle:
    ```sh
    ./gradlew build
    ```

3. Set up the containerized PostgreSQL database using Docker:
    ```sh
    docker compose up -d
    ```

## Running the Application

1. Start the Spring Boot application:
    ```sh
    ./gradlew bootRun
    ```

2. The application will be available at `http://localhost:8090`.

## Project Structure

- `src/main/java/com/vitira/itreasury` - Main source code
- `src/main/resources` - Configuration files and templates
- `src/test/java/com/vitira/itreasury` - Test source code
- `docker-compose.yml` - Docker Compose file for setting up the PostgreSQL database

## Configuration

The application configuration is located in `src/main/resources/application.properties`. Key configurations include:

- **Mail Configuration**:
    ```ini
    spring.mail.host=imap.gmail.com
    spring.mail.port=993
    spring.mail.username=noreply.vitira@gmail.com
    spring.mail.password=${G_PASS}
    spring.mail.protocol=imaps
    spring.mail.properties.mail.imap.ssl.enable=true
    spring.mail.properties.mail.imap.starttls.enable=true
    spring.mail.properties.mail.imap.auth=true
    spring.mail.properties.mail.debug=true
    ```

- **Database Configuration**:
    ```ini
    spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

## Usage

### Fund Position Service

The `FundPositionService` class provides methods to get the fund position for a company, including the total funds and bank-wise fund details.

### Cash Flow Service

The `CashFlowService` class provides methods to get the cash flow for a company, including the incoming and outgoing cash-flows.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.
