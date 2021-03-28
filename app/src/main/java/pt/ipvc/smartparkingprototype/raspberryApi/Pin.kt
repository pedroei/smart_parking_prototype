package pt.ipvc.smartparkingprototype.raspberryApi

data class Pin(
    val id: Int,
    val pin_num: Int,
    val color: String,
    val state: String
)

class LightStateRequest(
    val state: String
)
