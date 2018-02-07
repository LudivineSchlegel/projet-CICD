# Projet intégration continue

<h2>Bases</h2>

Projet se basant sur la documentation de déploiement d'une application web en Java avec HEROKU:

https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku

Dépôt original: https://github.com/heroku/gradle-getting-started

Intégration continue avec <a href="https://travis-ci.org/">Travis CI</a>

Lien : https://travis-ci.org/LudivineSchlegel/projet-CICD

<h2>Intégration continue</h2>

Intégration continue du dépôt github vers le gitHeroku puis création de l'application.
Création d'un fichier <a href="https://github.com/LudivineSchlegel/projet-CICD/blob/master/.travis.yml">.travis.yml</a>

3 étapes de déploiements organisées de la façon suivante :

```
stages:
  - install
  - test
  - name: deploy
    if: branch = master
```
  - install
  
Compilation de l'application.

Commande : 

```script: ./gradlew assemble```

  - test
  
Test du bon fonctionnement de l'application.

Commande :

```script: ./gradlew check```

Et en parallèle test de fonctionnement scripte:

Commande :

```script: echo "test en parallele"```

  - deploy
  
Déploiement sur HEROKU si sur branche master.

Commande  :

 ``` 
    script: skip
    deploy: &heroku
       provider: heroku
       api-key: 
        secure: $HEROKU_API_KEY
       app: limitless-ravine-55613
```
avec $HEROKU_API_KEY une variable d'environnement définie dans Travis CI

<img src="Capture du 2018-02-05 10-36-46.png" alt="logo git"/>

<h2>Problèmes rencontrés</h2>

Principalement la prise en main de TRAVIS Ci : 

* documentation faible, peu d’exemples
* partie "stage" et "job" très peu détailler et expliquer (éléments en version béta)
* documentation pour trouver comment faire une clef sécurisée sous HEROKU et l'intégrer dans Travis CI difficile à trouver. Donc déploiement de Travis CI vers HEROKU difficile à mettre en place.
