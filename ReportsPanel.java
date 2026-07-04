import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList; 

public class ReportsPanel extends JPanel {

    private JTextField fromField, toField;
    private JButton generateBtn;
    private JTable table;
    private DefaultTableModel tableModel;

    public ReportsPanel() {

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());

        fromField = new JTextField(10);
        toField = new JTextField(10);
        generateBtn = new JButton("Generate Report");

        topPanel.add(new JLabel("From (yyyy-mm-dd):"));
        topPanel.add(fromField);
        topPanel.add(new JLabel("To (yyyy-mm-dd):"));
        topPanel.add(toField);
        topPanel.add(generateBtn);

        String[] columns ={"ISBN","Book Name","Publisher","Copies Sold","Revenue"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        generateBtn.addActionListener(e -> handleGenerate());
    }


    private void handleGenerate(){

        tableModel.setRowCount(0);

        String from = fromField.getText().trim();
        String to = toField.getText().trim();

        if (from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both dates.");
            return;
        }

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        ArrayList<String[]> stats = Database.getSalesStats(fromDate, toDate);

        if (stats.isEmpty()){
            JOptionPane.showMessageDialog(this, "No sales in this period.");
            return;
        }

        for (String[] row : stats) {
            tableModel.addRow(row);
        }
    }
}
