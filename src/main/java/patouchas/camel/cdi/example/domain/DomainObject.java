package patouchas.camel.cdi.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * A simple domain object to use to our example 
 * 
 * @author patouchas
 *
 */
@Entity
public class DomainObject {

    @Id
    @SequenceGenerator(name = "dom", sequenceName = "sqName", allocationSize = 1)
    @GeneratedValue(generator = "dom", strategy = GenerationType.SEQUENCE)
    private Long databaseId;

    private String uniqueId;

    @Column(length = 10485760)
    private String messageBody;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public DomainObject(String uniqueId, String messageBody) {
        super();
        this.uniqueId = uniqueId;
        this.messageBody = messageBody;
    }

    protected DomainObject() {

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((databaseId == null) ? 0 : databaseId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DomainObject other = (DomainObject) obj;
        if (databaseId == null) {
            if (other.databaseId != null)
                return false;
        } else if (!databaseId.equals(other.databaseId))
            return false;
        return true;
    }

}
