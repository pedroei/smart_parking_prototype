package pt.ipvc.smartparkingprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import pt.ipvc.smartparkingprototype.adapters.ParkingLotAdapter
import pt.ipvc.smartparkingprototype.models.ParkingLotItem

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var arrayList:ArrayList<ParkingLotItem> ? = null
    private var gridView:GridView ? = null
    private var parkingLotAdapter:ParkingLotAdapter ? = null

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_main)

        // Message from Login
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        //GridView
        gridView = gvParks
        arrayList = ArrayList()
        arrayList = setDataList()
        parkingLotAdapter = ParkingLotAdapter(applicationContext, arrayList!!)
        gridView?.adapter = parkingLotAdapter
        gridView?.onItemClickListener = this

        // FAB clicks
        fab_add.setOnClickListener {
            onMapButtonClicked()
        }

        fab_map.setOnClickListener{
            Toast.makeText(this, "MSG: $message --> This is the map", Toast.LENGTH_SHORT).show()
        }

        fab_lots.setOnClickListener {
            Toast.makeText(this, "MSG: $message --> Show all lots", Toast.LENGTH_SHORT).show()
        }

        fab_qr_code.setOnClickListener {
            Toast.makeText(this, "MSG: $message --> QR Code", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onMapButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked =! clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            fab_map.visibility = View.VISIBLE
            fab_lots.visibility = View.VISIBLE
            fab_qr_code.visibility = View.VISIBLE
        } else {
            fab_map.visibility = View.INVISIBLE
            fab_lots.visibility = View.INVISIBLE
            fab_qr_code.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked) {
            fab_map.startAnimation(fromBottom)
            fab_lots.startAnimation(fromBottom)
            fab_qr_code.startAnimation(fromBottom)
            fab_add.startAnimation(rotateOpen)
        } else {
            fab_map.startAnimation(toBottom)
            fab_lots.startAnimation(toBottom)
            fab_qr_code.startAnimation(toBottom)
            fab_add.startAnimation(rotateClose)
        }
    }

    private fun setDataList(): ArrayList<ParkingLotItem> {
        val arrayList: ArrayList<ParkingLotItem> = ArrayList()

        arrayList.add(ParkingLotItem("1º de Maio", R.drawable.pe1, "Viana do Castelo", "Open 24 hours", 1))
        arrayList.add(ParkingLotItem("Campo da Agonia", R.drawable.pe2, "Viana do Castelo", "Open 24 hours", 1))
        arrayList.add(ParkingLotItem("Gil Eanes", R.drawable.pe3, "Viana do Castelo", "Open 24 hours", 1))
        arrayList.add(ParkingLotItem("Braga Parque", R.drawable.pe1, "Braga", "Open 24 hours", 1))

        arrayList.add(ParkingLotItem("1º de Maio", R.drawable.pe1, "Viana do Castelo", "Open 24 hours",1))
        arrayList.add(ParkingLotItem("Campo da Agonia", R.drawable.pe2, "Viana do Castelo", "Open 24 hours",1))
        arrayList.add(ParkingLotItem("Gil Eanes", R.drawable.pe3, "Viana do Castelo", "Open 24 hours",1))
        arrayList.add(ParkingLotItem("Braga Parque", R.drawable.pe1, "Braga", "Open 24 hours",1))

        arrayList.add(ParkingLotItem("1º de Maio", R.drawable.pe1, "Viana do Castelo", "Open 24 hours",1))
        arrayList.add(ParkingLotItem("Campo da Agonia", R.drawable.pe2, "Viana do Castelo", "Open 24 hours",1))
        arrayList.add(ParkingLotItem("Gil Eanes", R.drawable.pe3, "Viana do Castelo", "Open 24 hours",1))
        arrayList.add(ParkingLotItem("Braga Parque", R.drawable.pe1, "Braga", "Open 24 hours",1))

        return arrayList
    }

    // Click on grid item
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val items:ParkingLotItem = arrayList!!.get(position)
        Toast.makeText(applicationContext, "${items.name} with capacity of ${items.slots}", LENGTH_SHORT).show()
    }
}