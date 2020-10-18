package io.microprofile.showcase.rest;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.print.attribute.standard.MediaTray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@DeclareRoles({"Alumni"})
//@DenyAll
@ApplicationScoped
@Path("/api")
public class Checker {
    


    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)


    @RolesAllowed("Alumni")
    public String check(@Context SecurityContext securityContext){

        System.out.println("jwt:" + securityContext.getUserPrincipal());
        return "OK";
    }
}
