package tk.matheuslucena.realidade;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit2.Call;
import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.Product;

public interface RestInterface {


    @GET("/WebService/ProductList/")
    void getWSRestProduct(Callback<List<Product>> response);

    @GET("/WebService/OrderList/")
    void getWRestPedidos(Callback<List<Order>> response);


    @FormUrlEncoded
    @POST("/WebServicePHP/InsertOrderFinalizedJson.php")
    void insertUserJson(@Field("json") String json, Callback<Response> callBack);


    @FormUrlEncoded
    @POST("/WebServicePHP/AuthenticationClient.php")
    void insertUserAuthentication(@Field("json") String json, Callback<Response> callBack);

    @FormUrlEncoded
    @POST("/WebServicePHP/UpdateUser.php")
    void updateUser(@Field("json") String json, Callback<Response> callBack);
}
