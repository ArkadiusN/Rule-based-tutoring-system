package main;

import javax.swing.*;
/**
 * Implementation of Rule-based tutoring system.
 * @author Arkadiusz Nowacki (anowa002)
 * @version openjdk-17 java version "17.0.1", maven compiler source 14
 */
public class Main{
    public static void main(String[] args) {
        /* New Runnable for CLIMenu on other thread (method reference) */
//        SwingUtilities.invokeLater((Runnable) clientMenu);

        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                ClientMenu clientMenu = new ClientMenu();
            }
        });

    }
}
