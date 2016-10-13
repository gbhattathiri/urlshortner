# urlshortner
This is a Crude, basic url shortening web application.
Using Jax-rs, jersey and few other basic plugins.

Some of the known issues in the code at this point is
1) The logic used to store the account & password and also the url and counters are simple static maps.
2) The host name, error/success messages are hardcoded and not internationlized.
3) Not a lot of the validations are added.

Quick API paths for ease of use:

To Register:
http://localhost:8080/urlshortner/rest/register/<accountId>
To access the page:
http://localhost:8080/urlshortner/rest/tinyURL/<id> - for redirection

The above two links does not require any kind of authentication. 
But the following two links you would need to use Basic Authentication to access the url.
the username would be the accountid used to register and password will be the password return back as response on registration.

To add a new url:
http://localhost:8080/urlshortner/rest/tinyURL
Sample payload: {"url":"http://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/","redirectType":"302"}

To Fetch the stats of the list of url created for a particular account:
http://localhost:8080/urlshortner/rest/tinyURL/stats 

Future Enhancements:
1) Internationalization of error/success messages
2) Support for DB
