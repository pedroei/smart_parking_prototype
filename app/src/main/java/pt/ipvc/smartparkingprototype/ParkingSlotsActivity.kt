package pt.ipvc.smartparkingprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_parking_slots.*
import pt.ipvc.smartparkingprototype.models.ParkingLotItem

class ParkingSlotsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_slots)

        val parkingLot = intent.getSerializableExtra(EXTRA_PARKING_LOT) as? ParkingLotItem

        tv1.text = "${parkingLot?.name} ${parkingLot?.location} ${parkingLot?.location} ${parkingLot?.timeOpen} ${parkingLot?.slots}"
    }
}