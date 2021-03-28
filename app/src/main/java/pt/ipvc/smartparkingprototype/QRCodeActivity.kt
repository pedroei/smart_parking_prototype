package pt.ipvc.smartparkingprototype

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_q_r_code.*
import org.json.JSONObject
import pt.ipvc.smartparkingprototype.data.ParkingLotData
import pt.ipvc.smartparkingprototype.models.ParkingLotItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem
import pt.ipvc.smartparkingprototype.raspberryApi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class QRCodeActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)

        val request = ServiceBuilder.buildService(Endpoints::class.java)

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

            val spaceInfo = "Park ${parkingLot.name}, Section ${section.code}, Slot ${space.slot.toString()}"

            tvNoQRCode.visibility = View.INVISIBLE

            tvSpaceInfo.visibility = View.VISIBLE
            tvSpaceInfo.text = spaceInfo

            val activationLink = BASE_URL + "/${space.id}/on"
            if (activationLink.isNotBlank()) {
                val bitmap = generateQRCode(activationLink)
                ivQRCode.setImageBitmap(bitmap)
                ivQRCode.visibility = View.VISIBLE
                btnFinish.visibility = View.VISIBLE
            }

//            btnOpenSpace.visibility = View.VISIBLE
//
//            btnOpenSpace.setOnClickListener {
//                changeLightState(request, space, "on", it)
//            }

            btnFinish.setOnClickListener {
                changeLightState(request, space, "off", it)
            }
        } catch (e: Exception) {}

    }

    private fun generateQRCode(idSpace: String): Bitmap {
        val width = 250
        val height = 250
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(idSpace, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            println("Error generating QR Code: ${e.message}")
        }
        return bitmap
    }

    private fun changeLightState(
        request: Endpoints,
        space: ParkingSpaceItem,
        state: String,
        it: View
    ) {
        val call = request.changeLightState(space.id, state)

        call.enqueue(object : Callback<Pin> {
            override fun onResponse(call: Call<Pin>, response: Response<Pin>) {
                if (state == "on") {
                    Snackbar.make(it,
                        "Parking space unlocked ${response.body()}",
                        Snackbar.LENGTH_SHORT).show()
                    btnOpenSpace.isEnabled = false
                    btnFinish.visibility = View.VISIBLE
                } else if (state == "off") {
                    dt.reservedSpace = null
                    tvNoQRCode.visibility = View.VISIBLE
                    tvSpaceInfo.visibility = View.INVISIBLE
                    tvSpaceInfo.text = ""
                    btnOpenSpace.isEnabled = true
                    btnOpenSpace.visibility = View.INVISIBLE
                    btnFinish.visibility = View.INVISIBLE
                    ivQRCode.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<Pin>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, LENGTH_LONG).show()
    //                        Snackbar.make(it, "Error communicating with Raspberry Pi", Snackbar.LENGTH_SHORT).show()
            }

        })
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