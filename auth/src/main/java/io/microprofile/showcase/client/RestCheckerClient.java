package io.microprofile.showcase.client;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
@PermitAll
@Path("/cli")
public class RestCheckerClient {


    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String callChecker(){


        System.out.println("XXX");
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:8080/");
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
        AuthTokenService service = rtarget.proxy(AuthTokenService.class);

        
        String token = service.getToken("{ \"username\": \"alumni\", \"password\": \"alumni-secret\" }");
        System.out.println("Received Token:" + token);
        //client.close();

        Client client2 = ClientBuilder.newClient();
        client2.register(new AuthHeadersRequestFilter(token));
        WebTarget target2 = client2.target("http://localhost:8080/api/");
        ResteasyWebTarget rtarget2 = (ResteasyWebTarget) target2;
        CheckerService checkerService = rtarget2.proxy(CheckerService.class);
        checkerService.getjwt();
        System.out.println(token);
        //client2.close();

        return "OK";
    }

}
