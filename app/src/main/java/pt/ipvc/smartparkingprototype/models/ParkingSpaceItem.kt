package pt.ipvc.smartparkingprototype.models

import java.io.Serializable

class ParkingSpaceItem(var id: Int, var slot: Int?,
                       var reserved: Boolean?, var idSection: Int) : Serializable{
}