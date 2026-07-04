import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManagerPanel extends JPanel {

    private JButton viewBtn;
    private JTable table;
    private DefaultTableModel tableModel;

    public ManagerPanel() {

        setLayout(new BorderLayout(10, 10));


        viewBtn = new JButton("View Demand");

        String[] columns ={"Book Name","Author","Request Count"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(viewBtn, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        viewBtn.addActionListener(e -> handleView());
    }


    private void handleView() {

        tableModel.setRowCount(0);
        ArrayList<Book> list = Database.getRequestCounts();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this,"No data found.");
            return;
        }
        for (Book b : list) {
            tableModel.addRow(new Object[]{
                b.getTitle(),
                b.getAuthor(),
                b.getRequestCount()
            });
        }
    }
}
