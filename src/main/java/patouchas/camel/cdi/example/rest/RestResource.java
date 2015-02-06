package patouchas.camel.cdi.example.rest;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/msg/")
@ApplicationScoped
public class RestResource {

    @Inject
    private JMSContext context;

    @Resource(lookup = "SG")
    private Queue queue;

    @Consumes(MediaType.APPLICATION_XML)
    @PUT
    public Response sendMsg(@HeaderParam("UNIQUE_ID") String uniqueId, String body) {

        try {
            Message m = context.createObjectMessage(body);
            m.setStringProperty("UNIQUE_ID", uniqueId);
            context.createProducer().send(queue, m);
        } catch (JMSException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        return Response.status(Status.ACCEPTED).build();
    }

}
