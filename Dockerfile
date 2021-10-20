FROM openjdk:11.0.11-jdk

COPY ./target/currencyexchange-0.0.1-SNAPSHOT.jar /usr/app/currencyexchange.jar

WORKDIR /usr/app

CMD ["java", "-jar", "/usr/app/currencyexchange.jar"]