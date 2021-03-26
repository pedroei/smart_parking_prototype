package pt.ipvc.smartparkingprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_parking_slots.*
import pt.ipvc.smartparkingprototype.adapters.ParkingSlotAdapter
import pt.ipvc.smartparkingprototype.adapters.ParkingSpaceAdapter
import pt.ipvc.smartparkingprototype.models.ParkingLotItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem

class ParkingSlotsActivity : AppCompatActivity(), ParkingSpaceAdapter.OnItemClickListener {

    private lateinit var parkingSpaceAdapter: ParkingSpaceAdapter// lateint is a promise to kotlin that this variable will be initialized later

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

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

        // FAB clicks
        fab_add2.setOnClickListener {
            onMapButtonClicked()
        }

        fab_map2.setOnClickListener{
            val emptyLot = ParkingLotItem()
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra(EXTRA_PARKING_GEO, emptyLot)
            }
            startActivity(intent)
        }

        fab_lots2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {

            }
            startActivity(intent)
        }

        fab_qr_code2.setOnClickListener {
            Toast.makeText(this, "Go to QR Code", Toast.LENGTH_SHORT).show()
        }

    }

    fun onMapButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked =! clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            fab_map2.visibility = View.VISIBLE
            fab_lots2.visibility = View.VISIBLE
            fab_qr_code2.visibility = View.VISIBLE
        } else {
            fab_map2.visibility = View.INVISIBLE
            fab_lots2.visibility = View.INVISIBLE
            fab_qr_code2.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked) {
            fab_map2.startAnimation(fromBottom)
            fab_lots2.startAnimation(fromBottom)
            fab_qr_code2.startAnimation(fromBottom)
            fab_add2.startAnimation(rotateOpen)
        } else {
            fab_map2.startAnimation(toBottom)
            fab_lots2.startAnimation(toBottom)
            fab_qr_code2.startAnimation(toBottom)
            fab_add2.startAnimation(rotateClose)
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(applicationContext, "Clicked section",
            Toast.LENGTH_SHORT
        ).show()
    }
}