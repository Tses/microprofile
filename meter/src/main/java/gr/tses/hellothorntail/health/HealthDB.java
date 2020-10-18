
package gr.tses.hellothorntail.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class HealthDB implements HealthCheck  {
    @Override
    public HealthCheckResponse call()  {
        HealthCheckResponseBuilder  alive = HealthCheckResponse.named("DB");
        alive.withData("MySQL", "Fake");
        
        //return alive.up().build();
        return alive.down().build();
    }
}
