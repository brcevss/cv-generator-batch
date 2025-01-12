# Générateur de CV

## Description

Le projet CV Generator Batch est une application qui génère des CV au format PDF à partir de données contenues dans un fichier CSV. L'application utilise Spring Batch pour le traitement des données, et OpenAI GPT-4 pour générer des résumés professionnels à partir des compétences d'un candidat.  

L'application peut être utilisée pour automatiser la création de CV en fonction des informations d'un fichier CSV et générer des textes personnalisés avec l'aide de l'IA.

## Fonctionnalités

- Lecture de données à partir d'un fichier CSV (nom, prénom, email, compétences).  
- Génération d'un fichier PDF pour chaque candidat avec ses informations et un résumé généré par GPT-4.  
- Utilisation de Spring Batch pour gérer les étapes de traitement par lots.  
- Intégration avec l'API OpenAI pour générer des résumés professionnels.  

## Prérequis

- Java 17
- Maven pour la gestion des dépendances et la construction du projet
- Spring Boot pour la configuration de l'application
- API Key OpenAI pour accéder à l'API GPT-4


## Installation

### 1. Cloner le repository

Clonez le repository Git :

```
git clone https://github.com/ton-utilisateur/cv-generator-batch.git
cd cv-generator-batch
```

### 2. Configuration de l'API Key OpenAI

Dans le fichier application.properties, ajoutez votre clé API OpenAI :  

```
api.key=VOTRE_CLE_API_OPENAI
```
Assurez-vous que ce fichier est ajouté à votre .gitignore pour éviter de le pousser vers le dépôt Git.

### 3. Construire le projet

```
mvn clean install -X
```

### 4. Lancer l'application

```
mvn spring-boot:run
```

## Utilisation

### 1. Format du fichier CSV

Le fichier CSV doit être structuré comme suit :

```
prenom,nom,email,competences
Jean,Dupont,jean.dupont@example.com,"Java,Spring,SQL"
Marie,Durand,marie.durand@example.com,"Python,Django,MySQL"
```

### 2. Génération des CV

L'application lit le fichier CSV, extrait les informations des candidats, et génère un CV au format PDF pour chaque ligne du fichier. Le fichier PDF est sauvegardé dans le répertoire de travail avec le nom suivant :

```
<Nom>_<Prénom>_CV.pdf
```

### 3. Résumé généré par GPT-4

Le résumé pour chaque candidat est généré en envoyant une requête à l'API OpenAI GPT-4. Ce résumé est basé sur les compétences spécifiées pour chaque candidat dans le fichier CSV.

## Tests

### 1. Tests unitaires et d'intégration

Les tests sont organisés de la manière suivante :

- Tests unitaires : Vérifient le comportement des composants individuels (par exemple, la logique de génération du PDF).
- Tests d'intégration : Vérifient l'intégration entre les composants du système, y compris l'interaction avec l'API OpenAI et la génération de fichiers PDF.

Vous pouvez exécuter les tests avec la commande suivante :

```
mvn test
```

### Structure du projet

Voici une vue d'ensemble de la structure des packages de l'application :  

- model/ : Contient les classes de modèles de données.
- service/ : Contient les services pour générer du texte via l'API.
- jobs/ : Contient les jobs Spring Batch pour le traitement des données.
- writer/ : Contient les composants responsables de l'écriture des résultats.
- configuration/ : Contient la configuration de Spring Batch.
- unit/ : Contient les tests unitaires pour les composants de l'application.
- integration/ : Contient les tests d'intégration qui vérifient le bon fonctionnement des interactions entre les composants.

## Technologies utilisées

- Spring Boot : Framework pour créer des applications Java basées sur le concept de microservices.  
- Spring Batch : Framework pour traiter des volumes massifs de données en utilisant des traitements par lots.  
- OpenAI GPT-4 : Utilisé pour générer des résumés professionnels.  
- iText : Bibliothèque pour générer des fichiers PDF en Java.    

## Limitations
- Quota API : L'usage de l'API OpenAI est limité par un quota d'usage. Assurez-vous d'avoir un plan avec suffisamment de crédits.  
- Tests d'intégration : Certains tests d'intégration nécessitent une connexion Internet pour interagir avec l'API OpenAI.   
