package emreuzun.com.tabletmenu.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("table_number")
    @Expose
    private int table_number;

    @SerializedName("mac_address")
    @Expose
    private String mac_address;

    @SerializedName("products")
    @Expose
    private String products;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("delivery")
    @Expose
    private boolean isDelivery;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTable_number() {
        return table_number;
    }

    public void setTable_number(int table_number) {
        this.table_number = table_number;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }
}
