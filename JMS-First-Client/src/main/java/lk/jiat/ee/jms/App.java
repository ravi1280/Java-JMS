package lk.jiat.ee.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        try {
            InitialContext ic = new InitialContext();
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ic.lookup("jms/MyConnectionFactory");
            System.out.println(connectionFactory);

            TopicConnection connection = connectionFactory.createTopicConnection();
            connection.start();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = (Topic) ic.lookup("jms/MyTopic");
            TopicPublisher publisher = session.createPublisher(topic);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your message or type exit: ");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(line);
                publisher.publish(textMessage);

            }

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
