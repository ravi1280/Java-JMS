package lk.jiat.ee.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueSender {
    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory)
                    context.lookup("jms/MyQueueConnectionFactory");

            QueueConnection connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) context.lookup("jms/MyQueue");

            jakarta.jms.QueueSender sender = session.createSender(queue);

            for (int i = 0; i < 50; i++) {
                TextMessage message = session.createTextMessage();
                message.setText("Hello World, "+i);
                sender.send(message);
            }


            sender.close();
            session.close();
            connection.close();

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
