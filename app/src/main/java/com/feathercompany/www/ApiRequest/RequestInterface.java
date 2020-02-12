package com.feathercompany.www.ApiRequest;

import org.json.JSONArray;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {
    @POST("sessions")
    Call<String> sessions(@Body RequestBody object);


    @GET("boletins")
    Call<String> boletins(@Header("userToken") String userToken);

    @GET("boletins/view")
    Call<String> boletinsView(@Header("userToken") String userToken, @Query("boletimId") int boletimId, @Query("ano") int ano);

    @GET("faltas")
    Call<String> faltas(@Header("userToken") String userToken, @Query("boletimId") int boletimId, @Query("ano") int ano);

    @GET("horarios")
    Call<String> horarios(@Header("userToken") String userToken);
}
