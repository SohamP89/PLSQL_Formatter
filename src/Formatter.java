/*
Purpose: Organize PL/SQL statements into a specific format to improve readability and organization. This makes it
easier for both developers and support engineers to manage databases and fix any bugs/issues.

From now on, for any GUI applications in Java, use JGoodies instead of default Swing
because it provides modern, clean, and easy-to-use layouts. Alternatively, you could just use GridBagLayout.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Use Box.createRigidArea to make sure that the textboxes aren't oversized
// when you use them
public class Formatter extends JFrame implements ActionListener {
    JPanel panel = new JPanel();

    JPanel subPanel1 = new JPanel(new FlowLayout());
    JPanel subPanel2 = new JPanel(new FlowLayout());
    JPanel subPanel3 = new JPanel(new FlowLayout());
    JLabel label1 = new JLabel("Original:");
    JLabel label2 = new JLabel("Formatted:");

    JTextArea textArea1 = new JTextArea();
    JTextArea textArea2 = new JTextArea();
    JScrollPane scrollPane1 = new JScrollPane(textArea1);
    JScrollPane scrollPane2 = new JScrollPane(textArea2);
    JButton button1 = new JButton("Format");
    JMenuBar menuBar1 = new JMenuBar();
    JMenu file;
    JMenuItem newOption;
    JMenuItem quitOption;
    public static void main(String[] args) {
        Formatter gui = new Formatter();
    }

    public Formatter() {
        // Defines title, size, default operation, and layout of window
        super("PL/SQL Formatter");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        textArea1.setPreferredSize(new Dimension(525, 100));
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        textArea2.setPreferredSize(new Dimension(525, 100));
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);

        subPanel1.add(label1);
        subPanel1.add(textArea1);
        subPanel1.setAlignmentY(Component.TOP_ALIGNMENT);

        subPanel2.add(button1);
        subPanel2.setAlignmentY(Component.CENTER_ALIGNMENT);

        subPanel3.add(label2);
        subPanel3.add(textArea2);
        subPanel3.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        panel.add(subPanel1);
        panel.add(subPanel2);
        panel.add(subPanel3);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

}