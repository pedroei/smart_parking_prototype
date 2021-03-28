package pt.ipvc.smartparkingprototype.raspberryApi

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {

    @GET("/{id}/{state}")
    fun changeLightState(@Path("id") id: Int,
                         @Path("state") state: String
    ): Call<Pin>
}