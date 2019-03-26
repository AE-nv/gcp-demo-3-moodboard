# Deploying to GKE

* Build the jar: 
    ```bash
    mvn clean install
    ```
* Build the Docker image and give it the correct tag
    ```bash
    docker build -t gcr.io/message-board-c2da6/moodboard:v1.2 .
    ```
* Push the image to the gcp docker registry
    ```bash
    gcloud docker -- push gcr.io/message-board-c2da6/moodboard:v1.2
    ```
* Change the version in deployment/deployment.yaml
* Create the deployment in Kubernetes + Externalise the service
    ```bash
    kubectl apply -f deployment/deployment.yaml
    kubectl apply -f deployment/service.yaml
    ```
* Get the correct external ip
    ```bash
    kubectl get service moodboard
    ```
