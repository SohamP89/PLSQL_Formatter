/*
Purpose: Organize PL/SQL statements into a specific format to improve readability and organization. This makes it
easier for both developers and support engineers to manage databases and fix any bugs/issues.

From now on, for any GUI applications in Java, use JGoodies instead of default Swing
because it provides modern, clean, and easy-to-use layouts. Alternatively, you could just use GridBagLayout.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/*
Used this website as a reference: https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
 */

public class SqlFormatter extends JFrame implements ActionListener {
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel subPanel1 = new JPanel(new FlowLayout());
    JPanel subPanel2 = new JPanel(new FlowLayout());
    JPanel subPanel3 = new JPanel(new FlowLayout());
    JPanel subPanel4 = new JPanel(new FlowLayout());
    JLabel label1 = new JLabel("Original Query:");
    JLabel label2 = new JLabel("Formatted Query:");

    JTextArea textArea1 = new JTextArea();
    JScrollPane scrollPane1 = new JScrollPane(textArea1);
    JTextArea textArea2 = new JTextArea();
    JScrollPane scrollPane2 = new JScrollPane(textArea2);
    JButton formatButton = new JButton("Format");
    JButton clearButton = new JButton("Clear");
    JButton downloadButton = new JButton("Download");
    JButton copyButton = new JButton("Copy to Clipboard");
    JFileChooser fc = new JFileChooser();

    public static void main(String[] args) {
        SqlFormatter gui = new SqlFormatter();
    }

    public SqlFormatter() {
        // Defines title, size, default operation, and layout of window
        super("SQL Formatter");
        setSize(700, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridBagConstraints c = new GridBagConstraints();

        textArea1.setPreferredSize(new Dimension(525, 250));
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        textArea2.setPreferredSize(new Dimension(525, 250));
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);

        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane1.setSize(100, 100);
        scrollPane2.setSize(100, 100);

        subPanel1.add(label1);
        subPanel1.add(scrollPane1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(subPanel1, c);

        formatButton.addActionListener(SqlFormatter.this);
        subPanel2.add(formatButton);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;

        clearButton.addActionListener(SqlFormatter.this);
        subPanel2.add(clearButton);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(subPanel2, c);

        downloadButton.addActionListener(SqlFormatter.this);
        subPanel4.add(downloadButton);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;

        copyButton.addActionListener(SqlFormatter.this);
        subPanel4.add(copyButton);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        panel.add(subPanel4, c);

        subPanel3.add(label2);
        subPanel3.add(scrollPane2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(subPanel3, c);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if ( e.getSource() == formatButton ) {
            formatSql();
        }

        if ( e.getSource() == clearButton ) {
            textArea1.setText("");
            textArea2.setText("");
        }

        if ( e.getSource() == copyButton ) {
            StringSelection stringSelection = new StringSelection(textArea2.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }

        if ( e.getSource() == downloadButton ) {
            int returnVal = fc.showSaveDialog(SqlFormatter.this);
            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
                FileSaveAction();
            }
        }
    }

    private void FileSaveAction() {
        try {
            File file = fc.getSelectedFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textArea2.getText());
            bw.close();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void formatSql() {
        // Put next token into a specific variable instead of just using nextToken()
        // Because nextToken() skips and prints every other token, which messes up your output

        StringTokenizer st = new StringTokenizer(textArea1.getText());
        String token;

        while ( st.hasMoreTokens() ) {
            token = st.nextToken();
            System.out.println(token);
            if ( token.equals("SELECT") ) {
                textArea2.append(token + " ");
            }
            else if ( token.equals("FROM") ) {
                textArea2.append("\n  " + token + " ");
            }
            else if ( token.equals("WHERE") ) {
                textArea2.append("\n " + token + " ");
            }
            else if ( token.equals("AND") ) {
                textArea2.append("\n    " + token + " ");
            }
            else if ( token.endsWith(",") ) {
                textArea2.append(token + "\n");
            } else
                textArea2.append(token + " ");
        }
    }

}