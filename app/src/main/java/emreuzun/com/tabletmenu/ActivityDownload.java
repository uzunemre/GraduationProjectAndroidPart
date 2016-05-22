package emreuzun.com.tabletmenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import emreuzun.com.tabletmenu.retrofit.CategoryRetrofit;
import emreuzun.com.tabletmenu.retrofit.Product;
import emreuzun.com.tabletmenu.retrofit.RestInterfaceController;
import emreuzun.com.tabletmenu.retrofit.RetrofitModel;
import emreuzun.com.tabletmenu.utils.PhoneInfo;
import emreuzun.com.tabletmenu.utils.ShowMessage;
import emreuzun.com.tabletmenu.utils.UtilConstant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ActivityDownload extends Activity {


    ShowMessage showMessage;
    Button product_download;
    private ProgressDialog progressDialog;
    private RestAdapter restAdapter;
    private RestInterfaceController restInterface;
    private ArrayList<Product> list_product;
    private ArrayList<CategoryRetrofit> list_category = new ArrayList<CategoryRetrofit>();
    private String base_path = "/TabletMenu/api/ApiProduct/GetFood/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        product_download = (Button) findViewById(R.id.btndownload);
        showMessage = new ShowMessage(this);
        String mac_address = PhoneInfo.getWifiMacAddress();
        mac_address = mac_address.replaceAll(":","");

        base_path = base_path +mac_address;
        //showMessage.Toast(mac_address);
       // Toast.makeText(getApplicationContext(),mac_address,Toast.LENGTH_LONG).show();




    }


    public void btnDownloadClick(View view) {

        showMessage.showProgressDialog("Lütfen Bekleyiniz");

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(UtilConstant.URL+base_path)
                .build();

        restInterface = restAdapter.create(RestInterfaceController.class);


        showMessage.Toast(UtilConstant.URL+base_path);






      restInterface.getJsonValues(new Callback<RetrofitModel>() {
            @Override
            public void success(RetrofitModel retrofitModel, Response response) {

               // showMessage.Toast(""+retrofitModel.getCategories().size());

                if(retrofitModel.getCategories().size()==0)
                {
                    showMessage.Toast("Aktivasyon gerekli");
                    showMessage.stopProgressDialog();
                }

                else
                {
                    showMessage.stopProgressDialog();
                    //showMessage.Toast("Success+ActivityDownload"+retrofitModel.getCategories().get(0).
                    //      getProducts().get(0).getName());
               list_category = getCategory(retrofitModel, response);
                //  showMessage.Toast(list_category.get(0).getProducts().get(0).getName());
                Intent intent = new Intent(ActivityDownload.this, ActivityMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", list_category);
                intent.putExtras(bundle);
                startActivity(intent);
                }



            }

            @Override
            public void failure(RetrofitError error) {

                showMessage.Toast(error.getMessage());
            }
        });




    }


    ArrayList<CategoryRetrofit> getCategory(RetrofitModel retrofitModel, Response response)
    {
        ArrayList<CategoryRetrofit> items = new ArrayList<CategoryRetrofit>();

        int category_size = retrofitModel.getCategories().size();
        String categories;

        for (int i=0; i<category_size;i++)
        {
            list_product = new ArrayList<Product>(); //  It is important to new. Otherwise all list products
            // of categories will be same.


            for(int j=0 ; j<retrofitModel.getCategories().get(i).getProducts().size();j++)
            {

                Product products = new Product();
                products.setId(retrofitModel.getCategories().get(i).getProducts().get(j).getId());
                products.setDescription(retrofitModel.getCategories().get(i).getProducts().get(j).getDescription());
                products.setName(retrofitModel.getCategories().get(i).getProducts().get(j).getName());
                products.setPicture(UtilConstant.URL_IMAGES+retrofitModel.getCategories().get(i).getProducts().get(j).getPicture());
                products.setPrice(retrofitModel.getCategories().get(i).getProducts().get(j).getPrice());
                list_product.add(products);
            }




            /*CategoryRetrofit item = new CategoryRetrofit(retrofitModel.getCategories().get(i).getName(),
                    retrofitModel.getCategories().get(i).getPicture(),list_product);*/

            CategoryRetrofit category_item = new CategoryRetrofit();
            category_item.setName(retrofitModel.getCategories().get(i).getName());
            category_item.setPicture(retrofitModel.getCategories().get(i).getPicture());
            category_item.setProducts(list_product);




            items.add(category_item);



        }



        return items;


    }

}
