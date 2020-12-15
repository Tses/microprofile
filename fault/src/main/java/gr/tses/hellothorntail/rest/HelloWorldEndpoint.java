package gr.tses.hellothorntail.rest;

import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import gr.tses.hellothorntail.helper.Helper;
import gr.tses.hellothorntail.model.State;

@ApplicationScoped
@Path("/")

public class HelloWorldEndpoint {

    @Inject
    State state;

    @Inject
    Helper helper;
    
    @GET
    @Path("/status")
    public Response status(@QueryParam("e") String exception,@QueryParam("t") String timeout) throws InterruptedException {        
        state.setException(Integer.parseInt(exception));
        state.setTimeout(Integer.parseInt(timeout));
        return Response.ok("TimeOut:" + timeout + " Exception:" + exception).build();       
    }    
    
    @GET
    @Path("/retry")
    public Response retry() throws InterruptedException {       
        String s;
        try {
            s = helper.callRetry();    
        } catch (Exception e) {
            return Response.status(500).entity("NOT OK").build();           
        }        
        return Response.ok("OK " + s).build();            
    }

    @GET
    @Path("/timeout")
    public Response timout() throws InterruptedException {       
        String s;
        try {
            s = helper.callTimeout();    
        } catch (Exception e) {
            return Response.status(500).entity("NOT OK").build();           
        }        
        return Response.ok("OK " + s).build();       
    }    

    @GET
    @Path("/fallback")
    public Response fallback() throws InterruptedException {       
        String s;
        try {
            s  = helper.callTimeoutWithFallback();    
        } catch (Exception e) {
            return Response.status(500).entity("NOT OK").build();           
        }        
        return Response.ok("OK " + s).build();       
    }    

    @GET
    @Path("/bulkhead")
    public Response bulkhead(@QueryParam("async") String async) throws InterruptedException {       
        String s;
        try {
            if ("1".equals(async)){
                Future<String> f  = helper.serviceA();  
                System.out.println("Waiting fake process to complete ...");              
                // while(!f.isDone()) {                    
                //     Thread.sleep(100);
                // }
                s = f.get();
            } else {
                s  = helper.callBilkHead();    
            }
            System.out.println("got result "  + s);
        } catch (Exception e) {
            return Response.status(500).entity("NOT OK").build();           
        }        
        return Response.ok("OK " + s).build();       
    }   

    @GET
    @Path("/circuit")
    public Response circuit() throws InterruptedException {       
        String s;
        try {
            s  = helper.callCircuit();    
        } catch (Exception e) {
            return Response.status(500).entity("NOT OK" + e.getClass().getName()).build();           
        }        
        return Response.ok("OK " + s).build();       
    }    
}
