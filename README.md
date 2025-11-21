# telemetry - Full-Stack Observability Solution

This repository provides a full-stack logging and monitoring solution built using Docker Compose. It implements a centralized observability stack by integrating the following core components:

- Loki: Storage for Frontend and Backend application logs.
- Prometheus & cAdvisor: Metrics collection system for gathering container resource usage (CPU/Memory).
- Grafana: Visualization platform for unified querying and display of both logs and metrics.

This setup enables seamless logging from your React frontend and Spring Boot backend directly into the central stack.

## Installation

1. Install the Spring Boot application with Maven:
```bash
mvn clean install
```

2. Set up and start the application with Docker Compose:
```bash
docker-compose up
```


## Usage

The React application is available at [http://localhost:3000](http://localhost:3000). The Grafana dashboard is available at [http://localhost:3001](http://localhost:3001).

### Importing the Grafana Dashboard

To import the Grafana dashboard, follow these steps:

1. Navigate to [Grafana Dashboards](http://localhost:3001/dashboards).

2. Click the "Create dashboard" button and the "Import dashboard" Button.

3. Upload the `grafana_dashboard.json` file from the telemetry repository (use `grafana_dashboard_linux.json` for Linux systems).

4. Click the "Import" Button.

### Customizing CPU and Memory Graphs (Linux)

In the Linux environment, you can customize the CPU and Memory usage graphs in the Grafana dashboard by modifying the Prometheus query. Specifically, the `container_label_com_docker_compose_service` parameter allows you to select which container's resource usage you want to monitor.

For example, to monitor the CPU usage of the "backend" container, modify the query to:

```
rate(container_cpu_usage_seconds_total{container_label_com_docker_compose_service="backend"}[1m])
```
This adjustment will display the CPU usage of the backend container in the dashboard.

### Changing Grafana to Light Mode

To Change the Grafana Interface into Light Mode:

1. Click on the Profile Icon in the upper right corner and the "Profile" Button.

2. Preferences -> Interface theme -> Light


## Known Issues

1. The volume configuration `- /var/lib/docker/:/var/lib/docker:ro` in the `cadvisor` section of the `compose.yaml` file causes the following error on Linux systems:
   ```
   Error response from daemon: error while creating mount source path '/var/lib/docker': mkdir /var/lib/docker: read-only file system
   ```
   This can be bypassed by commenting out this line in the docker-compose.yaml file, although the exact purpose of this code is not known yet.
