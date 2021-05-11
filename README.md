Dummy Java Application Using based On Jhipster

## USAGE
* log in the application by using admin account OR the predefined user account OR by create your own account
* Create a game with the `entity` menu with the `game` submenu .
* you can add player to a game with the `player` submenu and associate him to a game
* In the `Game` detailled page you can deal card to player

* You also use `API` submenu in `Administation` menu to play with swagger. (You have to be log as admin user)

I create some API to cover some exercise requirements but I havent call them in the UI. 
To Call those API via SWAGGER you have to be logged as admin in dev mode (checkout project and run mvnw).

When you create a game, A complete deck is create and associate to the game. The creator user is also associated to the game.

* I generate the database model with JDLStudio [https://start.jhipster.tech/jdl-studio/](https://start.jhipster.tech/jdl-studio/), 
have a look on file `basic-poker-game.jdl` at the project root level

## Technical Stack
### Technology stack on the client side

Single Web page application:

* Angular 6
* Responsive Web Design with Twitter Bootstrap
* HTML5 Boilerplate
* Compatible with modern browsers (Chrome, FireFox, Microsoft Edge…)
* SWAGGER

### Technology stack on the server side

A complete Spring application:

* Spring Boot for easy application configuration
* Maven configuration for building, testing and running the application
* “development” and “production” profiles
* MapStruct to generate DTO 
* Spring Security
* Spring MVC REST + Jackson
* Spring Data JPA + Bean Validation
* Database updates with Liquibase
* Hibernate
* Ehcache
* H2 Data Base for development purpose

* MySQL database in production mode
