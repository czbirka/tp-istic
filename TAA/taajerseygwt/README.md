gwtjerseyjpaexample
===================

This provide an example for the integration of a GWT web app that call a json restfull webservices developped using jersey. 

It uses autobean to do the marshalling/unmarshalling of JSON Object

To run it

```bash
mvn clean compile gwt:compile  package tomcat7:run-war-only
