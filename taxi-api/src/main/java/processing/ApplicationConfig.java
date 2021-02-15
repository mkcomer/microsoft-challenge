package processing;


import org.glassfish.jersey.server.ResourceConfig;
import resource.HealthCheck;
import resource.TaxiResource;


import javax.ws.rs.ApplicationPath;


@ApplicationPath("")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig(){
        // register application resources
        register(HealthCheck.class);
        register(TaxiResource.class);
        System.out.println("Resources configured");
    }
}
