package patouchas.camel.cdi.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * Here is where we instantiate our Camel Context. We add the camel routes we
 * want and we also configure the jms component that we will use by setting the
 * connecton factory and the transaction manager
 * 
 * 
 * @author patouchas
 *
 */
@Startup
@Singleton
public class CamelBootstrap {

    @Inject
    private CdiCamelContext ctx;

    @Inject
    private ConnectionFactory cf;

    @Inject
    private PlatformTransactionManager ptm;

    @Inject
    private MyRoute testRoute;

    @PostConstruct
    public void init() {

        ctx.setTypeConverterStatisticsEnabled(true);
        ctx.setName("camelTest");
        ctx.addComponent("jms", JmsComponent.jmsComponentTransacted(cf, ptm));
        addRoute(testRoute);
        ctx.start();
    }

    @PreDestroy
    public void shutdown() {
        ctx.stop();
    }

    private void addRoute(RouteBuilder rb) {
        try {
            ctx.addRoutes(rb);
        } catch (Exception e) {
        }
    }

}
