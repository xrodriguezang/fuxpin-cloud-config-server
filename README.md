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
You have to define in environment variables(java) to populate the github credentials. In ***VM options*** define:

````
${user.github.login}
${user.github.password}
````

## Example *Serverless* execution

* default:

``C:\Users\amgri\.jdks\jdk-11.0.7\bin\java -jar .\fuxpin-config-server-0.0.1.jar``
* production:

``C:\Users\amgri\.jdks\jdk-11.0.7\bin\java -jar "-Dspring.profiles.active=production" .\fuxpin-config-server-0.0.1.jar``

Commands to generate .jar:

````
$ gradew clean assemble
````
directory:
````
/build/libs/
````

## Production launcher

``
java -jar "-Dspring.profiles.active=production" "-Duser.github.login=login" "-Duser.github.password=password" "-Dkeystore.password=password" "-Drest.user=user" "-Drest.password=password" fuxpin-config-server-0.0.1.jar
``
References:

* https://cloud.spring.io/spring-cloud-config/reference/html/
* https://cloud.spring.io/spring-cloud-config/reference/html/#_spring_cloud_config_server