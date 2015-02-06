package patouchas.camel.cdi.example.domain;

/**
 * A simple repository interface
 * 
 * @author patouchas
 *
 */
public interface DomainRepository {

    void persist(DomainObject domainObject);
}
