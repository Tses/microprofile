package io.microprofile.showcase.rest;

import java.util.Date;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.print.attribute.standard.MediaTray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

//@DeclareRoles({"Alumni"})
//@DenyAll


//@ApplicationScoped
@Path("/api")
public class Checker {



    @Inject
    @Claim(standard=Claims.iss)
    private ClaimValue<String>issuer;

    @Inject
    @Claim(value="exp",standard=Claims.iat)
    private Long timeClaim;
    
    @Context
    SecurityContext securityContext;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("Alumni")
    public String check(@Context SecurityContext securityContext){

        System.out.println("jwt:" + securityContext.getUserPrincipal());
        return "OK";
    }
    @Path("check")
    @RolesAllowed("Alumni")
    public String check(){
        return "OK";
    }

    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)    
    @Path("jwt")
    @RolesAllowed("Alumni")

    

    

   
    public String getjwt(){
        
        org.eclipse.microprofile.jwt.JsonWebToken j = (JsonWebToken) securityContext.getUserPrincipal();
        System.out.println("isuer1" + j.getIssuer());
        System.out.println("isuer2" + issuer.getValue());
        


        
        Date d = new Date(timeClaim * 1000);

        System.out.println("token date:" + d);


        System.out.println("is user in role :" + securityContext.isUserInRole("Alumni") );        
        
        
        return "OK";
    }
}
