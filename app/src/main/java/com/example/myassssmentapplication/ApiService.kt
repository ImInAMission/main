package com.example.myassssmentapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // Dynamic login endpoint: /footscray/auth or /sydney/auth or /br/auth
    @POST("{location}/auth")
    fun login(
        @Path("location") location: String,
        @Body request: LoginRequest
    ): Call<LoginResponse>

    // Dashboard endpoint: /dashboard/{keypass}
    @GET("dashboard/{keypass}")
    fun getDashboard(
        @Path("keypass") keypass: String
    ): Call<DashboardResponse>
}
