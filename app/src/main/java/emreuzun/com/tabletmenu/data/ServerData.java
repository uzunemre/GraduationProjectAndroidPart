package emreuzun.com.tabletmenu.data;

import java.util.ArrayList;
import java.util.List;

import emreuzun.com.tabletmenu.retrofit.CategoryRetrofit;
import emreuzun.com.tabletmenu.retrofit.Product;

/**
 * Created by Emre on 12.4.2016.
 */
public class ServerData {


    public static List<Product> getProductsFromServer(CategoryRetrofit cat)
    {
        List<Product> items = new ArrayList<Product>();
        for (int i=0 ; i<cat.getProducts().size();i++)
        {
            Product item = new Product(cat.getProducts().get(i).getId(),cat.getProducts().get(i).getDescription(),
                    cat.getProducts().get(i).getName(),cat.getProducts().get(i).getPicture(),
                    cat.getProducts().get(i).getPrice(),cat.getProducts().get(i).getCalorie());

            items.add(item);
        }

        return  items;
    }

    public static List<CategoryRetrofit> getCategoryServer(ArrayList<CategoryRetrofit> cat)
    {

        List<CategoryRetrofit> items = new ArrayList<CategoryRetrofit>();

        for(int i=0;i<cat.size();i++)
        {
            //Category item = new Category(Long.parseLong("0" + i),cat.get(i).getName(),"http://assets.kraftfoods.com/recipe_images/opendeploy/125635_640x428.jpg", 5);
            CategoryRetrofit item = new CategoryRetrofit(cat.get(i).getName(),cat.get(i).getPicture(),
                    cat.get(i).getProducts());
            items.add(item);
        }

        return items;
    }





}
