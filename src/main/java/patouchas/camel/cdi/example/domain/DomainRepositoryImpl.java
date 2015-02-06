package patouchas.camel.cdi.example.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class DomainRepositoryImpl implements DomainRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public void persist(DomainObject domainObject) {
        entityManager.persist(domainObject);
    }

}
