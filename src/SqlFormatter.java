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
    JPanel textPanel1 = new JPanel(new FlowLayout());
    JPanel subPanel1 = new JPanel(new FlowLayout());
    JPanel subPanel2 = new JPanel(new FlowLayout());
    JPanel subPanel3 = new JPanel(new FlowLayout());
    JPanel subPanel4 = new JPanel(new FlowLayout());
    JLabel label1 = new JLabel("Original SQL:");
    JLabel label2 = new JLabel("Formatted SQL:");

    JTextArea textArea1 = new JTextArea();
    JScrollPane scrollPane1 = new JScrollPane(subPanel1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JTextArea textArea2 = new JTextArea();
    JScrollPane scrollPane2 = new JScrollPane(textArea2);
    JButton formatButton = new JButton("Format");
    JButton clearButton = new JButton("Clear");
    JButton downloadButton = new JButton("Download");
    JButton copyButton = new JButton("Copy to Clipboard");
    JFileChooser fc = new JFileChooser();
    //JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 100, 100, 100, 500);

    public static void main(String[] args) {
        SqlFormatter gui = new SqlFormatter();
    }

    public SqlFormatter() {
        // Defines title, size, default operation, and layout of window
        super("SQL Formatter");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        GridBagConstraints c = new GridBagConstraints();

        textArea1.setPreferredSize(new Dimension(1250, 350));
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        textArea2.setPreferredSize(new Dimension(1250, 350));
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);

        scrollPane1.setSize(100, 500);
//        scrollPane2.setSize(100, 100);

        subPanel1.add(label1);
        subPanel1.add(textArea1);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 1;
//        panel.add(subPanel1, c);

        formatButton.addActionListener(SqlFormatter.this);
        subPanel2.add(formatButton);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 2;

        clearButton.addActionListener(SqlFormatter.this);
        subPanel2.add(clearButton);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 2;
//        panel.add(subPanel2, c);

        subPanel3.add(label2);
        subPanel3.add(textArea2);
//        //subPanel3.add(scrollPane2);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 3;
//        panel.add(subPanel3, c);

        downloadButton.addActionListener(SqlFormatter.this);
        subPanel4.add(downloadButton);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 4;

        copyButton.addActionListener(SqlFormatter.this);
        subPanel4.add(copyButton);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 4;
//        panel.add(subPanel4, c);

        panel.add(subPanel1);
        panel.add(subPanel2);
        panel.add(subPanel3);
        panel.add(subPanel4);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if ( e.getSource() == formatButton ) {
            textArea2.append("");
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
        String lastToken;
        String output;
        int asThere = 0;
        int isLeftThere = 0;

        String[] KEYWORDS = {"SELECT", "FROM", "WHERE", "AND", "WITH",
        "CASE", "COUNT(", "SUM("};

        String[] FUNCTIONS = {"DATE_FORMAT", "SUM", "CHAR_LENGTH", "AVG",
        "COUNT", "FIRST", "LAST", "MAX", "MIN", "UCASE", "LCASE", "MID",
        "LEN", "ROUND", "NOW", "FORMAT"};

        while ( st.hasMoreTokens() ) {
            token = st.nextToken();
            System.out.println(token);
            if ( token.equalsIgnoreCase("SELECT") ) {

//                if ( asThere == 2 ) {
//                    textArea2.append("\n            " + token.toUpperCase() + " ");
//                }
//                else {
//                    textArea2.append("\n" + token.toUpperCase() + " ");
//                }

                textArea2.append("\n" + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("FROM") ) {
//                System.out.println("\nFROM() found!\n");
//                if ( asThere == 2) {
//                    textArea2.append("\n" +
//                            "              " + token.toUpperCase() + " ");
//                }
//                else {
//
//                    textArea2.append("\n  " + token.toUpperCase() + " ");
//                }

                textArea2.append("\n  " + token.toUpperCase() + " ");

            }
            else if ( token.contains("FROM(") ) {
                textArea2.append("\n               " + token.toUpperCase() );
            }
            else if ( token.equalsIgnoreCase("WHERE") ) {

//                if ( asThere == 2) {
//                    textArea2.append("\n" +
//                            "            " + token.toUpperCase() + " ");
//                }
//                else
//                    textArea2.append("\n " + token.toUpperCase() + " ");

                textArea2.append("\n " + token.toUpperCase() + " ");

            }
            else if ( token.equalsIgnoreCase("AND") ) {
                //textArea2.append("\n       " + token.toUpperCase() + " ");
                textArea2.append("\n     " + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("WITH") ) {
                textArea2.append("\n   " + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("CASE") ) {
                textArea2.append("\n       " + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("COUNT") || token.toUpperCase().startsWith("COUNT(") ) {
//                textArea2.append("            " + token.toUpperCase());
                textArea2.append(token.toUpperCase());
            }
            else if ( token.equalsIgnoreCase("LEFT") ) {
                isLeftThere += 1;
                textArea2.append("\n   " + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("GROUP") ) {
//                if ( asThere == 2 ) {
//                    textArea2.append("\n" +
//                            "            " + token.toUpperCase() + " ");
//                }
//                else
//                    textArea2.append("\n " + token.toUpperCase() + " ");

                textArea2.append("\n " + token.toUpperCase() + " ");
            }
            else if ( token.equals("AND") ) {
                textArea2.append("\n    " + token + " ");
            }
            else if ( token.equalsIgnoreCase("AS") ) {
                asThere += 1;
                textArea2.append(" " + token.toUpperCase() + " ");
            }
            else if ( token.endsWith(",") ) {

                for (String function:
                        FUNCTIONS) {
                    if ( token.contains(function) ) {
                        System.out.println("FUNCTION FOUND!");
                        textArea2.append(token + " ");
                        break;
                    }
                }

                textArea2.append(token + "\n            ");
            }
            else if ( token.equalsIgnoreCase("JOIN") ) {
                if ( isLeftThere == 0 ) {
                    textArea2.append("\n    " + token.toUpperCase() + " ");
                }
                else {
                    textArea2.append(" " + token.toUpperCase() + " ");
                    isLeftThere = 0;
                }

            }

            else if ( token.equals("(") || token.contains("(") ) {
                asThere += 1;
//                if ( asThere == 2) {
//                    textArea2.append("\n" + token + " ");
//                }
//                else {
//                    textArea2.append(token);
//                }

                textArea2.append(token);
            }

            else if ( token.contains(",") ) {

                if ( token.startsWith("(") && token.endsWith(")") ) {
                    textArea2.append(token + " ");
                }
                else if ( token.endsWith(")") ) {
                    textArea2.append(token + " ");
                }
                else {
                    String replcStr = token.replace(",", ",\n            ");
                    System.out.println("String: {" + replcStr + "}");
                    textArea2.append(replcStr);
                }
            }
            else if ( token.startsWith(",") ) {
                String replcStr = token.replace(",", ",\n           ");
                System.out.println("String: " + replcStr);
                textArea2.append(replcStr);
            }
            else if ( token.equals("*/") ) {
                textArea2.append(" " + token + "\n            ");
            }
            else if ( token.contains("*/") ) {
                 String replcStr = token.replace("*/", " */\n            ");
                 textArea2.append(replcStr);
            }
            else if ( token.contains(")") ) {
                if ( asThere == 2) {
                    asThere = 0;
                    textArea2.append(token);
                }
                else {
                    textArea2.append(token + " ");
                }
            }
            else if ( token.equalsIgnoreCase("WHEN") ) {
                textArea2.append("\n" +
                        "        " + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("THEN") ) {
                textArea2.append("\n        " + token.toUpperCase() + " ");
            }
            else if ( token.equalsIgnoreCase("ELSE") ) {
                textArea2.append("\n        " + token.toUpperCase() + " ");
            }
            else {
                textArea2.append(token + " ");
            }
        }
    }

}