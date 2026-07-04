import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SearchPanel extends JPanel {

    private JRadioButton byTitle, byAuthor;
    private JTextField searchField;
    private JButton searchBtn;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SearchPanel() {

        setLayout(new BorderLayout(10, 10));

        //Radio Buttons + Search field
        JPanel topPanel = new JPanel(new FlowLayout());

        byTitle  = new JRadioButton("By Title", true);
        byAuthor = new JRadioButton("By Author");

        ButtonGroup group = new ButtonGroup();
        group.add(byTitle);
        group.add(byAuthor);

        searchField = new JTextField(20);
        searchBtn   = new JButton("Search");

        topPanel.add(byTitle);
        topPanel.add(byAuthor);
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        //results table
        String[] columns = {"Title","Author","Stock","Rack","Price"};
        tableModel  = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);

        add(topPanel,BorderLayout.NORTH);
        add(new JScrollPane(resultTable),BorderLayout.CENTER);

        //BUTTON ACTION 
        searchBtn.addActionListener(e -> handleSearch());
    }


    private void handleSearch(){

        tableModel.setRowCount(0);
        String input = searchField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter something.");
            return;
        }

        //BY TITLE
        if (byTitle.isSelected()) {

            Book book = Database.searchByTitle(input);

            if (book == null) {
                //Not in shop at all
                JOptionPane.showMessageDialog(this, "Book not in shop. Please give details.");
                askProcurement();

            } else if (book.getStock() == 0) {
                //In shop but out of stock
                Database.incrementRequest(book.getIsbn());
                JOptionPane.showMessageDialog(this, "Out of stock! Request noted.");

            } else {
                //In stock
                tableModel.addRow(new Object[]{
                    book.getTitle(),
                    book.getAuthor(),
                    book.getStock(),
                    book.getRackNo(),
                    "Rs." + book.getPrice()
                });
            }
        }

        //BY AUTHOR
        else {

            ArrayList<Book> list = Database.searchByAuthor(input);

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No books found.");
            } else {
                for (Book b : list) {
                    tableModel.addRow(new Object[]{
                        b.getTitle(),
                        b.getAuthor(),
                        b.getStock(),
                        b.getRackNo(),
                        "Rs."+ b.getPrice()
                    });
                }
            }
        }
    }

    private void askProcurement(){

        JTextField t = new JTextField();
        JTextField a = new JTextField();
        JTextField p = new JTextField();
        JTextField i = new JTextField();

        JPanel form = new JPanel(new GridLayout(4,2,5, 5));
        form.add(new JLabel("Title:")); form.add(t);
        form.add(new JLabel("Author:")); form.add(a);
        form.add(new JLabel("Publisher:")); form.add(p);
        form.add(new JLabel("ISBN:")); form.add(i);

        int res = JOptionPane.showConfirmDialog(this, form, "Book Details", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION){
            Database.addProcurementRequest(t.getText(), a.getText(), p.getText(), i.getText());
            JOptionPane.showMessageDialog(this, "Request saved!");
        }
    }
}
