package gr.tses.hellothorntail.helper;

import java.util.concurrent.CompletableFuture;

import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

import gr.tses.hellothorntail.model.State;

@ApplicationScoped
public class Helper {

    @Inject
    State state;

    @Retry(maxRetries = 5, delay = 1000, retryOn = { RuntimeException.class })
    public void callRetry() throws InterruptedException {
        state.call();
    }

    @Timeout(value = 1000)
    public void callTimeout() throws InterruptedException {
        state.call();
    }

    @Timeout(value = 1000)
    @Fallback(fallbackMethod = "fallback")
    public String callTimeoutWithFallback() throws InterruptedException {
        
        return state.call();
    }

    private String fallback() throws InterruptedException {
        return "Static Response from fallback";
    }

    @Bulkhead(value = 3)
    @Fallback(fallbackMethod = "fallback")
    public String callBilkHead() throws InterruptedException {
        return state.call();
    }

    @Asynchronous()
    @Bulkhead(value = 5, waitingTaskQueue = 5)
    @Fallback(fallbackMethod = "fallbackAsync")
    public Future<String> serviceA() throws InterruptedException {      
        return CompletableFuture.completedFuture(state.call());
    }

    private Future<String> fallbackAsync() throws InterruptedException {
        return CompletableFuture.completedFuture("Static Response from fallbackAsync");
    }


    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 20000)
    @Fallback(fallbackMethod = "fallbackCirtuitOpen",applyOn = CircuitBreakerOpenException.class)
    public String callCircuit() throws InterruptedException {
        return state.call();
    }
    private String fallbackCirtuitOpen() throws InterruptedException {
        return "The service is unavailable please try again later";
    }

}
