#Use jdk image to build
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /opt/app

#Copy source code
COPY monolith/ ./monolith/
COPY infrastructure/ ./infrastructure/
COPY domain/ ./domain/
COPY application/ ./application/
COPY token.txt ./token.txt

# Copy .mvn mvnw and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

#Download all dependencies
RUN ./mvnw dependency:go-offline

#Use docker for test containers and build app
FROM docker:latest
RUN ./mvnw clean install

#Use jre image to deploy
FROM eclipse-temurin:17-jre-alpine as deployer
WORKDIR /opt/app

#Copy and run .jar
COPY --from=builder /opt/app/monolith/target/*.jar /opt/app/*.jar
COPY --from=builder /opt/app/token.txt /opt/app/token.txt
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]

#docker build -t cocktail-bot:1.0 .

