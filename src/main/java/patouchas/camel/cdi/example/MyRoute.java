package patouchas.camel.cdi.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.IdempotentRepository;

/**
 * TestRoute consumes the myQueue. It is transacted and will check for
 * duplicates by using a memory idempotent. If the received message is not a
 * duplicate it will persist it.
 * 
 * @author patouchas
 *
 */
@ApplicationScoped
public class MyRoute extends RouteBuilder {

    private IdempotentRepository<String> repo;

    private MyProcessor myProcessor;

    @Inject
    public MyRoute(IdempotentRepository<String> repo, MyProcessor myProcessor) {
        this.repo = repo;
        this.myProcessor = myProcessor;
    }

    protected MyRoute() {

    }

    @Override
    public void configure() throws Exception {

        from("jms:myQueue").transacted("PROPAGATION_REQUIRED")
        		.idempotentConsumer(header("UNIQUE_ID"))
                .messageIdRepository(repo).skipDuplicate(false)
                .choice()
                	.when(property(Exchange.DUPLICATE_MESSAGE).isEqualTo(true)).log(LoggingLevel.INFO, "Message Duplicate.")
                .otherwise()
                	.process(myProcessor).log(LoggingLevel.INFO, "Message Saved.");
    }
}
