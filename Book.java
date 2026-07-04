class Book {

    String isbn;
    String title;
    String author;
    String publisher;
    String publisherAddress;
    double price;
    int stock;
    String rackNo;
    int reqCount;
    int threshold;
    int avgProcurementDay;

    Book(String isbn, String title, String author, String publisher,
         String publisherAddress, double price, int stock,
         String rackNo, int reqCount, int threshold, int avgProcurementDay) {

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publisherAddress = publisherAddress;
        this.price = price;
        this.stock = stock;
        this.rackNo = rackNo;
        this.reqCount = reqCount;
        this.threshold = threshold;
        this.avgProcurementDay = avgProcurementDay;
    }

    //GETTERS
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public String getPublisherAddress() { return publisherAddress; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getRackNo() { return rackNo; }
    public int getRequestCount() { return reqCount; }
    public int getThreshold() { return threshold; }
    public int getAvgProcurementDay() { return avgProcurementDay; }

    //SETTERS
    public void setStock(int stock)  {this.stock = stock;}
    public void setRequestCount(int reqCount)  {this.reqCount = reqCount;}
    public void setPrice(double price)  {this.price = price;}
    public void setThreshold(int threshold) {this.threshold = threshold;}

    public String toFileString(){
        return isbn + "|" + title + "|" + author + "|" + publisher + "|" +
               publisherAddress + "|" + price + "|" + stock + "|" +
               rackNo + "|" + reqCount + "|" + threshold + "|" +
               avgProcurementDay;
    }

    public static Book fromFileString(String line){
        String[] sec = line.split("\\|");
        return new Book(
            sec[0],
            sec[1],
            sec[2],
            sec[3],
            sec[4],
            Double.parseDouble(sec[5]),
            Integer.parseInt(sec[6]),
            sec[7],
            Integer.parseInt(sec[8]),
            Integer.parseInt(sec[9]),
            Integer.parseInt(sec[10])
        );
    }
}