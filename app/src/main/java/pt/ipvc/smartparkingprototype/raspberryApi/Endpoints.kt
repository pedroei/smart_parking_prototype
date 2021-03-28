package pt.ipvc.smartparkingprototype.raspberryApi

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {

    @PATCH("/pins/{id}")
    fun changeLightState(@Path("id") id: Int,
                         @Body body: LightStateRequest
    ): Call<Pin>
}