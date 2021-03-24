package pt.ipvc.smartparkingprototype.models

import java.io.Serializable

class ParkingLotItem(var id: Int?, var name: String?, var image: Int?,
                     var location: String?, var timeOpen: String?,
                     var sections: ArrayList<ParkingSpaceSection>) : Serializable {

}