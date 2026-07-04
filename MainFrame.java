import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Book Shop Automation System");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JTabbedPane tabs = new JTabbedPane();


        tabs.addTab("Search Book",new SearchPanel());
        tabs.addTab("Sales",new SalesPanel());
        tabs.addTab("Inventory Update",new InventoryPanel());
        tabs.addTab("View Report",new ReportsPanel());
        tabs.addTab("Low Stock Items",new LowStockPanel());
        tabs.addTab("For Manager", new ManagerPanel());

        add(tabs);
    }
}
