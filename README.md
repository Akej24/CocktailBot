# Cocktail Bot

Cocktail Bot is an application based on JDA (Java Discord API) that connects to an external public https://www.thecocktaildb.com api, 
so when some commands do not return any result, it is possible that the api contains gaps that prevent the execution of individual functionalities 
on specific drinks (e.g. a drink may not have <br/> an image or a recipe)

The application has commands such as:<br/>
**!help** - _Shows a list of available commands_<br/>
**!random** - _Draws a random drink_<br/>
**!random -a** - _Draws a random alcoholic drink_<br/>
**!random -na** - _Draws a random non-alcoholic drink_<br/>
**!recipe \<drink name>** - _Shows a recipe for the given drink_<br/>
**!ingredient \<ingredient name>** - _Shows information for the given drink_<br/>
**!favourites** - _Shows a list of favourite drinks (max 50)_<br/>
**!suggest \<username> \<drink name>** - _Suggest the user the given drink (max 50)_<br/>
**!showsuggested** - _Shows a list of suggested drinks_<br/>
**!accept \<drink name>** - _Accepts the given drink and move it to 'to try' category_<br/>
**!reject \<drink name>** - _Rejects the given drink and remove it from suggested_<br/>
**!totry** - _Shows a list of drinks to try (max 50)_<br/>

To add drink to favourites click heart emote ❤ under recipe for the given drink and to remove drink from favourites just click cross emote ❌.
<br/><br/>

# Technologies

The application was created based on the following technologies:
- Java
- Spring Boot
- Redis
- Unit and Integration Tests

Some libraries used for this project:
- Slf4j
- Lombok
- JDA (Java Discord API)
- Json
- JUnit/Mockito
<br/><br/>

# How to run application?

To run application u need to create `token.txt` in the following places and paste your discord bot token into them (CocktailBot is root directory):

`/CocktailBot/token.txt`<br/>
`/CocktailBot/monolith/src/test/resources/token.txt`

Commands:<br/>
`$ git clone https://github.com/Akej24/CocktailBot` <br/>
`$ docker build -t cocktail-bot:1.0 .` <br/>
`$ docker pull redis:7.0` <br/>
`$ docker-compose up` <br/>