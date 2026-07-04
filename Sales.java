import java.time.LocalDate;

class Sales{

	String isbn;
    int quantity;
    double totalPrice;
    LocalDate date;

    Sales(String isbn,int quantity,double totalPrice,LocalDate date){
    	this.isbn = isbn;
    	this.quantity = quantity;
    	this.totalPrice = totalPrice;
    	this.date = date;
    }

    //GETTER:
    String getIsbn(){
		return isbn;
	}
	int getQuantity(){
		return quantity;
	}
	double getTotalPrice(){
		return totalPrice;
	}
	LocalDate getDate(){
		return date;
	}

	public String toFileString() {
        return isbn + "|" + date.toString() + "|" + quantity + "|" + totalPrice;
    }

    public static Sales fromFileString(String line){
    	String[] sec = line.split("\\|");
    	return new Sales(
    	sec[0],
    	Integer.parseInt(sec[2]),
    	Double.parseDouble(sec[3]),
    	LocalDate.parse(sec[1])
    	);
    }
}