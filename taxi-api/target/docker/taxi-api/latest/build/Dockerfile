FROM tomcat:9.0-alpine

# EXPOSE SERVER PORT
EXPOSE 8080

# SET HOME
WORKDIR /usr/local/tomcat/

COPY maven/taxi-api-*.war /usr/local/tomcat/webapps/taxi-api.war

# RUN TOMCAT
CMD ["catalina.sh","run"]

