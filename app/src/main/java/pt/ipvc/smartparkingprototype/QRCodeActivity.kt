package pt.ipvc.smartparkingprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_q_r_code.*
import pt.ipvc.smartparkingprototype.data.ParkingLotData
import pt.ipvc.smartparkingprototype.models.ParkingLotItem
import java.lang.Exception

class QRCodeActivity : AppCompatActivity() {

    val dataClass = ParkingLotData()

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)

        // FAB clicks
        fab_add3.setOnClickListener {
            onMapButtonClicked()
        }

        fab_map3.setOnClickListener{
            val emptyLot = ParkingLotItem()
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra(EXTRA_PARKING_GEO, emptyLot)
            }
            startActivity(intent)
        }

        fab_lots3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        fab_qr_code3.setOnClickListener {
            Snackbar.make(it,"Already in this screen", Snackbar.LENGTH_SHORT).show()
        }

        try {
            val space = dt.getReservedSlot()
            val section = dt.getSectionById(space.idSection)
            val parkingLot = dt.getParkingLotById(section?.idLot!!)

            val msg = "Park ${parkingLot.name}, Section ${section.code}, Slot ${space.slot.toString()}"
            Toast.makeText(applicationContext, msg , LENGTH_SHORT).show()
        } catch (e: Exception) {

        }

    }

    fun onMapButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked =! clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            fab_map3.visibility = View.VISIBLE
            fab_lots3.visibility = View.VISIBLE
            fab_qr_code3.visibility = View.VISIBLE
        } else {
            fab_map3.visibility = View.INVISIBLE
            fab_lots3.visibility = View.INVISIBLE
            fab_qr_code3.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked) {
            fab_map3.startAnimation(fromBottom)
            fab_lots3.startAnimation(fromBottom)
            fab_qr_code3.startAnimation(fromBottom)
            fab_add3.startAnimation(rotateOpen)
        } else {
            fab_map3.startAnimation(toBottom)
            fab_lots3.startAnimation(toBottom)
            fab_qr_code3.startAnimation(toBottom)
            fab_add3.startAnimation(rotateClose)
        }
    }
}