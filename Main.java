public class Main {
    public static void main(String[] args) {
        
        Database.loadBooks();
        Database.loadSales();
        
        new MainFrame().setVisible(true);
    }
}
