import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;

public class ClientHome {
    private JPanel panel;
    private JButton addButton;
    private JButton removeButton;
    private JButton searchButton;
    private JTextField wordStringField;
    private JTextArea wordDetailArea;
    private JPanel panel1;
    public static String wordDetails;

    public ClientHome() {

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                wordDetailArea.setText("");
                boolean isValidWord = true;
                String wordString = wordStringField.getText();
                if (wordString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter a valid word string");
                    isValidWord = false;
                }
                if (isValidWord) {
                    Shared.requestString = "SEARCH*" + wordString;
                    // System.out.println(Shared.requestString);
                    try {
                        Thread.sleep(200);
                        wordDetailArea.setFont(Font.getFont(String.valueOf(Font.BOLD)));
                        wordDetailArea.setText(wordDetails);
                        wordDetails = "";

                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                wordDetailArea.setText("");
                JFrame addNewFrame = new JFrame("Add New Word");
                addNewFrame.setContentPane(new AddNewWord().getPanel1());
                addNewFrame.setResizable(false);
                addNewFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                addNewFrame.pack();
                addNewFrame.setVisible(true);

            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                wordDetailArea.setText("");
                boolean isValidWord = true;

                String wordString = wordStringField.getText();

                if (wordString.equals("") || wordString == null) {
                    JOptionPane.showMessageDialog(null, "Please Enter a valid word string");
                    isValidWord = false;
                }
                System.out.println(isValidWord);
                if (isValidWord) {
                    Shared.requestString = "REMOVE*" + wordString;

                }
            }
        });
    }

    public JPanel getPanel() {
        return panel1;
    }


}
