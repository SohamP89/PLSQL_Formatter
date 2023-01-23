/*
Purpose: Organize PL/SQL statements into a specific format to improve readability and organization. This makes it
easier for both developers and support engineers to manage databases and fix any bugs/issues.
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
public class Formatter extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JPanel subPanel1 = new JPanel();
    JPanel subPanel2 = new JPanel();
    JPanel subPanel3 = new JPanel();
    JTextField textField1 = new JTextField();
    JTextField textField2 = new JTextField();
    JButton button1 = new JButton("Format");
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
        // Defines title, size, default operation, and layout of window
        super("PL/SQL Formatter");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout());

        textField1.setPreferredSize(new Dimension(500, 200));
        button1.setPreferredSize(new Dimension(100, 100));
        textField2.setPreferredSize(new Dimension(500, 200));

        subPanel1.add(textField1);
        subPanel2.add(button1);
        subPanel3.add(textField2);

        panel.add(subPanel1, BorderLayout.NORTH);
        panel.add(subPanel2, BorderLayout.CENTER);
        panel.add(subPanel3, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

}
