package bruteforce.ro.financedemo.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForgeApi {
    String BASE_URL = "https://forex.1forge.com";
    String api_key="hmjsYxJ9fwZ671q7QwHZdzBdAga7icBf";

    @GET("/1.0.3/convert")
    Call<ConvertResponse> convert(@Query("from") String status, @Query("to") String to, @Query("quantity") String quantity,@Query("api_key") String api_key);
}
