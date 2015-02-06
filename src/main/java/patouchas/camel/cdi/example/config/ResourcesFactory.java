package patouchas.camel.cdi.example.config;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@ApplicationScoped
public class ResourcesFactory {

    @Resource(lookup = "java:/TransactionManager")
    private TransactionManager transactionManager;

    @Resource
    private ConnectionFactory connectionFactory;

    private IdempotentRepository<String> repo = new MemoryIdempotentRepository();

    @PersistenceContext(unitName = "cdi-camel-pu")
    private EntityManager em;

    @Produces
    @Named("transactionManager")
    @ApplicationScoped
    public PlatformTransactionManager getTransactionManager() {
        return new JtaTransactionManager(transactionManager);
    }

    @Produces
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Produces
    public IdempotentRepository<String> create() {
        return repo;
    }

    @Produces
    public EntityManager actsEntityManager() {
        return em;
    }

    @Produces
    @Named("PROPAGATION_REQUIRED")
    @ApplicationScoped
    public SpringTransactionPolicy getPropagationRequired(
            @Named("transactionManager") PlatformTransactionManager manager) {
        SpringTransactionPolicy propagationRequired = new SpringTransactionPolicy();
        propagationRequired.setTransactionManager(manager);
        propagationRequired.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        return propagationRequired;
    }
}
