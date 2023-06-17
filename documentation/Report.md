# VS-Lab - eShop Report

`Gruppe(IZ-Kürzel): krse1019, krse1020, rufl1020, wigo1011`

## Aufgabe 1
### 1. Aufgabe

![WebShopScreenshot01](./pictures/WebShop01.png "Suchanfrage")
![WebShopScreenshot02](./pictures/WebShop02.png "Produktbeschreibung")
![WebShopScreenshot03](./pictures/WebShop03.png "Search")

#### Class Diagram

![ClassDiagram](./pictures/ClassDiagram-AddProductAction.png "ClassDiagram")

---
























### 2. Aufgabe

>Analysieren sie Struktur und Verhalten des eShop --> c - Erstellen sie ein UML Strukturdiagramm (zB Klassendiagramm) und ein UML Verhaltens- diagramm (zB Sequenzdiagramm).

![UMLDiagram](./pictures/UML-AddProduct.png "UMLDiagram")


---






### 3. Aufgabe

>Erstellen sie ein fachliches Makro-modell des eShop mit DDD --> e - Diskutieren sie die Auswahl eines Bounded Context für die Migration als Microservice.

![ContextMap](./pictures/context_map.png "ContextMap")


Es ist sinnvoll einen Bounded Context zu migrieren, weil dieser einen in sich geschlossene Domäne repräsentiert und somit keine Überschneidung mit Komponenten aus dem Legacy System hat.

Wenn man sich das Strangler pattern als Vorgehen aussucht, wäre es am sinnvollsten die Komponenten zu migrieren, die am häufigsten von den Entwicklern geändert werden müssen. Komponenten die vielleicht gar nicht mehr geändert werden müssen, können sogar in manchen Szenarien als Legacy System bestehen bleiben. So oder so muss wenn die Migration Schrittweise erfolgt eine Schnittstelle zwischen legacy System und den neuen Microservices geschaffen werden.

---








































## Aufgabe 2
Architekturskizze des Webshops in Docker-Containern. 

![Architekturskizze](pictures/ArchitekturDocker.png)



---

































## Augabe 3 

In diesem Abschnitt finden Sie Screenshots zu den verschiedenen Frontends und der Arbeit des Load-Balancers.

### Minikube und Docker images
- Ausgabe der Liste aller Docker images auf dem System

    ![dockerImages](pictures/DockerImages.png)
- oAusgabe der Liste aller Kubernetes Pods aus dem Minikube

    ![getPods](pictures/GetPods.png)

### Load-Balancer
1. Erster ProductRequest

![firstRequest](pictures/Replica1.png)

2. Zweiter ProductRequest

![secondRequest](pictures/Replica2.png)

3. Dritter ProductRequest

![thirdRequest](pictures/Replica3.png)

Alle Hostnamen sind unterschiedlich! -> Der Load-Balancer funktioniert wie erwartet. 

### Kiali und Grafana Dashboards

- Screenshot von Kiali nach Generierung von Traffic auf dem Webshop

    ![kialiGraph](pictures/Kiali-Graph.png)


- Schreenshot von Grafana(istio-control-plane-dashboard) nach Generierung von Traffic auf dem Webshop
    ![grafanaDashboard](pictures/Grafana-Dashboard.png)
    
---

## Aufgabe 4

Performancetest Ergebnisse der Responsetime der verschiedenen Requests.

![PErformance-Test Ergebnisse mit inmplementiertem Timer - 2.](pictures/performance-2.png)

![PErformance-Test Ergebnisse mit inmplementiertem Timer - 1.](pictures/performance-1.png)

### Avarage Response-Time

| Action    | Called Action | Avg. Response Time |
| ---       | ---               | ---               |
| open add-Product Tab | getCategories() |6ms |
| add new product | addProduct() | 35ms |
| list all products | getProducts() | 45ms |
| edit categories | getCategories() | 5ms |
| add new category | addCategory() | 8ms |
| delete categorie | delCategoryById() | 14ms |
| delete product | deleteProductById() | 8ms |
| search product | getProductForSearchValues() | 13ms |





