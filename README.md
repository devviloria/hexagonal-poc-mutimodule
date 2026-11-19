# Hexagonal POC Multi-módulos

Ejemplo Spring Boot basado en Maven multi-módulos. Conserva la arquitectura
hexagonal y añade el empaquetado de una aplicación desplegable en Kubernetes.

## Módulos

- `domain`: entidad `Product` y excepciones del dominio.
- `application`: puertos de entrada y salida, y servicio de aplicación.
- `adapter-web`: API REST, DTO y manejo de errores HTTP.
- `adapter-persistence`: implementación en memoria del repositorio.
- `bootstrap`: aplicación Spring Boot y composición de componentes.

El almacenamiento es en memoria. Cada pod conserva sus productos únicamente
mientras sigue ejecutándose; para mantener datos persistidos entre reinicios se
debería sustituir el adaptador por una base de datos externa.

## Ejecutar localmente

Requiere JDK 21. El proyecto incluye Maven Wrapper.

```bash
./mvnw clean package
java -jar bootstrap/target/bootstrap-0.0.1-SNAPSHOT.jar
```

## API

```bash
curl -i -X POST http://localhost:8080/api/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Teclado","price":25.50}'

curl http://localhost:8080/api/products

PRODUCT_ID="pega-aqui-el-id-devuelto-al-crear"

curl -i -X PUT "http://localhost:8080/api/products/$PRODUCT_ID" \
  -H 'Content-Type: application/json' \
  -d '{"name":"Teclado mecánico","price":49.99}'

curl -i -X DELETE "http://localhost:8080/api/products/$PRODUCT_ID"
```

`PUT` reemplaza el nombre y el precio del producto; responde `404` si el ID no
existe. `DELETE` responde `204` al eliminarlo y `404` si no existe.

## Docker

Primero genera el JAR y después construye la imagen:

```bash
./mvnw clean package
docker build -t hexagonal-products:1.0.0 .
docker run --rm -p 8080:8080 hexagonal-products:1.0.0
```

## Kubernetes

Aplica los manifiestos desde la raíz del proyecto:

```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl get pods
kubectl get service hexagonal-products
```

En Docker Desktop, Minikube o un clúster local compatible puedes acceder a:

```text
http://localhost:30080/api/products
```

Dependiendo del proveedor Kubernetes, puede ser necesario usar la dirección IP
del clúster o reenviar el puerto:

```bash
kubectl port-forward service/hexagonal-products 8080:80
```
