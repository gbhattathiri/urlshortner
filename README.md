# urlshortner
This is a Crude, basic url shortening web application.<br>
Using Jax-rs, jersey and few other basic plugins.<br>
<br>
# Some of the known issues in the code at this point is<br>
1) The logic used to store the account & password and also the url and counters are simple static maps.<br>
2) The host name, error/success messages are hardcoded and not internationlized.<br>
3) Not a lot of the validations are added.<br>
<br>
# Quick API paths for ease of use:
<br>
<b>To Register:</b> <br>
http://localhost:8080/urlshortner/rest/register/"-accountId-" <br>
<b>To access the page:</b> <br>
http://localhost:8080/urlshortner/rest/tinyURL/"-id-" - for redirection
<br>
<br>
The above two links does not require any kind of authentication. <br>
<br>
<br>
But the following two links you would need to use Basic Authentication to access the url.<br>
the username would be the accountid used to register and password will be the password return back as response on registration.<br>
<br>
<b>To add a new url:</b><br>
http://localhost:8080/urlshortner/rest/tinyURL<br>
Sample payload: {"url":"http://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/","redirectType":"302"}<br>
<br>
<b>To Fetch the stats of the list of url created for a particular account:</b><br>
http://localhost:8080/urlshortner/rest/tinyURL/stats <br>

# Future Enhancements:
1) Internationalization of error/success messages<br>
2) Support for DB<br>
