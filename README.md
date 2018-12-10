# Hi LogMeIn !
You are welcome to check my code.
Just keep in mind that a part of the application was generated with JHipster 5.7.0 you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.7.0](https://www.jhipster.tech/documentation-archive/v5.7.0).

If check out the project you can build and run the easily by running the command: `mvnw` in the root level of the project

you can also see a demo at this adress: [https://basic-poker-app.herokuapp.com/#/](https://basic-poker-app.herokuapp.com/#/)

## USAGE
* log in the application by using admin account OR the predefined user account OR by create your own account
* Create a game with the `entity` menu with the `game` submenu .
* you can add player to a game with the `player` submenu and associate him to a game
* In the `Game` detailled page you can deal card to player

* You also use `API` submenu in `Administation` menu to play with swagger. (You have to be log as admin user)

I create some API to cover some exercise requirements but I havent call them in the UI.

When you create a game, A complete deck is create and associate to the game. The creator user is also associated to the game.

* To cover requirement of sorting suit or card left by suit I use SpringJPA on SQL database.
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
