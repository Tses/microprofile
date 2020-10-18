package gr.tses.hellothorntail.rest;

import java.net.URI;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import gr.tses.hellothorntail.model.Person;
import gr.tses.hellothorntail.parser.PersonParser;

@ApplicationScoped
@Path("/hello")
public class HelloWorldEndpoint {

    @Inject
    @ConfigProperty(name = "test.myprop")
    private String myprop1;

    @Inject
    @ConfigProperty(name = "test.myotherprop")
    private String myprop2;


    @Inject
    @ConfigProperty(name = "MY_OTHER")
    private String myprop3;    


    @Inject
    @ConfigProperty(name = "test.myperson")
    private Person myPerson;

    @Inject
    @ConfigProperty(name = "DBP1")
    private String fakeDB;

    

    @Inject
    private Config config;

    @Inject
    private PersonParser parser;

    @Context
    private HttpServletRequest servletRequest;

    final static Logger LOG = Logger.getLogger(HelloWorldEndpoint.class);

    @Context
    UriInfo uriInfo;

    @GET
    @Produces("text/plain")
    public Response doGet() {

        Optional<String> optionalValue = config.getOptionalValue("optional", String.class);

        optionalValue.ifPresent(v -> System.out.println(v));
        // optionalValue.ifPresentOrElse(v -> System.out.println(v), () ->
        // System.out.println("optional not exists"));

        System.out.println("absolutePath: " + uriInfo.getAbsolutePath());
        System.out.println("baseUri: " + uriInfo.getBaseUri());
        System.out.println("matchedResource: " + uriInfo.getMatchedResources());
        System.out.println("matchedUri: " + uriInfo.getMatchedURIs());
        System.out.println("path: " + uriInfo.getPath());
        System.out.println("pathParameters: " + uriInfo.getPathParameters());
        System.out.println("pathSegments: " + uriInfo.getPathSegments());
        System.out.println("queryParameters: " + uriInfo.getQueryParameters());
        System.out.println("requestUri: " + uriInfo.getRequestUri());
        System.out.println("relativize test: " + uriInfo.relativize(URI.create("test")));
        System.out.println("resolve test: " + uriInfo.resolve(URI.create("test")));

        return Response.ok("Hello from Thorntails! ->" + " fake db:" + fakeDB + " props:" + myprop1 + "," + myprop2 + " " + myprop3 + " Default person is "  + myPerson.getLastName()).build();
    }

    @POST

    @Path("/aloha")

    @Produces(MediaType.TEXT_PLAIN)

    @Consumes(MediaType.APPLICATION_JSON)
    public String hola(String json) {

        Person p = parser.parse(json);
        String hostname = servletRequest.getServerName();
        LOG.info(hostname);
        return String.format("Aloha mai %s %s from %s on %s", p.getFirstName(), p.getLastName(), p.getLocation(),
                hostname);
    }

    @PostConstruct
    private void init() {
        LOG.info("AlohaResource created!");
    }
}
