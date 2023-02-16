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
- Executer la commande `mvn clean install` dans chacun des répertoires suivants (l'ordre est important):
  - `common`
  - `server`
  - `client`
- Lancer maintenant le serveur de la manière que vous voulez (jar, IDE, etc.)
- Lancer un ou plusieurs clients de la même manière
- Créer un compte
- Se connecter
- Profiter de l'application

## Fonctionnalités

Voici la liste des fonctionnalités implémentées dans l'application:

- Inscription
- Connexion
- Déconnexion
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
- Profil
  - Affichage des informations de l'utilisateur
  - Modification des informations de l'utilisateur

## Architecture

L'application java est décomposée en 3 packages distincts.

### Serveur

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