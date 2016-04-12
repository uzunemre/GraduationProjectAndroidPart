

/**
 * Created by Emre on 6.3.2016.
 */

package emreuzun.com.tabletmenu.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RetrofitModel {

    @SerializedName("categories")
    @Expose
    public List<CategoryRetrofit> categories = new ArrayList<CategoryRetrofit>();

    public List<CategoryRetrofit> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryRetrofit> categories) {
        this.categories = categories;
    }






}
