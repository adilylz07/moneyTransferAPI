# moneytTransferAPI


Transfer API is a Spring Boot application that provides RESTful APIs for managing customers and accounts, as well as performing money transfers. This project is containerized using Docker and deployed on a Kubernetes cluster.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Building the Application](#building-the-application)
- [Running the Application](#running-the-application)


## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- JDK 11
- Maven
- Docker
- Kubernetes (Minikube or any Kubernetes cluster)
- kubectl
- Postman (for API testing)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/<your-username>/transfer-api.git
    cd transfer-api
    ```

## Building the Application

1. Use Maven to build the Spring Boot application:
    ```sh
    mvn clean install
    ```

## Running the Application

### Running Locally

1. Run the application using Maven:
    ```sh
    mvn spring-boot:run
    ```

### Running with Docker

1. Build the Docker image:
    ```sh
    docker build -t transfer-api:latest .
    ```

2. Tag and push the Docker image to Docker Hub:
    ```sh
    docker tag transfer-api:latest <your-dockerhub-username>/transfer-api:latest
    docker push <your-dockerhub-username>/transfer-api:latest
    ```

### Deploying on Kubernetes

1. Create a `deployment.yaml` file in the root directory of your project with the following content:

    ```yaml
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: transfer-api-deployment
    spec:
      replicas: 3
      selector:
        matchLabels:
          app: transfer-api
      template:
        metadata:
          labels:
            app: transfer-api
        spec:
          containers:
          - name: transfer-api
            image: <your-dockerhub-username>/transfer-api:latest
            ports:
            - containerPort: 8080
    ---
    apiVersion: v1
    kind: Service
    metadata:
      name: transfer-api-service
    spec:
      type: LoadBalancer
      ports:
      - port: 80
        targetPort: 8080
      selector:
        app: transfer-api
    ```

    Replace `<your-dockerhub-username>` with your actual Docker Hub username.

2. Apply the `deployment.yaml` file to your Kubernetes cluster:
    ```sh
    kubectl apply -f deployment.yaml
    ```

3. Verify the deployment:
    ```sh
    kubectl get pods
    kubectl get svc
    ```
