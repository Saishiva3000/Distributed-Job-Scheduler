FROM maven:3.9-eclipse-temurin-25-alpine AS yoo

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

copy src ./src

RUN mvn clean package -DskipTests

RUN java -Djarmode=layertools -jar target/*.jar extract

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN addgroup -S mundas && adduser -S karrimunda -G mundas

COPY --from=yoo /app/dependencies/ ./

COPY --from=yoo /app/spring-boot-loader/ ./

COPY --from=yoo /app/snapshot-dependencies/ ./

COPY --from=yoo /app/application/ ./

RUN chown -R karrimunda:mundas /app

EXPOSE 8080

USER karrimunda

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]