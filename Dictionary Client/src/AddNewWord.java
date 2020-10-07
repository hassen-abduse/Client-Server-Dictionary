import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.*;

public class AddNewWord{
    private JTextField wordStringField;
    private JTextArea definition1Area;
    private JTextArea definition2Area;
    private JComboBox wordClass;
    private JButton addWordButton;
    private JButton cancelButton;
    private  JPanel panel1;

    public AddNewWord() {

        addWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean validword = true;
                String addRequest = "";
                String wordString = wordStringField.getText();
                if(wordString.equals("")) {JOptionPane.showMessageDialog(null, "Please Enter a valid word String"); validword = false;}

                String choosenWordClass = wordClass.getSelectedItem().toString();
                String definition1 = definition1Area.getText();
                if(validword) {

                    if (definition1.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please Enter a valid definition for the word");
                        validword = false;
                    }
                }
                String definition2 = definition2Area.getText();
                if(validword) {
                    Word word = new Word(wordString, choosenWordClass, definition1, definition2);
                    JSONObject wordObject = new JSONObject(word);
                    Shared.requestString = "ADD*" + wordObject.toString();
                }


            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // close the add new word window


            }
        });
    }

    public  JPanel getPanel1() {
        return panel1;
    }

}
