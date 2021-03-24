package pt.ipvc.smartparkingprototype.models

import java.io.Serializable

class ParkingSpaceSection(var id: Int, var code: String,
                          var slots: ArrayList<ParkingSpaceItem>?, var idLot: Int) :
    Serializable {
}