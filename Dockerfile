FROM anapsix/alpine-java:latest
MAINTAINER netisov@gmail.com
VOLUME /var/lib/short-libs
COPY build/libs/*.jar /opt/short-urls/short-urls.jar
CMD ["java","-jar", "/opt/short-urls/short-urls.jar"]
EXPOSE 80