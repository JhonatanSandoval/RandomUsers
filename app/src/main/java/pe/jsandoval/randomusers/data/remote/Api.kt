package pe.jsandoval.randomusers.data.remote

import pe.jsandoval.randomusers.data.remote.response.UsersResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/")
    suspend fun getUsers(@Query("results") results: Int = 50): Response<UsersResultResponse>

}