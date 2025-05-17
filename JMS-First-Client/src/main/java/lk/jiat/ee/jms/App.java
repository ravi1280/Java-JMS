package lk.jiat.ee.jms;

import jakarta.jms.TopicConnectionFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        try {
            InitialContext ic = new InitialContext();
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ic.lookup("jms/MyConnectionFactory");
            System.out.println(connectionFactory);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
