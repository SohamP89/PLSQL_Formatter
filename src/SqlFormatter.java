/*
Purpose: Organize PL/SQL statements into a specific format to improve readability and organization. This makes it
easier for both developers and support engineers to manage databases and fix any bugs/issues.

From now on, for any GUI applications in Java, use JGoodies instead of default Swing
because it provides modern, clean, and easy-to-use layouts. Alternatively, you could just use GridBagLayout.
 */

/*
Source for code that copies text to clipboard: https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

public class SqlFormatter extends JFrame implements ActionListener {

    /* Definition and Declaration of Java Swing components */
    JPanel subPanel1 = new JPanel(new FlowLayout());
    JPanel subPanel2 = new JPanel(new FlowLayout());
    JPanel subPanel3 = new JPanel(new FlowLayout());
    JPanel subPanel4 = new JPanel(new FlowLayout());
    JLabel label1 = new JLabel("Original SQL:");
    JLabel label2 = new JLabel("Formatted SQL:");

    JTextArea textArea1 = new JTextArea();
    JTextArea textArea2 = new JTextArea();
    JButton formatButton = new JButton("Format");
    JButton clearButton = new JButton("Clear");
    JButton downloadButton = new JButton("Download");
    JButton copyButton = new JButton("Copy to Clipboard");
    JFileChooser fc = new JFileChooser();

    /* ---- Main Method ----
     * Creates new instance of constructor in order to run the main program */
    public static void main(String[] args) {
        SqlFormatter gui = new SqlFormatter();
    }

    /* ---- Constructor ----
     * Sets the title, size, layout, and close operation of the program.
     * Adds the Swing components to the plane */
    public SqlFormatter() {
        /* Defines title, size, default operation, and layout of window */
        super("SQL Formatter");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* Initialization of key Swing components here */
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Sets the size and wrap styles of the Textareas */
        textArea1.setPreferredSize(new Dimension(1250, 350));
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        textArea2.setPreferredSize(new Dimension(1250, 350));
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);

        /* Divides the Swing components into sub-panels */
        subPanel1.add(label1);
        subPanel1.add(textArea1);

        formatButton.addActionListener(SqlFormatter.this);
        subPanel2.add(formatButton);

        clearButton.addActionListener(SqlFormatter.this);
        subPanel2.add(clearButton);

        subPanel3.add(label2);
        subPanel3.add(textArea2);

        downloadButton.addActionListener(SqlFormatter.this);
        subPanel4.add(downloadButton);

        copyButton.addActionListener(SqlFormatter.this);
        subPanel4.add(copyButton);


        /*  Adds sub-panels to main panel  */
        panel.add(subPanel1);
        panel.add(subPanel2);
        panel.add(subPanel3);
        panel.add(subPanel4);

        /*  Adds panel to main frame  */
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if ( e.getSource() == formatButton ) {
            textArea2.setText("");
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

            FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL File (.sql)", "sql");
            fc.setFileFilter(filter);

            int returnVal = fc.showSaveDialog(SqlFormatter.this);

            if ( returnVal == JFileChooser.APPROVE_OPTION ) {

                String filename;

                if ( !fc.getSelectedFile().getName().contains(".") ) {
                    filename = fc.getSelectedFile() + ".sql";
                    fc.setSelectedFile(new File(filename));
                }

                FileSaveAction();
            }
        }
    }

    /* This method saves the "Formatted Output" text to a file with the
     *  specified name and the selected directory */
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

    /* This method formats the original SQL statement inputted by the user */
    private void formatSql() {

        /* Tokenizer splits each keyword, function, and special character of the original SQL
        * into substrings */
        StringTokenizer st = new StringTokenizer(textArea1.getText());
        String token;

        /* The int variable asThere checks how many times the user has used the AS keyword
         * If the user has called the AS keyword followed by an open parentheses, then the
         * program uses the variable to adjust the formatting. */
        int asThere = 0;

        /* The int variable isLeftThere checks how many times the user has called the LEFT keyword
         * By checking how often LEFT has been called, the program adjusts the formatting, so that LEFT
         * is properly aligned. */
        int isLeftThere = 0;

        String[] FUNCTIONS = {"DATE_FORMAT", "SUM", "CHAR_LENGTH", "AVG",
        "COUNT", "FIRST", "LAST", "MAX", "MIN", "UCASE", "LCASE", "MID",
        "LEN", "ROUND", "NOW", "FORMAT"};

        while ( st.hasMoreTokens() ) {
            token = st.nextToken();

            if ( token.equalsIgnoreCase("SELECT") ) {
                textArea2.append("\n" + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("FROM") ) {
                textArea2.append("\n  " + token.toUpperCase() + " ");
            }

            else if ( token.contains("FROM(") ) {
                textArea2.append("\n               " + token.toUpperCase() );
            }

            else if ( token.equalsIgnoreCase("WHERE") ) {
                textArea2.append("\n " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("AND") ) {
                textArea2.append("\n     " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("WITH") ) {
                textArea2.append("\n   " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("CASE") ) {
                textArea2.append("\n    " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("COUNT(") || token.toUpperCase().startsWith("COUNT(") ) {
                textArea2.append(token.toUpperCase());
            }

            else if ( token.equalsIgnoreCase("LEFT") ) {
                isLeftThere += 1;
                textArea2.append("\n    " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("ORDER") ) {
                textArea2.append("\n " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("BY") ) {
                textArea2.append(token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("NOT") ) {
                textArea2.append(token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("IN") ) {
                textArea2.append(token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("NULL") ) {
                textArea2.append(token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("GROUP") ) {
                textArea2.append("\n " + token.toUpperCase() + " ");
            }

            else if ( token.equals("AND") ) {
                textArea2.append("\n    " + token + " ");
            }

            else if ( token.equalsIgnoreCase("AS") ) {
                asThere += 1;
                textArea2.append(" " + token.toUpperCase() + " ");
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

            else if ( token.equalsIgnoreCase("UNION") ) {
                textArea2.append("\n" + token.toUpperCase() + " ");
            }

            else if ( token.contains("(") ) {
                asThere += 1;

                textArea2.append(token);
            }

            else if ( token.contains(",") ) {

                if ( token.startsWith("(") && token.endsWith(")") ) {
                    textArea2.append(token + " ");
                }
                else if ( token.endsWith(")") ) {
                    textArea2.append(token + " ");

                    boolean functionFound = false;

                    for (String func:
                         FUNCTIONS) {
                        if ( token.contains(func) ) {
                            functionFound = true;
                            break;
                        }
                    }

                    if ( functionFound ) {
                        textArea2.append(" ");
                        functionFound = false;
                    }

                }
                else {
                    String replcStr = token.replace(",", ",\n            ");
                    textArea2.append(replcStr);
                }
            }

            else if ( token.startsWith(",") ) {
                String replcStr = token.replace(",", ",\n           ");
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
                        "  " + token.toUpperCase() + " ");
            }

            else if ( token.equalsIgnoreCase("THEN") ) {
                textArea2.append("\n   " + token.toUpperCase() + " ");
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