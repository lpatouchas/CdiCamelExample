package patouchas.camel.cdi.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import patouchas.camel.cdi.example.domain.DomainObject;

@ApplicationScoped
public class MyProcessor implements Processor {

    private EntityManager entityManger;

    @Inject
    public MyProcessor(EntityManager entityManager) {
        this.entityManger = entityManager;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        String msgId = exchange.getIn().getHeader("UNIQUE_ID").toString();
        String msgBody = exchange.getIn().getBody().toString();
        DomainObject domainObject = new DomainObject(msgId, msgBody);
        entityManger.persist(domainObject);

    }

    protected MyProcessor() {

    }

}
