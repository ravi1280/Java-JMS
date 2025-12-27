package lk.jiat.ee.client;

import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class App {
    public static void main(String[] args) {


        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.impl.SerialInitContextFactory");
            props.setProperty(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
            props.setProperty(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

// Required: Point to the running Payara server (embedded or remote)
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); // Payara's default

            InitialContext context = new InitialContext(props);
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("jms/MyConnectionFactory");

            TopicConnection connection = connectionFactory.createTopicConnection();
            connection.start();

            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = (Topic) context.lookup("jms/MyTopic");

            TopicSubscriber subscriber = session.createSubscriber(topic);

            Message message = subscriber.receive();
            System.out.println(message.getBody(String.class));

            subscriber.setMessageListener(new MessageListener() {

                @Override
                public void onMessage(Message message) {
                    try {
                        String msg =message.getBody(String.class);
                        System.out.println(msg);
//                        message.acknowledge();
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            while (true) {}

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
