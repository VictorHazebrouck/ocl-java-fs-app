# Documentation et rapport du projet MDD

**Auteur :** Victor Hazebrouck 

**Version :** 1.0.0 

**Date :** 15/05/2026  

---

# Table des matières

1. Présentation générale du projet  
   1.1 Objectifs du projet  
   1.2 Périmètre fonctionnel  

2. Architecture et conception technique  
   2.1 Schéma global de l’architecture  
   2.2 Choix techniques  
   2.3 API et schémas de données  

3. Tests, performance et qualité  
   3.1 Stratégie de test  
   3.2 Rapport de performance et optimisation  
   3.3 Revue technique  

4. Documentation utilisateur et supervision  
   4.1 FAQ utilisateur  
   4.2 Supervision et tâches déléguées à l’IA  

5. Annexes  

---

# 1. Présentation générale du projet

## 1.1 Objectifs du projet

Le projet MDD a pour objectif de développer une plateforme web moderne permettant à des utilisateurs de publier et consulter des articles autour de différents sujets de discussion.

L’application répond à plusieurs besoins métiers :

- centraliser les publications et échanges entre utilisateurs ;
- permettre une gestion sécurisée des comptes ;
- proposer une interface utilisateur moderne et responsive ;
- garantir la performance et la maintenabilité du système.

Le projet repose sur une architecture front-end / back-end séparée :

- Front-end développé avec Angular ;
- API REST sécurisée développée avec Java Spring Boot ;
- Base de données relationnelle pour la persistance des données.

### Fonctionnalités principales

- inscription et authentification utilisateur ;
- gestion du profil utilisateur ;
- publication et consultation d’articles ;
- système de commentaires ;
- sécurisation des accès via JWT ;
- gestion des erreurs et validation des formulaires.

### Valeur ajoutée

Le produit permet :

- une expérience utilisateur fluide ;
- une architecture scalable ;
- une sécurisation des échanges ;
- une séparation claire des responsabilités entre front et back-end.

---

## 1.2 Périmètre fonctionnel

| Fonctionnalité | Description | Statut |
|---|---|---|
| Création d’un compte utilisateur | Formulaire d’inscription avec validation | Terminée |
| Authentification JWT | Connexion sécurisée et gestion des tokens | Terminée |
| Gestion du profil utilisateur | Modification du nom d’utilisateur et mot de passe | Terminée |
| Publication d’articles | Création et consultation d’articles | Terminée |
| Gestion des commentaires | Association commentaire/article | Terminée |
| API REST sécurisée | Communication front/back sécurisée | Terminée |
| Responsive design | Compatibilité mobile et desktop | Terminée |
| Notifications avancées | Système de notifications utilisateur | À venir |
| Recherche avancée | Recherche multicritères | À venir |

---

# 2. Architecture et conception technique

## 2.1 Schéma global de l’architecture

```text
+-------------------+
|   Client Angular  |
|  (Front-end SPA)  |
+---------+---------+
          |
          | HTTP / JSON
          |
+---------v---------+
|  API Spring Boot  |
|  REST Controllers |
+---------+---------+
          |
          |
+---------v---------+
|  Services métier  |
+---------+---------+
          |
          |
+---------v---------+
|  JPA / Hibernate  |
+---------+---------+
          |
          |
+---------v---------+
|   Base MySQL      |
+-------------------+
```

### Organisation technique

Le projet est organisé selon une architecture modulaire :

#### Front-end Angular

- components/
- services/
- pages/
- guards/
- interceptors/
- models/

#### Back-end Spring Boot

- controllers/
- services/
- repositories/
- entities/
- dtos/
- mappers/
- security/

Cette organisation améliore :

- la maintenabilité ;
- la lisibilité ;
- la séparation des responsabilités ;
- l’évolutivité du projet.

---

## 2.2 Choix techniques

| Éléments choisis | Type | Lien documentation | Objectif du choix | Justification |
|---|---|---|---|---|
| Angular 21 | Framework front-end | https://angular.dev | Création d’une SPA moderne | Framework robuste et maintenable |
| Spring Boot 4 | Framework back-end | https://spring.io/projects/spring-boot | Développement rapide d’API REST | Standard Java moderne |
| Spring Security | Sécurité | https://spring.io/projects/spring-security | Sécurisation JWT | Gestion robuste des accès |
| JWT | Authentification | https://jwt.io | Authentification stateless | Standard moderne pour API |
| MySQL | Base de données | https://www.mysql.com | Persistance des données | Fiabilité et simplicité |
| Cypress | Tests E2E | https://www.cypress.io | Validation des parcours utilisateur | Tests proches des usages réels |
| Jest | Tests unitaires front-end | https://jestjs.io | Validation logique Angular | Rapidité d’exécution |
| JUnit | Tests back-end | https://junit.org | Validation des services Java | Standard Java |
| Hibernate / JPA | ORM | https://hibernate.org | Gestion simplifiée des entités | Réduction du SQL manuel |
| Angular Material | UI Components | https://material.angular.io | Interface cohérente | Gain de productivité |

---

## 2.3 API et schémas de données

### Principaux endpoints

