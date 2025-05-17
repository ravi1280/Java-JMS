package lk.jiat.ee.jms;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        try {
            InitialContext ic = new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
