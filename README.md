# 📦 Sistema de Inventario de Almacenes - ArquiSoft Parcial 2

**Arquitectura de Software - Parcial #2**  
*API RESTful para gestión de inventarios de productos en almacenes*

---

## 🎯 Descripción del Proyecto

Sistema de gestión de inventario desarrollado en **Spring Boot** que permite administrar productos y almacenes a través de una API REST. El sistema implementa las siguientes funcionalidades principales:

### 📋 Funcionalidades Principales

1. **🔍 Consulta de Inventario (GET)**: Consultar el inventario de productos de un almacén determinado
2. **➕ Creación de Productos (POST)**: Ingresar información de productos del almacén con cantidades iniciales en stock

---

## 🏗️ Arquitectura del Sistema

El proyecto implementa una **arquitectura en capas** siguiendo las mejores prácticas de Spring Boot:

```
src/main/java/com/udea/ArquiSoft_Parcial2/
├── 📁 model/          # Entidades JPA (Almacen, Producto)
├── 📁 repository/     # Capa de acceso a datos (JpaRepository)
├── 📁 service/        # Lógica de negocio y conversiones DTO
├── 📁 controller/     # Controladores REST API
├── 📁 dto/            # Objetos de transferencia de datos
├── 📁 config/         # Configuraciones (CORS, DataInitializer)
└── 📁 exception/      # Manejo global de excepciones
```

---

## 🛠️ Tecnologías Utilizadas

### Backend Framework
- **Spring Boot 3.5.3**
- **Java 17**
- **Maven** (Gestión de dependencias)

### Base de Datos
- **PostgreSQL** (Base de datos principal)
- **Spring Data JPA** (ORM)
- **Hibernate** (Implementación JPA)

### Herramientas y Librerías
- **Lombok** (Reducción de código boilerplate)
- **Bean Validation** (Validación de datos)
- **Spring Boot Actuator** (Monitoreo)
- **SLF4J + Logback** (Logging)

---

## 🚀 Configuración y Ejecución

### Prerrequisitos

1. **Java 17** o superior
2. **PostgreSQL 12+** 
3. **Maven 3.6+**

### Configuración de Base de Datos

1. Crear base de datos PostgreSQL:
```sql
CREATE DATABASE inventario_db;
CREATE USER postgres WITH ENCRYPTED PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE inventario_db TO postgres;
```

2. Verificar configuración en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventario_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Ejecutar la Aplicación

```bash
# Clonar el repositorio
git clone <repository-url>
cd ArquiSoft_Parcial2

# Compilar el proyecto
./mvnw clean compile

# Ejecutar la aplicación
./mvnw spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

---

## � Documentación Interactiva de la API (Swagger)

### 🌐 URLs de Acceso a Swagger

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva:

| Recurso | URL | Descripción |
|---------|-----|-------------|
| **Swagger UI** | **http://localhost:8080/swagger-ui.html** | Interfaz interactiva para probar la API |
| **OpenAPI JSON** | **http://localhost:8080/v3/api-docs** | Especificación OpenAPI en formato JSON |
| **OpenAPI YAML** | **http://localhost:8080/v3/api-docs.yaml** | Especificación OpenAPI en formato YAML |

### 🛠️ Solución de Problemas de Swagger

Si encuentras errores al acceder a Swagger, sigue estos pasos:

#### Error 500 en `/api-docs` o `/v3/api-docs`:
1. **Verifica que la aplicación compile correctamente:**
   ```bash
   ./mvnw clean compile
   ```

2. **Revisa los logs de la aplicación** para identificar errores específicos

3. **URLs alternativas a probar:**
   - `http://localhost:8080/swagger-ui/index.html`
   - `http://localhost:8080/v3/api-docs`

#### Si Swagger UI no carga:
1. **Verifica que la aplicación esté ejecutándose:** `http://localhost:8080/actuator/health`
2. **Prueba el endpoint directo:** `http://localhost:8080/api/inventario/productos?almacenId=1`
3. **Revisa la consola del navegador** para errores JavaScript

#### Configuración mínima funcional:
Si persisten los problemas, puedes acceder a la API directamente usando:
- **curl** (como se muestra en los ejemplos)
- **Postman** o **Insomnia**
- **REST Client extensions** en VS Code

### 🔧 Características de Swagger UI

- **Interfaz interactiva**: Prueba los endpoints directamente desde el navegador
- **Documentación completa**: Esquemas, ejemplos y descripciones detalladas
- **Validación automática**: Validación de parámetros y respuestas
- **Ejemplos de uso**: Casos de ejemplo para cada endpoint
- **Descarga de especificación**: Exporta la documentación en JSON/YAML

---

## �📚 API Endpoints

### Base URL: `http://localhost:8080/api/inventario`

| Método | Endpoint | Descripción | Headers Requeridos |
|--------|----------|-------------|-------------------|
| `GET` | `/productos?almacenId={id}` | Consultar inventario por almacén | `API-Version: 1.0` |
| `POST` | `/productos` | Crear nuevo producto | `API-Version: 1.0`, `Content-Type: application/json` |

---

## 📖 Documentación de la API

### 🔍 Consultar Inventario

**GET** `/api/inventario/productos?almacenId={id}`

