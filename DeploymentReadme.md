# HowToDeploy
this setup aims to work with minikube based on windows.


## start minikube

```bash
# start minikube with 16GB of ram and 4 cpus
minikube start --memory=16384 --cpus=4
```

## get ressources
```bash
# base git with mysql and legacy webshop
git clone https://github.com/HuBaX/hska-vis-legacy.git

# category microservice git
git clone https://github.com/HuBaX/CategoryService.git

# product microservice git
git clone https://github.com/HuBaX/ProductService.git
```

## build microservices
to build in minikube use in windows:
`& minikube -p minikube docker-env --shell powershell | Invoke-Expression`

```bash
# build product service
cd ProductService/
docker build -t product .
cd ..


# build category service
cd CategoryService/
docker build -t category .
cd ..

# build apache and mysql
cd hska-vis-legacy/
docker build -t apache -f .\docker\DockerfileApache .

docker build -t mavogel/hska-vis-web-shop-db-image -f .\docker\DockerfileMySQL .

docker build -t mavogel/hska-vis-legacywebshop -f .\docker\Dockerfile .

```

## deployment
Run the deployment yamls from the yaml-folder in the minikube cluster.

```bash
cd hska-vis-legacy/
kubectl apply -f ./yaml/mysql/
kubectl apply -f ./yaml/deployment/
kubectl apply -f ./yaml/services/
```

Check if everything is up and running.

```bash
kubectl get pods
```

## installation of istio
see: (https://youtu.be/voAyroDb6xk)
1. install istioctl(in admin powershell!):
```bash
choco install istioctl
```
alternative see: https://istio.io/latest/docs/setup/getting-started/

2. install istio
```bash
istioctl install
```
3. configure Envoy Proxy injection
create labe fro istio for default namespace
```bash
kubectl get ns default --show-labels
kubectl label namespace default istio-injection=enabled
kubectl get ns default --show-labels
```
(delete label with:  `kubectl label namespace default istio-injection-`)

4. delete all deployments and redeploy

```bash
# delete all
kubectl delete -f ./yaml/services/
kubectl delete -f ./yaml/deployment/
kubectl delete -f ./yaml/mysql/


# redeploy all pods
kubectl apply -f ./yaml/mysql/
kubectl apply -f ./yaml/deployment/
kubectl apply -f ./yaml/services/
```

## install Grafana, Prometheus and kaili addons for istio
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


## Make shop visible

1. minikube service

```bash
minikube service legacy-service --url
```

2. go to url and port and add /EShop-1.0.0 to it

3. login with: 
    user: admin
    pwd: admin
