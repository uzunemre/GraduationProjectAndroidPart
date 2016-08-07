package emreuzun.com.tabletmenu.retrofit;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Emre on 23.5.2016.
 */
public interface PostOrder {



    //@POST("/TabletMenu2/api/ApiProduct/OrderPost")
    @POST("/api/ApiProduct/OrderPost")
    Call<String> createOrder(@Body Order order);



}