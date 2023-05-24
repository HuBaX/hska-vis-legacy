## Report Screenshots 
In this section we provide screenshots for the different frontends and the work of the load-balancer.

### Minikube and Docker images
- output from list all docker images on the system

    ![dockerImages](pictures/DockerImages.png)
- output from list all pods in all namespaces on the minikube

    ![getPods](pictures/GetPods.png)

### Load balancer
1. First GetProduct request

![firstRequest](pictures/Replica1.png)

2. Second GetProduct request

![secondRequest](pictures/Replica2.png)

3. Third GetProduct request

![thirdRequest](pictures/Replica3.png)

All hostnames are different! -> The load-balancer is working accordingly. 

### Kiali and Grafana

- Screenshot of Kiali after generating some traffic on the webshop

    ![kialiGraph](pictures/Kiali-Graph.png)

- Screenshot of Grafana(istio-control-plane-dashboard) after generating some 

traffic on the webshop
    ![grafanaDashboard](pictures/Grafana-Dashboard.png)
    

