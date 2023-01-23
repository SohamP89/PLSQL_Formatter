/*
Purpose: Organize PL/SQL statements into a specific format to improve readability and organization. This makes it
easier for both developers and support engineers to manage databases and fix any bugs/issues.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Formatter extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JTextField jTextField1 = new JTextField();
    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//
//        } catch (UnsupportedLookAndFeelException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }

        Formatter gui = new Formatter();
    }

    public Formatter() {
        super("PL/SQL Formatter");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(new BorderLayout());

        jTextField1.setSize(500, 500);

        panel.add(jTextField1);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

    }

}
