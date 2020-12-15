package gr.tses.hellothorntail.rest;

import java.net.URI;
import java.util.Optional;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.Timer;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;


import gr.tses.hellothorntail.model.Person;
import gr.tses.hellothorntail.parser.PersonParser;
import io.opentracing.tag.Tags;

import org.eclipse.microprofile.metrics.annotation.Gauge;

@ApplicationScoped
@Path("/hello")

public class HelloWorldEndpoint {

 

   

    @Inject
    private PersonParser parser;

    @Context
    private HttpServletRequest servletRequest;

    final static Logger LOG = Logger.getLogger(HelloWorldEndpoint.class);

    @Context
    UriInfo uriInfo;

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry baseRegistry;

    @Inject
    @Metric(name = "helloCounter",absolute = true)
    Counter metricCounter;

    @Inject
    @Metric(name = "helloTimer",absolute = true,unit = MetricUnits.SECONDS)
    Timer metricTimer;

    @Inject
    @Metric(name = "helloMeter",displayName = "A descriptive name",absolute = true,description = "A hello meter")
    //@Metric(name = "helloMeter",displayName = "A descriptive name",absolute = true,description = "A hello meter",tags = {"micro=profile","uni=systems"})
    Meter metricMeter;

    @GET
    @Produces("text/plain")
    public Response doGet() throws InterruptedException {

        metricCounter.inc();
        metricMeter.mark();
        Timer.Context context = metricTimer.time();
        Thread.sleep((new Random()).nextInt(1000));
        context.close();
        return Response.ok("Hello from Microprofile !").build();
    }

    Histogram h;
    @GET
    @Path("/hist")
    @Consumes(MediaType.TEXT_PLAIN)
    public String histogram(@QueryParam("i") String i){

        if (!baseRegistry.getMetadata().containsKey("HistogramProgrammatic")) {
            
            Metadata m = Metadata.builder().withName("HistogramProgrammatic").build();        

            //h = baseRegistry.histogram(m,new Tag("mysuper","histogrammetric"),new Tag("amicroprofile","helloworld"));            
            h = baseRegistry.histogram(m);            
        }
        h.update(Integer.parseInt(i));
        return i;
    }

    @POST
    @Path("/aloha")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "hola-count",reusable = true,absolute = true)
    @Metered(name = "hola-metered",absolute = true)
    @Timed(name = "hola-timed",absolute = true,unit = MetricUnits.SECONDS)
    @Consumes(MediaType.APPLICATION_JSON)
    public String hola(String json)  throws InterruptedException{

        Thread.sleep((new Random()).nextInt(1000));

        Person p = parser.parse(json);
        String hostname = servletRequest.getServerName();
        LOG.info(hostname);
        return String.format("Aloha mai %s %s from %s on %s", p.getFirstName(), p.getLastName(), p.getLocation(),
                hostname);
    }


    @POST
    @Path("/ahola")
    @Produces(MediaType.TEXT_PLAIN)
    //@Counted(name = "hola-count",reusable = true,absolute = true)
    @Consumes(MediaType.APPLICATION_JSON)
    public String ahola(String json) {
        
        Person p = parser.parse(json);
        
        return String.format("Ahola mai %s %s from %s", p.getFirstName(), p.getLastName(), p.getLocation());   }    

    @PostConstruct
    private void init() {
        LOG.info("AlohaResource created!");
    }

    @Gauge(unit = MetricUnits.NONE,absolute = true)
    public Integer getGauge() {
        return (new Random()).nextInt(1000);
        
    }

}
