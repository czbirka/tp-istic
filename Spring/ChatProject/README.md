# TP Spring

### Authors
* Thomas Daniellou (<thomas.danll@gmail.com>)
* Amona Souliman (<amona.souliman1@gmail.com>)

### Getting started
Open the project with IntelliJ or Spring IDE

### Running the application
Run the server (`ChatRoom.java` located in the package `fr.istic.chat.server`)
Then run the client (`Main.java` located in the package `fr.istic.chat.client`)

Usernames and passwords are:
* titi => passtiti
* toto => passtoto
* test => testPassword

### Bugs
For some reason, we can not run RMI when "beaned"

```
Exception in thread "main" org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.remoting.rmi.RmiServiceExporter#0' defined in class path resource [server-beans.xml]: Invocation of init method failed; nested exception is java.rmi.server.ExportException: object already exported
```