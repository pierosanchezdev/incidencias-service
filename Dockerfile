# Etapa 1: Build de la app
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copiamos el Maven wrapper y archivos necesarios
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente
COPY src src

# Empaquetamos el proyecto
RUN ./mvnw clean package -DskipTests

# ---------------------------------------------------------------------

# Etapa 2: Imagen final de ejecución
FROM eclipse-temurin:21-jre

WORKDIR /app

# Variable para profile (por defecto dev)
ENV SPRING_PROFILES_ACTIVE=dev

# Copiamos solo el JAR generado
COPY --from=builder /app/target/*.jar app.jar

# Variables de entorno necesarias (puedes pasar en docker run también)
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV JWT_SECRET=""

# Exponer puerto
EXPOSE 9090

# Comando de arranque
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]
