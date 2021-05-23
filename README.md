# Fuxpin Cloud Config Server

![](https://img.shields.io/badge/fuxpin%20config%20server-0.0.1-blue)

Spring Cloud server provides properties to the applications. Thanks to this, when an external application is started or replicated, this server load all the properties from GitHub and serves it.

## Behaviour
* When the server is started, it pulls all the configuration properties into their cloned sources.
* The server clones a remote repository every time that an application request its configuration
* If Git repository is shut down, problems with connection, network, timeout,... the server provides the latest version pulled

## Usage

Environment Repository:

* application -  which maps to spring.application.name on the client side.
* profile - which maps to spring.profiles.active on the client (comma-separated list).
* label - which is a server side feature labelling a "versioned" set of config files. For example: master, develop, main, ...

## Examples

**Example 1**:

````
https://xrodrig.dnsnet.info:8445/fuxpin-config-server/ventas/localhost/master
````

* application: ventas
* profile: localhost
* label = branch : master

Response:

````
{
    "name": "ventas",
    "profiles": [
        "localhost"
    ],
    "label": "develop",
    "version": "ca545de1a0ec4960cc7bc84514d68d31ec771bda",
    "state": null,
    "propertySources": [
        {
            "name": "https://github.com/xrodriguezang/fuxpin-properties/file:C:\\Users\\amgri\\AppData\\Local\\Temp\\config-repo-12576579889428229231\\ventas-localhost.yml",
            "source": {
                "server.port": 8443,
                "server.name": "https://xrodrig.dnsnet.info:${server.port}"
            }
        },
        {
            "name": "https://github.com/xrodriguezang/fuxpin-properties/file:C:\\Users\\amgri\\AppData\\Local\\Temp\\config-repo-12576579889428229231\\ventas.yml",
            "source": {
                "spring.application.name": "ventas",
                "spring.jpa.hibernate.ddl-auto": "none",
                "spring.datasource.initialization-mode": "always",
                "spring.datasource.platform": "postgres",
                "spring.datasource.url": "jdbc:postgresql://pi.intranet.cat:5432/aplicaciondeventas",
                "spring.datasource.username": "XXXXXXXX",
                "spring.datasource.password": "XXXXXXXX",
                "server.servlet.context-path": "/ventas",
                "server.ssl.enabled": true,
                "server.ssl.keyPassword": XXXXXXX,
                "server.ssl.keyStore": "file:C:\\Users\\amgri\\desenvolupament\\ss-tfg-unir\\xrodrigdnskeystore",
                "server.ssl.keyStorePassword": XXXXXX,
                "keycloak.realm": "programasgestion",
                "keycloak.resource": "aplicacion-ventas",
                "keycloak.auth-server-url": "https://tfc.dnsnet.info:8443/auth/",
                "keycloak.ssl-required": "external",
                "keycloak.public-client": true,
                "keycloak.principal-attribute": "uid"
            }
        }
    ]
}
````

**Example 2**

````
https://xrodrig.dnsnet.info:8445/fuxpin-config-server/ventas/production/develop
````

* application: ventas
* profile: production
* label = branch: develop

Response:

````
{
    "name": "ventas",
    "profiles": [
        "production"
    ],
    "label": "develop",
    "version": "ca545de1a0ec4960cc7bc84514d68d31ec771bda",
    "state": null,
    "propertySources": [
        {
            "name": "https://github.com/xrodriguezang/fuxpin-properties/file:C:\\Users\\amgri\\AppData\\Local\\Temp\\config-repo-12576579889428229231\\ventas-production.yml",
            "source": {
                "server.port": 8446,
                "server.name": "https://tfc.dnsnet.info:${server.port}"
            }
        },
        {
            "name": "https://github.com/xrodriguezang/fuxpin-properties/file:C:\\Users\\amgri\\AppData\\Local\\Temp\\config-repo-12576579889428229231\\ventas.yml",
            "source": {
                "spring.application.name": "ventas",
                "spring.jpa.hibernate.ddl-auto": "none",
                "spring.datasource.initialization-mode": "always",
                "spring.datasource.platform": "postgres",
                "spring.datasource.url": "jdbc:postgresql://pi.intranet.cat:5432/aplicaciondeventas",
                "spring.datasource.username": "XXXXXXXX",
                "spring.datasource.password": "XXXXXXXX",
                "server.servlet.context-path": "/ventas",
                "server.ssl.enabled": true,
                "server.ssl.keyPassword": XXXXXX,
                "server.ssl.keyStore": "file:C:\\Users\\amgri\\desenvolupament\\ss-tfg-unir\\xrodrigdnskeystore",
                "server.ssl.keyStorePassword": XXXXXX,
                "keycloak.realm": "programasgestion",
                "keycloak.resource": "aplicacion-ventas",
                "keycloak.auth-server-url": "https://tfc.dnsnet.info:8443/auth/",
                "keycloak.ssl-required": "external",
                "keycloak.public-client": true,
                "keycloak.principal-attribute": "uid"
            }
        }
    ]
}
````
## Define enviorments

To enable the *production* profile. In this cas, the server run reading production properties. In ***VM options*** define: 
````
-Dspring.profiles.active=production
````

## Enviorment variables
You have to define in environment variables(java), for example, to populate the github credentials. In Spring Boot task -> ***VM options*** define:

````
-Duser.github.login=login 
-Duser.github.password=password 
-Dkeystore.password=password 
-Drest.user=user 
-Drest.password=password
````

## Examples *Serverless* execution

* default:

``C:\Users\amgri\.jdks\jdk-11.0.7\bin\java -jar .\fuxpin-config-server-0.0.1.jar``
* production:

``C:\Users\amgri\.jdks\jdk-11.0.7\bin\java -jar "-Dspring.profiles.active=production" .\fuxpin-config-server-0.0.1.jar``

Commands to generate .jar:

````
$ gradew clean assemble
````
jar location
````
${PROJECT_DIRECTORY}/build/libs/
````

## Production launcher

````
java -Xms128m -Xmx256m -jar -Dspring.profiles.active=production -Duser.github.login=login -Duser.github.password=password -Dkeystore.password=password -Drest.user=user -Drest.password=password fuxpin-config-server-0.0.1.jar
````

## Create a Run Java Jar Application with Systemd
* For this configuration *pi* user is used to run the serveless installation. Before proced, create a directory:
````
/opt/java-jar 
````
Give the user ang group ownership permissions for the Fuxpin Systems Jars: 
````
sudo chown -R pi:pi /opt/java-jars 
````
* Create Systemd Service
````
sudo vi /etc/systemd/system/fuxpinconfigserver.service
````
with contents:
````editorconfig
[Unit]
Description=Fuxpin Cloud Config Server Java service

[Service]
WorkingDirectory=/opt/java-jars
ExecStart=java -Xms128m -Xmx256m -jar -Dspring.profiles.active=production -Duser.github.login=login -Duser.github.password=password -Dkeystore.password=password -Drest.user=user -Drest.password=password fuxpin-config-server-0.0.1.jar
User=pi
Type=simple
Restart=on-failure

[Install]
WantedBy=multi-user.target
````

* Before start the application, reload systemd so that it knows ot the new service added: 
````
sudo systemctl daemon-reload
````

* Once realoaded, the service is avaliable:
````
sudo systemctl start fuxpinconfigserver
````
* Also, verify the status:
````
sudo systemctl status fuxpinconfigserver
````
Result:
````
● fuxpinconfigserver.service - Fuxpin Cloud Config Server Java service
   Loaded: loaded (/etc/systemd/system/fuxpinconfigserver.service; static; vendor preset: enabled)
   Active: active (running) since Sun 2021-05-23 12:10:12 CEST; 16min ago
 Main PID: 2596 (java)
    Tasks: 38 (limit: 4915)
   Memory: 197.7M
   CGroup: /system.slice/fuxpinconfigserver.service
           └─2596 /usr/bin/java -Xms128m -Xmx256m -jar -Dspring.profiles.active=production -Duser.github.login=login -Duser.github.password=password -Dkeystore.password=password -Drest.user=user -Drest.password=password fuxpin-config-server-0.0.1.jar

May 23 12:10:37 raspberrypi java[2596]: 2021-05-23 12:10:37,332 INFO  org.springframework.security.web.DefaultSecurityFilterChain : Will secure any request with [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@db2d95, org.springfra
May 23 12:10:38 raspberrypi java[2596]: 2021-05-23 12:10:38,714 INFO  org.springframework.boot.web.embedded.tomcat.TomcatWebServer : Tomcat started on port(s): 8446 (https) with context path '/fuxpin-config-server'
May 23 12:10:38 raspberrypi java[2596]: 2021-05-23 12:10:38,817 INFO  unir.tfg.configserver.fuxpinconfigserver.FuxpinConfigServerApplication : Started FuxpinConfigServerApplication in 22.958 seconds (JVM running for 26.114)
May 23 12:10:39 raspberrypi java[2596]: 2021-05-23 12:10:39,060 INFO  org.springframework.boot.availability.ApplicationAvailabilityBean : Application availability state LivenessState changed to CORRECT
May 23 12:10:39 raspberrypi java[2596]: 2021-05-23 12:10:39,070 INFO  org.springframework.boot.availability.ApplicationAvailabilityBean : Application availability state ReadinessState changed to ACCEPTING_TRAFFIC
````
* To stop the application:
````
sudo systemctl stop fuxpinconfigserver
````  
* To restart the application:
````
sudo systemctl restart fuxpinconfigserver.service
````

* To enable the service on startup server boot:
````
sudo systemctl enable fuxpinconfigserver
````
Result:
````
Created symlink /etc/systemd/system/multi-user.target.wants/fuxpinconfigserver.service → /etc/systemd/system/fuxpinconfigserver.service.
````

# References:

* https://cloud.spring.io/spring-cloud-config/reference/html/
* https://cloud.spring.io/spring-cloud-config/reference/html/#_spring_cloud_config_server
* https://computingforgeeks.com/how-to-run-java-jar-application-with-systemd-on-linux/