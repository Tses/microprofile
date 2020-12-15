
package gr.tses.hellothorntail.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import gr.tses.hellothorntail.model.State;

@Health
@ApplicationScoped
public class HealthTimeout implements HealthCheck {
    @Inject
    State state;

    @Override
    public HealthCheckResponse call()  {
        HealthCheckResponseBuilder  alive = HealthCheckResponse.named("Timeout");
        alive.withData("Processing time",  state.getTimeout());
        
        return alive.up().build();
        
    }
}
