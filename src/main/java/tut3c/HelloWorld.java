package tut3c;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
* A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
* is enabled
*
* @author gbrey@redhat.com
*
*/

@Path("/")
public class HelloWorld {

    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        return "result";
    }

}