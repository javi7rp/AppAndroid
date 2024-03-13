package com.example.hospedajetema3.retrofit

import com.example.hospedajetema3.models.Juego
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @POST("endp/auth")
    suspend fun auth(@Body loginUser: RequestLoginUser): Response<ResponseLogin>

    @POST("endp/registro")
    suspend fun registro(@Body registerUser: RequestRegisterUser) : Response<ResponseRegister>

    @GET("endp/juego")
    suspend fun getJuegos(@Header("api-key") apiKey: String): Response<ResponseListJuegos>

    @DELETE("endp/juego")
    suspend fun borrarJuego(@Header("api-key") apiKey: String, @Query("id") JuegoId: String): Response<ResponseDeleteJuego>

    @POST("endp/juego")
    suspend fun addJuego(@Header("api-key") apiKey: String, @Body juegoData: RequestAddJuego): Response<ResponseAddJuego>

    @PUT("endp/juego")
    suspend fun editarJuego(@Header("api-key") apiKey: String, @Query("id") juegoId:String, @Body juegoData: RequestModifyJuego): Response<ResponseModifyJuego>
}