```bash
curl -X GET "http://localhost:8080/api/inventario/productos?almacenId=1" \
     -H "API-Version: 1.0"
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Inventario consultado exitosamente",
  "data": {
    "almacen": {
      "id": 1,
      "nombre": "Almacén Central",
      "direccion": "Calle 10 #20-30",
      "ciudad": "Medellín",
      "activo": true
    },
    "productos": [
      {
        "id": 1,
        "codigo": "ELEC001",
        "nombre": "Laptop Dell",
        "descripcion": "Laptop Dell Inspiron 15",
        "precio": 2500000.00,
        "cantidadStock": 10,
        "cantidadMinima": 2,
        "cantidadMaxima": 50,
        "categoria": "Electrónicos",
        "unidadMedida": "Unidad",
        "stockCritico": false,
        "activo": true
      }
    ]
  }
}
```

### ➕ Crear Producto

**POST** `/api/inventario/productos`

```bash
curl -X POST "http://localhost:8080/api/inventario/productos" \
     -H "API-Version: 1.0" \
     -H "Content-Type: application/json" \
     -d '{
       "codigo": "ELEC006",
       "nombre": "Tablet Samsung",
       "descripcion": "Tablet Samsung Galaxy Tab A8",
       "precio": 800000.00,
       "cantidadStock": 15,
       "cantidadMinima": 3,
       "cantidadMaxima": 40,
       "categoria": "Electrónicos",
       "unidadMedida": "Unidad",
       "almacenId": 1
     }'
```

**Respuesta exitosa (201):**
```json
{
  "success": true,
  "message": "Producto creado exitosamente",
  "data": {
    "id": 9,
    "codigo": "ELEC006",
    "nombre": "Tablet Samsung",
    "descripcion": "Tablet Samsung Galaxy Tab A8",
    "precio": 800000.00,
    "cantidadStock": 15,
    "cantidadMinima": 3,
    "cantidadMaxima": 40,
    "categoria": "Electrónicos",
    "unidadMedida": "Unidad",
    "stockCritico": false,
    "activo": true
  }
}
```

---

## 🗃️ Modelo de Datos

### Entidad Almacén
- `id` (Long): Identificador único
- `nombre` (String): Nombre del almacén
- `direccion` (String): Dirección física
- `telefono` (String): Número de contacto
- `email` (String): Correo electrónico
- `ciudad` (String): Ciudad ubicación
- `pais` (String): País ubicación
- `activo` (Boolean): Estado del almacén

### Entidad Producto
- `id` (Long): Identificador único
- `codigo` (String): Código único del producto
- `nombre` (String): Nombre del producto
- `descripcion` (String): Descripción detallada
- `precio` (BigDecimal): Precio unitario
- `cantidadStock` (Integer): Cantidad actual en stock
- `cantidadMinima` (Integer): Stock mínimo requerido
- `cantidadMaxima` (Integer): Stock máximo permitido
- `categoria` (String): Categoría del producto
- `unidadMedida` (String): Unidad de medida
- `activo` (Boolean): Estado del producto
- `almacenId` (Long): Referencia al almacén

---

## 🌱 Datos de Prueba

El sistema incluye un **DataInitializer** que carga automáticamente datos de prueba:

### Almacenes por Defecto:
1. **Almacén Central** (ID: 1) - Medellín
2. **Almacén Norte** (ID: 2) - Medellín  
3. **Almacén Bogotá** (ID: 3) - Bogotá

### Productos de Ejemplo:
- Electrónicos: Laptops, Monitores, Teclados, etc.
- Oficina: Papel, Marcadores, Cuadernos, etc.
- Total: **8 productos** distribuidos en los 3 almacenes

---
---

## 📂 Estructura del Proyecto

```
ArquiSoft_Parcial2/
├── 📁 src/main/java/com/udea/ArquiSoft_Parcial2/
│   ├── 📄 ArquiSoftParcial2Application.java    # Clase principal
│   ├── 📁 model/
│   │   ├── 📄 Almacen.java                     # Entidad Almacén
│   │   └── 📄 Producto.java                    # Entidad Producto
│   ├── 📁 repository/
│   │   ├── 📄 AlmacenRepository.java           # Repositorio Almacén
│   │   └── 📄 ProductoRepository.java          # Repositorio Producto
│   ├── 📁 service/
│   │   ├── 📄 AlmacenService.java              # Servicio Almacén
│   │   └── 📄 ProductoService.java             # Servicio Producto
│   ├── 📁 controller/
│   │   └── 📄 InventarioController.java        # Controlador REST
│   ├── 📁 dto/
│   │   ├── 📄 AlmacenDTO.java                  # DTO Almacén
│   │   ├── 📄 ProductoDTO.java                 # DTO Producto
│   │   └── 📄 ApiResponse.java                 # Wrapper respuestas
│   ├── 📁 config/
│   │   ├── 📄 WebConfig.java                   # Configuración CORS
│   │   └── 📄 DataInitializer.java             # Carga datos iniciales
│   └── 📁 exception/
│       ├── 📄 AlmacenNotFoundException.java    # Excepción almacén
│       ├── 📄 ProductoYaExisteException.java   # Excepción producto
│       └── 📄 GlobalExceptionHandler.java      # Manejador global
├── 📁 src/main/resources/
│   └── 📄 application.properties               # Configuración app
└── 📄 pom.xml                                  # Dependencias Maven
```

---

## 👥 Autor

**Estudiantes:** Estiven Ospina González , Maria Daniela Rodriguez Chacon, Juan José Garcia y Sara Galvan Ortega
**Materia:** Arquitectura de Software  
**Evaluación:** Parcial #2  
**Universidad:** Universidad de Antioquia

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
