## 🌤️ WeatherApp - Une application météo multiplateforme avec Kotlin Multiplatform

> ⚠️ **Note importante**: Cette application est actuellement **en début de développement**. De nombreuses fonctionnalités sont encore en cours d'implémentation et des changements majeurs peuvent survenir. N'hésitez pas à contribuer à son évolution !
> 

WeatherApp est une application météo moderne qui utilise Kotlin Multiplatform pour offrir une expérience utilisateur cohérente sur plusieurs plateformes (Android, Desktop et Web via WebAssembly). L'application exploite l'API OpenWeatherMap pour fournir des informations météorologiques précises et à jour.

## ✨ Fonctionnalités
- **Prévisions météo actuelles** : Température, humidité, vitesse du vent
- **Infos complémentaires** : Lever et coucher du soleil, prévisions sur plusieurs jours
- **Interface utilisateur intuitive** en Compose Multiplatform
- **Support multiplateforme** :
    - 📱 Android
    - 💻 Desktop (Windows, macOS, Linux)
    - 🌐 Web (via WebAssembly)

## 🚧 État du projet
Ce projet est en **phase initiale de développement**. Voici l'état actuel :
- ✅ Structure du projet mise en place
- ✅ Intégration avec l'API OpenWeatherMap
- ✅ Interface utilisateur de base
- ⏳ Prévisions sur plusieurs jours (en cours)
- ⏳ Géolocalisation (à venir)
- ⏳ Stockage local des données (à venir)
- ⏳ Notifications météo (à venir)
- etc..

## 🛠️ Technologies utilisées
- **Kotlin Multiplatform** : Partage de logique métier entre plateformes
- **Compose Multiplatform** : UI déclarative pour toutes les plateformes
- **Ktor** : Client HTTP pour les appels API
- **Kotlinx.serialization** : Sérialisation/désérialisation JSON
- **Kotlinx.datetime** : Gestion des dates compatible multiplateforme
- **WebAssembly (Wasm)** : Support web moderne et performant

## 🚀 Installation
### Prérequis
- Android Studio Arctic Fox ou plus récent
- JDK 17 ou plus récent
- Kotlin 1.9+ avec support Kotlin Multiplatform

### Configuration de l'API
1. Créez un compte sur [OpenWeatherMap](https://openweathermap.org/) et obtenez une clé API
2. Ajoutez votre clé API et l'URL de base dans un fichier `secret.properties` à la racine du projet
``` 
baseUrl=https://api.openweathermap.org/data/2.5
apiKey=VOTRE_CLE_API
```
### Compilation
#### Android
``` bash
./gradlew assembleDebug
```
#### Desktop
``` bash
./gradlew runDesktop
```
#### Web (WebAssembly)
``` bash
./gradlew wasmJsBrowserRun
```
## 🧩 Architecture
L'application suit une architecture propre avec séparation des préoccupations :
- **network** : API et services pour la récupération des données météo
- **model** : Classes de données partagées entre plateformes
- **utils** : Utilitaires pour la conversion et le formatage des données
- **ui** : Composants UI construits avec Compose Multiplatform

La gestion des spécificités de chaque plateforme est assurée par le mécanisme `expect/actual` de Kotlin.
## 🔄 Contribution
Les contributions sont les bienvenues ! Étant donné que le projet est en début de développement, c'est le moment idéal pour apporter vos idées et votre expertise. Voici comment procéder :
1. Forkez le projet
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/amazing-feature`)
3. Committez vos changements (`git commit -m 'Add some amazing feature'`)
4. Poussez vers la branche (`git push origin feature/amazing-feature`)
5. Ouvrez une Pull Request

N'oubliez pas de ne jamais inclure vos clés API ou autres informations sensibles dans vos commits.

## 📝 Feuille de route
- Amélioration de l'interface utilisateur
- Mode hors ligne
- Widgets pour Android
- Tests unitaires et d'intégration

## 📜 Licence
Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🙏 Remerciements
- [OpenWeatherMap](https://openweathermap.org/) pour leur API météo
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) pour la possibilité de partager du code entre plateformes
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) pour l'UI déclarative multiplateforme

Développé avec ❤️ et ☕
