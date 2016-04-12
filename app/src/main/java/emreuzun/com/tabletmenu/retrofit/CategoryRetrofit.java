
/**
 * Created by Emre on 6.3.2016.
 */

package emreuzun.com.tabletmenu.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CategoryRetrofit implements Parcelable {

    public CategoryRetrofit()
    {

    }

    public CategoryRetrofit(String name, String picture, List<Product> products)
    {

       this.name = name;
       this.picture = picture;
       this.products = products;
    }

    public CategoryRetrofit(Parcel in)
    {

        this.name = in.readString();
        this.picture = in.readString();
       in.readTypedList(products,Product.CREATOR);


    }





    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("picture")
    @Expose
    private String picture;

    @SerializedName("products")
    @Expose
    public List<Product> products = new ArrayList<Product>();



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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(picture);
        dest.writeTypedList(products);

    }

    public static final Creator<CategoryRetrofit> CREATOR
            = new Creator<CategoryRetrofit>() {
        public CategoryRetrofit createFromParcel(Parcel in) {
            return new CategoryRetrofit(in);
        }

        public CategoryRetrofit[] newArray(int size) {
            return new CategoryRetrofit[size];
        }
    };




}