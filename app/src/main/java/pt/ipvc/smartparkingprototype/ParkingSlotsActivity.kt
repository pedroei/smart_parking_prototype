package pt.ipvc.smartparkingprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_parking_slots.*
import pt.ipvc.smartparkingprototype.adapters.ParkingSlotAdapter
import pt.ipvc.smartparkingprototype.adapters.ParkingSpaceAdapter
import pt.ipvc.smartparkingprototype.models.ParkingLotItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem

class ParkingSlotsActivity : AppCompatActivity(), ParkingSpaceAdapter.OnItemClickListener {

    private lateinit var parkingSpaceAdapter: ParkingSpaceAdapter// lateint is a promise to kotlin that this variable will be initialized later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_slots)

        val parkingLot = intent.getSerializableExtra(EXTRA_PARKING_LOT) as? ParkingLotItem

        if (parkingLot != null) {
            tvTitle2.text = parkingLot!!.name
            parkingSpaceAdapter = ParkingSpaceAdapter(parkingLot!!.sections, this)
            rvParkingSpaceItems.adapter = parkingSpaceAdapter
            rvParkingSpaceItems.layoutManager = LinearLayoutManager(this)
        }

        btnGoMap.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra(EXTRA_PARKING_GEO, parkingLot)
            }
            startActivity(intent)
        }

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(applicationContext, "Clicked section",
            Toast.LENGTH_SHORT
        ).show()
    }
}