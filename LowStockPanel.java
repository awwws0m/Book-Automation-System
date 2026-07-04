import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LowStockPanel extends JPanel {

    private JButton reportBtn;
    private JTable table;
    private DefaultTableModel tableModel;

    public LowStockPanel() {

        setLayout(new BorderLayout(10, 10));

        reportBtn = new JButton("Print Low Stock Report");

        String[] columns ={"Book Name","Publisher","Address","Copies to Order"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(reportBtn,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        reportBtn.addActionListener(e -> handleReport());
    }


    private void handleReport(){

        tableModel.setRowCount(0);
        ArrayList<String[]> list = Database.getLowStockBooks();

        if (list.isEmpty()){
            JOptionPane.showMessageDialog(this, "All books are stocked properly!");
            return;
        }

        for (String[] row : list){
            tableModel.addRow(row);
        }
    }
}