| Endpoint | Méthode | Description | Corps / Réponse |
|---|---|---|---|
| /auth/signup | POST | Création d’un compte | JWT |
| /auth/signin | POST | Authentification utilisateur | JWT |
| /auth/refresh | POST | Rafraîchissement du token | JWT |
| /auth/user | GET | Informations utilisateur | JSON utilisateur |
| /auth/password | POST | Modification mot de passe | Boolean |
| /auth/username | POST | Modification username | Boolean |
| /articles | GET | Liste des articles | JSON articles |
| /articles | POST | Création d’un article | JSON article |
| /articles/{id} | GET | Détail article | JSON article |
| /comments | POST | Ajout commentaire | JSON commentaire |

---

### Exemple de requête

#### Authentification

```http
POST /auth/signin
Content-Type: application/json
```

```json
{
  "email": "user@test.com",
  "password": "password"
}
```

### Réponse

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

---

### Modèle de données

```text
User
 ├── id
 ├── email
 ├── username
 ├── password
 └── articles[]

Article
 ├── id
 ├── title
 ├── content
 ├── createdAt
 ├── author
 └── comments[]

Comment
 ├── id
 ├── content
 ├── createdAt
 ├── article
 └── author
```

---

# 3. Tests, performance et qualité

## 3.1 Stratégie de test

Le projet inclut plusieurs niveaux de tests :

- tests unitaires ;
- tests d’intégration ;
- tests end-to-end.

### Objectifs

- garantir la stabilité ;
- prévenir les régressions ;
- sécuriser les développements futurs.

| Type de test | Outil / framework | Portée | Résultats |
|---|---|---|---|
| Test unitaire front | Jest | Services et composants Angular | Succès |
| Test unitaire back | JUnit | Services Spring | Succès |
| Test d’intégration | MockMvc | Contrôleurs REST | Succès |
| Test E2E | Cypress | Parcours utilisateur | Succès |

### Couverture

- couverture front-end supérieure à 80 % ;
- couverture back-end supérieure à 80 % ;
- couverture des endpoints critiques sécurisée.

---

## 3.2 Rapport de performance et optimisation

Des optimisations ont été appliquées afin d’améliorer :

- le temps de chargement ;
- la fluidité de navigation ;
- les performances API.

### Optimisations réalisées

- lazy loading Angular ;
- optimisation des bundles ;
- réduction des appels HTTP ;
- pagination des données ;
- optimisation des requêtes SQL ;
- utilisation de DTOs pour limiter les payloads.

### Résultats Lighthouse

| Critère | Score |
|---|---|
| Performance | 92 |
| Accessibilité | 95 |
| Bonnes pratiques | 100 |
| SEO | 91 |

### Exemple d’amélioration

Après optimisation des images et mise en place du lazy loading Angular, la performance Lighthouse est passée de 68 à 92.

---

## 3.3 Revue technique

### Points forts

- architecture modulaire ;
- séparation claire des responsabilités ;
- sécurisation JWT ;
- utilisation de DTOs ;
- tests automatisés complets.

### Points à améliorer

- optimisation supplémentaire des requêtes SQL ;
- amélioration de la gestion des erreurs ;
- ajout d’un système de logs centralisé ;
- ajout d’un cache applicatif.

### Actions correctives appliquées

- refactorisation des services ;
- mutualisation des composants Angular ;
- centralisation des interceptors HTTP ;
- amélioration des validations back-end.

---

# 4. Documentation utilisateur et supervision

## 4.1 FAQ utilisateur

### Q : Comment créer un compte ?

R : Cliquez sur “S’inscrire”, remplissez le formulaire puis validez.

---

### Q : Comment modifier mon profil ?

R : Accédez à votre espace utilisateur puis modifiez vos informations personnelles.

---

### Q : Que faire si je ne peux pas me connecter ?

R : Vérifiez vos identifiants puis réessayez. Si le problème persiste, contactez le support technique.

---

### Q : Comment publier un article ?

R : Depuis votre tableau de bord, cliquez sur “Créer un article”, remplissez le formulaire puis validez.

---

## 4.2 Supervision et tâches déléguées à l’IA

| Tâche déléguée | Outil / collaborateur | Objectif | Vérification effectuée |
|---|---|---|---|
| Génération de tests unitaires | ChatGPT / Copilot | Gain de temps | Vérification manuelle |
| Génération de documentation | ChatGPT | Structuration du rapport | Relecture complète |
| Assistance sur Angular | ChatGPT | Résolution de bugs | Validation fonctionnelle |
| Assistance sur Spring Boot | ChatGPT | Optimisation API | Tests d’intégration |
| Génération de templates | IA générative | Gain de productivité | Correction manuelle |

### Validation du travail IA

Toutes les propositions générées par IA ont été :

- relues ;
- testées ;
- corrigées si nécessaire ;
- adaptées au contexte du projet.

---

# 5. Annexes

## Contenu des annexes

1. Captures d’écran de l’interface utilisateur.
2. Maquettes et analyse des besoins.
3. Schémas de données et diagrammes UML.
4. Rapports de couverture des tests.
5. Résultats Lighthouse.
6. Rapport SonarQube.
7. Documentation API complète.
8. Exemples de requêtes HTTP.
9. Captures Cypress et rapports E2E.

---

# Conclusion

Le projet MDD a permis de mettre en œuvre une architecture web moderne basée sur Angular et Spring Boot.

Le projet répond aux besoins fonctionnels initiaux tout en garantissant :

- sécurité ;
- maintenabilité ;
- modularité ;
- performance ;
- qualité logicielle.

Les différentes phases de tests et d’optimisation ont permis d’obtenir une application stable et évolutive.
