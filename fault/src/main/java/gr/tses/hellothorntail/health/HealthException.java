
package gr.tses.hellothorntail.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import gr.tses.hellothorntail.helper.Helper;
import gr.tses.hellothorntail.model.State;

@Health
@ApplicationScoped
public class HealthException implements HealthCheck {


    @Inject 
    State state;

    @Override
    public HealthCheckResponse call()  {
        HealthCheckResponseBuilder  alive = HealthCheckResponse.named("isrunning");
        if (state.getException() == 1){
            return alive.down().build();
        } else {
            return alive.up().build();
        }
        
        
    }
}
