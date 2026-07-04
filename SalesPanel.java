import javax.swing.*;
import java.awt.*;

public class SalesPanel extends JPanel {

    private JTextField isbnField;
    private JButton saleBtn;
    private JTextArea receiptArea;

    public SalesPanel(){

        setLayout(new BorderLayout(10, 10));

        //ISBN input + Button
        JPanel topPanel = new JPanel(new FlowLayout());

        isbnField = new JTextField(20);
        saleBtn   = new JButton("Process Sale");

        topPanel.add(new JLabel("Enter ISBN:"));
        topPanel.add(isbnField);
        topPanel.add(saleBtn);

        //Receipt display
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(topPanel,BorderLayout.NORTH);
        add(new JScrollPane(receiptArea),BorderLayout.CENTER);

        saleBtn.addActionListener(e -> handleSale());
    }


    private void handleSale(){

        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ISBN.");
            return;
        }
        String result = Database.processSale(isbn);


        if (result == null){
            JOptionPane.showMessageDialog(this, "Book not found!");

        } 
        else if (result.equals("OUT")) {
            JOptionPane.showMessageDialog(this, "Book is out of stock!");

        } 
        else {
            receiptArea.setText(result);
        }
        isbnField.setText("");
    }
}
