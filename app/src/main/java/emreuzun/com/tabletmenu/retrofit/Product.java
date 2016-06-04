


package emreuzun.com.tabletmenu.retrofit;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Product implements Parcelable {

    @SerializedName("Id")
    @Expose
    public Integer Id;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("picture")
    @Expose
    public String picture;
    @SerializedName("price")
    @Expose
    public Float price;

    @SerializedName("calorie")
    @Expose
    public Integer calorie;

    int total=1;


    public Product(Integer Id, String description, String name, String picture, Float price, Integer calorie)
    {
        this.Id = Id;
        this.description = description;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.calorie = calorie;
    }

    public Product(Parcel in)
    {
        this.Id = in.readInt();
        this.description = in.readString();
        this.name = in.readString();
        this.picture = in.readString();
        this.price = in.readFloat();
        this.calorie = in.readInt();
    }

    public Product()
    {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getSumPrice() {
        return (price*total);
    }

    public String getStrPrice() {
        return "\u20BA "+getPrice();
    }

    public Integer getCalorie() {
        return calorie;
    }

    public void setCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

            dest.writeInt(Id);
            dest.writeString(description);
            dest.writeString(name);
            dest.writeString(picture);
            dest.writeFloat(price);
            dest.writeInt(calorie);

    }

    public static final Creator<Product> CREATOR =
            new Creator<Product>() {

                @Override
                public Product createFromParcel(Parcel source) {
                    return new Product(source);
                }

                @Override
                public Product[] newArray(int size) {
                    return new Product[size];
                }
            };

}