package patouchas.camel.cdi.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.IdempotentRepository;

@ApplicationScoped
public class TestRoute extends RouteBuilder {

    private IdempotentRepository<String> repo;

    private MyProcessor myProcessor;

    @Inject
    public TestRoute(IdempotentRepository<String> repo, MyProcessor myProcessor) {
        this.repo = repo;
        this.myProcessor = myProcessor;
    }

    protected TestRoute() {

    }

    @Override
    public void configure() throws Exception {

        from("jms:SG").transacted("PROPAGATION_REQUIRED")
        		.idempotentConsumer(header("UNIQUE_ID"))
                .messageIdRepository(repo).skipDuplicate(false)
                .choice()
                	.when(property(Exchange.DUPLICATE_MESSAGE).isEqualTo(true)).log(LoggingLevel.INFO, "Message Duplicate.")
                .otherwise()
                	.process(myProcessor).log(LoggingLevel.INFO, "Message Saved.");
    }
}
