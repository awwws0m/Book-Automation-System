import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database{

	private static final String BOOK = "books.txt";
	private static final String SALES = "sales.txt";
	private static final String PROCUREMENT = "procurement.txt";

	private static ArrayList<Book> books = new ArrayList<>();
	private static ArrayList<Sales> sales = new ArrayList<>();

	public static void loadBooks(){
		books.clear();
		try(BufferedReader br = new BufferedReader(new FileReader(BOOK))){
			String line;
			while ((line = br.readLine()) != null){
				if(!line.trim().isEmpty()){
					books.add(Book.fromFileString(line));
				}
			}
		}
		catch(IOException e){
			System.out.println("Books.txt not found");
		}
	}

	public static void loadSales(){
		sales.clear();
		try(BufferedReader br = new BufferedReader(new FileReader(SALES))){
			String line;
			while ((line = br.readLine()) != null){
				if(!line.trim().isEmpty()){
					sales.add(Sales.fromFileString(line));
				}
			}
		}
		catch(IOException e){
			System.out.println("Sales.txt not found");
		}
	}

	private static void saveBooks(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK))) {
            for(Book b : books){
                bw.write(b.toFileString());
                bw.newLine();
            }
        } catch (IOException e){
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private static void saveSales() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(SALES))) {
            for(Sales s : sales){
                bw.write(s.toFileString());
                bw.newLine();
            }
        } catch (IOException e){
            System.out.println("Error saving sales: " + e.getMessage());
        }
    }

    public static Book searchByTitle(String title){
    	for(Book b : books){
    		if(b.getTitle().equalsIgnoreCase(title.trim())){
    			return b;
    		}
    	}
    	return null; //NOT FOUND
    }

    public static ArrayList<Book> searchByAuthor(String author){
    	ArrayList<Book> res = new ArrayList<>();
    	for(Book b : books){
    		if(b.getAuthor().equalsIgnoreCase(author.trim())){
    			res.add(b);
    		}
    	}
    	return res;
    }

    public static void incrementRequest(String isbn){
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                b.setRequestCount(b.getRequestCount() + 1);
                saveBooks();
                return;
            }
        }
    }

    public static void addProcurementRequest(String title,String author,
                                              String publisher, String isbn) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PROCUREMENT,true))) {
            
            String line = title + "|" + author + "|" + publisher + "|" +
                          isbn + "|" + LocalDate.now().toString();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving procurement: "+ e.getMessage());
        }
    }

    public static String processSale(String isbn){
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn.trim())){

                if (b.getStock()<= 0){
                    return "OUT"; //out of stock
                }

                b.setStock(b.getStock() - 1); //Reduce the stock by 1

                //Record the sale
                Sales sale = new Sales(isbn,1,b.getPrice(),LocalDate.now());
                sales.add(sale);

                //Save both files
                saveBooks();
                saveSales();

                //Build and return receipt
                return "===== BOOK SHOP RECEIPT =====" + "\n" +
                       "Book  : " + b.getTitle()      + "\n" +
                       "Author: " + b.getAuthor()     + "\n" +
                       "ISBN  : " + isbn               + "\n" +
                       "Price : Rs." + b.getPrice()   + "\n" +
                       "Date  : " + LocalDate.now()   + "\n" +
                       "=============================";
            }
        }
        return null; //book not found
    }

    public static boolean updateStock(String isbn, int copiesAdded) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn.trim())){
                b.setStock(b.getStock() + copiesAdded);
                saveBooks();
                return true;
            }
        }
        return false;
    }
    public static ArrayList<String[]> getSalesStats(LocalDate from,LocalDate to){
        ArrayList<String[]> result = new ArrayList<>();

        for(Book b : books){
            int copiesSold = 0;
            double revenue = 0;

            for(Sales s : sales){
                if (s.getIsbn().equalsIgnoreCase(b.getIsbn()) &&
                    !s.getDate().isBefore(from) &&
                    !s.getDate().isAfter(to)){

                    copiesSold += s.getQuantity();
                    revenue += s.getTotalPrice();
                }
            }
            if(copiesSold > 0){
                result.add(new String[]{
                    b.getIsbn(),
                    b.getTitle(),
                    b.getPublisher(),
                    String.valueOf(copiesSold),
                    "Rs."+ revenue
                });
            }
        }
        return result;
    }
    public static ArrayList<String[]> getLowStockBooks(){
        ArrayList<String[]> result = new ArrayList<>();
        LocalDate twoWeeksAgo = LocalDate.now().minusDays(14);

        for (Book b : books) {

            if (b.getStock() < b.getThreshold()) {

                //Count copies sold in last 2 weeks
                int soldIn2Weeks = 0;
                for (Sales s : sales) {
                    if (s.getIsbn().equalsIgnoreCase(b.getIsbn()) &&
                        !s.getDate().isBefore(twoWeeksAgo)) {
                        soldIn2Weeks += s.getQuantity();
                    }
                }
                int inventoryNeeded = soldIn2Weeks * b.getAvgProcurementDay();
                int toProcure = inventoryNeeded - b.getStock();
                if (toProcure < 1) toProcure = 1; // at least order 1

                result.add(new String[]{
                    b.getTitle(),
                    b.getPublisher(),
                    b.getPublisherAddress(),
                    String.valueOf(toProcure)
                });
            }
        }

        return result;
    }

    public static ArrayList<Book> getRequestCounts(){
        ArrayList<Book> sorted = new ArrayList<>(books);

        for (int i=0; i<sorted.size() - 1; i++) {
            for (int j=0; j<sorted.size() - 1 - i; j++) {
                if (sorted.get(j).getRequestCount() < sorted.get(j + 1).getRequestCount()) {
                    Book temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }

    public static ArrayList<Book> getAllBooks() {
        return books;
    }
}
