package com.victorsaico.cartive.Servicies;

import com.victorsaico.cartive.Models.Asiento;
import com.victorsaico.cartive.Models.CreateTicket;
import com.victorsaico.cartive.Models.Ticket;
import com.victorsaico.cartive.Models.Usuario;
import com.victorsaico.cartive.Models.Viaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by JARVIS on 22/11/2017.
 */

public interface ApiService {
    String API_BASE_URL = "https://cartive-alejovictor.c9users.io";

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<Usuario> login(@Field("username") String username,
                        @Field("password")String passwrod);

    @FormUrlEncoded
    @POST("api/v1/register")
    Call<ResponseMessage> register(@Field("username")String username,
                                   @Field("correo")String correo,
                                   @Field("password")String password,
                                   @Field("nombres")String nombres,
                                   @Field("apellidos")String apellidos,
                                   @Field("dni")String dni);
    @FormUrlEncoded
    @POST("api/v1/viajes")
    Call<List<Viaje>> buscarViaje(@Field("destino") String ciudad,
                                  @Field("fecha") String fecha);

    @FormUrlEncoded
    @POST("api/v1/tickets")
    Call<List<Ticket>> obtenerticket(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("api/v1/asientos")
    Call<List<Asiento>>showAsiento(@Field("bus_id") Integer bus_id);


    @POST("api/v1/createticket")
    Call<ResponseMessage> createticket(@Body CreateTicket createTicket);
                                       //@Field("num_asiento") Integer num_asiento),
                                       //@Body("nuasientos") List<Integer> asientos;

}
