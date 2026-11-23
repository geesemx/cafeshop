package cafeshopmanagementsystem.model;

import java.sql.Date;

public class productData {

    private Integer id;
    private String productId;
    private String productName;
    private String type;
    private Integer stock;
    private Double price;
    private String status;
    private String image;
    private Date date;
    private Integer quantity;

    public productData(Integer id, String productId,
             String productName, String type, Integer stock,
             Double price, String status, String image, Date date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }
    
    public productData(Integer id, String productId, String productName, 
            String type, Integer quantity, Double price, String image, Date date){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
    
    public String getType(){
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }
    
    public Integer getQuantity(){
        return quantity;
    }

    public void setProductId(String productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setType(String type) { this.type = type; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setPrice(Double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setImage(String image) { this.image = image; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
