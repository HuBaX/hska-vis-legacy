[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)

# Distributed Information Systems Laboratory aka vis-lab
This project is a fork from the [vis-lab](https://github.com/hka-iwi-vislab/hska-vis-legacy) project of the [Hochschule Karlsruhe](http://www.hs-karlsruhe.de) (University of Applied Sciences) and is used for the Distributed Information Systems Laboratory.

Table of Contents
=================

   * [Distributed Information Systems Laboratory aka vis-lab](#distributed-information-systems-laboratory-aka-vis-lab)
      * [Getting Started](#getting-started)
         * [Prerequisites](#prerequisites)
         * [Build the microservices](#build-the-microservices)
         * [Deploy the microservices in minikube](#deploy-the-microservices-in-minikube)
      * [Installation of istio](#installation-of-istio)
         * [Installation of Grafana, Prometheus and kiali](#installation-of-grafana-prometheus-and-kiali)
      * [Expose the shop](#expose-the-shop)
      * [License](#license)


## Getting Started
This is a guide how to deploy and run the project on your local machine using minikube on a windows host system. For other OS use different commands to build in minikube context.

### Prerequisites

- start minikube with enough ressources (8GB can work, but 16GB is recommended)
```bash
# start minikube with 16GB of ram and 4 cpus
minikube start --memory=16384 --cpus=4
```

- clone the git repositories
```bash
# base git with mysql and legacy webshop
git clone https://github.com/HuBaX/hska-vis-legacy.git

# category microservice git
git clone https://github.com/HuBaX/CategoryService.git

# product microservice git
git clone https://github.com/HuBaX/ProductService.git
```

### Build the microservices

- Build the product service
```bash
cd ProductService/
& minikube -p minikube docker-env --shell powershell | Invoke-Expression
docker build -t product .
cd ..
```

- Build the category service
```bash
cd CategoryService/
& minikube -p minikube docker-env --shell powershell | Invoke-Expression
docker build -t category .
cd ..
```
- Build apache, mysql and the legacy webshop
```bash
cd hska-vis-legacy/
& minikube -p minikube docker-env --shell powershell | Invoke-Expression
docker build -t apache -f .\docker\DockerfileApache .

docker build -t mavogel/hska-vis-web-shop-db-image -f .\docker\DockerfileMySQL .

docker build -t mavogel/hska-vis-legacywebshop -f .\docker\Dockerfile .
```
### Deploy the microservices in minikube

0. Go in the hska-vis-legacy folder
```bash
cd hska-vis-legacy/
```

1. Run the deployment yamls from the yaml-folder in the minikube cluster.
```bash
kubectl apply -f ./yaml/mysql/
kubectl apply -f ./yaml/deployment/
kubectl apply -f ./yaml/services/
```
2. Check if everything is up and running.

```bash
kubectl get pods
```

## Installation of istio
0. install istioctl(in admin powershell!):
```bash
choco install istioctl
```
If no choco is available, see alternative installations: https://istio.io/latest/docs/setup/getting-started/

1. install istio
```bash
istioctl install
```

2. configure Envoy Proxy injection
create labe fro istio for default namespace
```bash
kubectl get ns default --show-labels
kubectl label namespace default istio-injection=enabled
kubectl get ns default --show-labels
```
(delete label with:  `kubectl label namespace default istio-injection-`)

3. delete all deployments and redeploy

```bash
# delete all
kubectl delete -f ./yaml/deployment/

# redeploy all pods
kubectl apply -f ./yaml/deployment/
```

### Installation of Grafana, Prometheus and kiali

0. copy the yaml files from istio addon folder to yaml folder istio-addons
(get files from latest release: https://github.com/istio/istio/releases)

1. apply the yaml files
```bash
kubectl apply -f ./yaml/istio-addons/
```

2. portforward kiali service

get service port from kiali: `kubectl get svc -A`

```bash
kubectl port-forward svc/kiali -n istio-system 20001
```

3. visit Kiali frontend at localhost:20001

4. portfoward Grafana service

get service port from kiali: `kubectl get svc -A`

```bash
kubectl port-forward svc/grafana -n istio-system 3000
```

5. visit Grafana frontend at localhost:3000

## Expose the shop

1. minikube service

```bash
minikube service legacy-service --url
```

2. go to url and port and add /EShop-1.0.0 to it

3. login with: 
```yaml
user: admin
pwd: admin
```

 
 ## License
 Source code is open source and released under the MIT license.