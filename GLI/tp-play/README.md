# TP Play

See instructions in the [wiki](https://github.com/julienrf/tp-play/wiki/TP-Play-%28Java%29).

### Group
* Thomas Daniellou (<thomas.danll@gmail.com>)
* Amona Souliman (<amona.souliman1@gmail.com>)

### Running the application
First, you need to run [our web service](https://github.com/tdaniellou/tp-istic/tree/master/TAA/taajerseygwt) with these commands (in separate terminals) :

```
./run-hsqldb-server.sh
mvn clean compile tomcat7:run
```

Then you can start Play with :

`./sbt` and type `run` in the terminal.

### Accessing the application
Go to <http://localhost:9000>.

### Running the tests
To run the tests, you first need to register a user via the application.

Then, in the `test/controllers/AuthenticationTest.java` file, just replace the values of `username` and `password` with the ones you used to register.

Finally, in a Play console, type `test` to launch the tests.

### Questions

##### Question 2
> Sachant que l’information sur le fait qu’un utilisateur est authentifié ou non est stocké dans sa session, et que dans Play les sessions sont stockés chez les clients (dans des cookies), est-ce que l’action de déconnexion peut être de type `GET` ? Pourquoi ?

Oui, l'action de déconnexion peut être de type `GET` car celui-ci ne doit pas modifier l'état de l'application. Vu que l'information sur l'authentification de l'utilisateur est stockée dans sa session (stockée côté client par Play), le changement de cette information n'aura pas d'impact sur l'application.


##### Question 9
> Que représentent les types de données `Promise` et `Observable` ? Quelle est la différence entre les deux ?

Une `Promise` est un type de données permettant de calculer et renvoyer un résultat sans être bloquant. Play va donc renvoyer le résultat seulement lorsque la `Promise` sera satisfaite.
Le client sera bloqué en attendant la réponse mais aucun blocage n'aura lieu côté serveur. Ainsi, l'interaction du serveur avec d'autres clients sera possible.

Un `Observable` est un objet qui va pouvoir emettre des données en continu. Ainsi, des `Observer`  vont pouvoir venir lire ses informations via des "sentinelles" qui attendent l'emission de données et réagissent au moment venu.
