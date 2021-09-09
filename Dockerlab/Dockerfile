FROM openjdk:alpine

LABEL maintainer "eric.muellenbach@yncrea.fr"

EXPOSE 3000

ADD app.java .

RUN javac app.java

ENTRYPOINT ["java", "app"]