Battleship
===

This project the game where's two players
create yourself fleet and fighting between
themselves.

How to start?
==
Come to place where you saved project and write in 
terminal next commands 

Developer mode
--

In this mode all changes than you do auto-reload. 
You don't need to reload frontend modules. 

1. Download javascript dependencies `yarn install` and start 
frontend `yarn start`

2. If you want to start backend in terminal line use this command
`./gradlew bootRun` or you can use java tools example 

```shell script
java -jar ~/projectDir/Battleship/build/libs/Battleship-1.0-SNAPSHOT.jar -D spring.profiles.active=dev
```

Production mode
-- 

This mode started two command line after download repository:


Tree struct project
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