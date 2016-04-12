package emreuzun.com.tabletmenu.retrofit;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Emre on 6.3.2016.
 */
public interface RestInterfaceController{


    @GET("/Test/api/Product/GetFood/1234567")
    void getJsonValues(Callback<RetrofitModel> response);


}
