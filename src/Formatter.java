/*
Purpose: Organize PL/SQL statements into a specific format to improve readability and organization. This makes it
easier for both developers and support engineers to manage databases and fix any bugs/issues.
 */

/*
From now on, for any GUI applications in Java, use JGoodies instead of default Swing
because it provides modern, clean, and easy-to-use layouts.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Formatter extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JPanel subPanel1 = new JPanel();
    JTextArea textField1 = new JTextArea();
    JTextArea textField2 = new JTextArea();
    JButton button1 = new JButton("Format");
    public static void main(String[] args) {
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
        subPanel1.add(button1);
        subPanel1.add(textField2);

        panel.add(subPanel1);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

}
