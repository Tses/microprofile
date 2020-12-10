package gr.tses.hellothorntail.rest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
@Path("/hello")
public class HelloWorldEndpoint {

    final static Logger LOG = Logger.getLogger(HelloWorldEndpoint.class);

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello hard coded").build();
    }


}
