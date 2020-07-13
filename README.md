Battleship
---

This project the game where's two players
create yourself fleet and fighting between
themselves.

How to start?
--- 

Come to place where you saved project and write in 
terminal next commands 

Download js dependencies `syarn install` and start 
frontend `yarn start`

If you want to start backend in terminal line use this command
`./gradlew bootRun`
or you can use java tools example 

```shell script
java -jar ~/projectDir/Battleship/build/libs/Battleship-1.0-SNAPSHOT.jar
```

Tree struct progect
---

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── by
│   │   │       └── deathsmell
│   │   │           └── battleship
│   │   │               ├── controller
│   │   │               │   └── MainController.java
│   │   │               ├── Main.java
│   │   │               ├── utils
│   │   │               │   └── Helper.java
│   │   │               └── Validators
│   │   │                   └── BattleFieldValidator.java
│   │   └── resources
│   │       ├── application.yaml
│   │       ├── static
│   │       │   └── js
│   │       │       ├── index.js
│   │       │       └── pages
│   │       │           └── App.vue
│   │       └── templates
│   │           └── index.html
│   └── test
│       ├── java
│       │   └── by
│       │       └── deathsmell
│       │           └── battleship
│       │               └── Validators
│       │                   ├── BattleFieldValidatorTest.java
│       │                   └── CodeWarsTests.java
│       └── resources
├── build.gradle
├── gradle.build
├── gradlew
├── gradlew.bat
├── package.json
├── settings.gradle
├── webpack
├── webpack.config.js
└── yarn.lock
```