package pt.ipvc.smartparkingprototype.models

import java.io.Serializable

class ParkingSpaceSection(var code: String, var slots: ArrayList<ParkingSpaceItem>?) :
    Serializable {
}