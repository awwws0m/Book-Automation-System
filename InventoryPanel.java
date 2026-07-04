import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends JPanel {

    private JTextField isbnField;
    private JTextField copiesField;
    private JButton updateBtn;
    private JLabel statusLabel;

    public InventoryPanel() {

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));

        isbnField   = new JTextField(20);
        copiesField = new JTextField(10);
        updateBtn   = new JButton("Update Stock");
        statusLabel = new JLabel(" ");

        add(new JLabel("ISBN:"));
        add(isbnField);
        add(new JLabel("Copies to be added:"));
        add(copiesField);
        add(updateBtn);
        add(statusLabel);

        updateBtn.addActionListener(e -> handleUpdate());
    }


    private void handleUpdate() {

        String isbn   = isbnField.getText().trim();
        String copies = copiesField.getText().trim();

        if (isbn.isEmpty() || copies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        int qty = Integer.parseInt(copies);
        boolean success = Database.updateStock(isbn, qty);

        if (success) {
            statusLabel.setText("Stock updated successfully!");
        } else {
            statusLabel.setText("Book not found!");
        }

        isbnField.setText("");
        copiesField.setText("");
    }
}
