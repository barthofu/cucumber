# Cucumber

Cucumber est une application de rencontre spécialisée dans le speed dating, qui offre une expérience de rencontre rapide et efficace pour les utilisateurs.

Il s'agit d'un projet de groupe réalisé dans le cadre de la Licence Pro DevOps à l'IUT Lyon 1. L'objectif était de réaliser une application native en Java comportant ces caractéristiques au minimum:
- Base de données
- Architecture client/serveur
- Sockets

## Auteurs

- [Bartholomé GILI](mailto:dev.bartho@gmail.com)
- [Noé FAVIER](mailto:noe.favier@etu.univ-lyon1.fr)

## Installation

### Prérequis

- Java 18
- Maven 3

### Installation

- Cloner le dépôt
- Lancer la commande `mvn clean install` à la racine du projet
- Lancer le serveur avec la commande `java -jar server/target/server-1.0-SNAPSHOT.jar`
- Lancer un ou plusieurs clients avec la commande `java -jar client/target/client-1.0-SNAPSHOT.jar`
- Créer un compte
- Se connecter
- Profiter de l'application

## Fonctionnalités

Voici la liste des fonctionnalités implémentées dans l'application:

- Inscription
- Connexion
- Affichage en temps réel du nombre d'utilisateurs connectés
- Chat
  - File d'attente
  - Assignation automatique avec un autre utilisateur en attente
  - Chrono
  - Envoi de messages
  - Affichage des messages
- Système de favoris
- Elements sauvegardés en base de données:
  - Messages
  - Utilisateurs
  - "Chat room" (canal de discussion chronométré entre deux utilisateurs aléatoires)
  - Favoris

## Architecture

L'application java est décomposée en 3 packages distincts.

### Server

Le serveur applicatif auquel les différents clients vont se connecter. Il va threader chaque connexion et veille à la bonne gestion et inter-communication des clients.

Voici son architecture :

![Server architecture](https://i.imgur.com/Dg93Wc3.png)

### Client

Il s’agit de l’application avec interface graphique dont disposent les utilisateurs finaux.

Voici son architecture :

![Client architecture](https://i.imgur.com/5Kp2gCd.png)

### Common

Enfin, le package common contient les classes/interfaces/objets communs aux deux
applications (client et serveur).


## Todo
- [x] Improve auth: don't permit login if user is already logged in
- [x] Readme

### Bonus
- [ ] When server disconnects, redirects to a error 500 page with a "Close" button
- [ ] If server is unreachable when opening client, show an error page
- [ ] Improve favorites: both users should add each other to validate the fav
- [ ] Profile page
- [ ] Refactor authentication so it uses hashes instead of bcrypt
