package lk.jiat.ee.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TopicSender {
    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();
            TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("jms/MyConnectionFactory");

            // Lookup topic
            TopicConnection connection = factory.createTopicConnection(); // Corrected line
            connection.start();

            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            System.out.println("Session:-  " + session);

            Topic topic = (Topic) context.lookup("jms/MyTopic");
            TopicPublisher publisher = session.createPublisher(topic);
            System.out.println("Topic:-  " + topic);

            //TextMessage message = session.createTextMessage();
            //message.setText("Hello World!");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your Message or type 'exit' to exit");

            while (true) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                TextMessage message = session.createTextMessage();
                message.setText(line);
                publisher.publish(message);
            }




            // Clean up resources
            //session.close();
            //connection.close();
            //context.close();

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
