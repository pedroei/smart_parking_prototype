package pt.ipvc.smartparkingprototype

import android.content.Intent
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
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceSection

const val EXTRA_PARKING_LOT = "Parking lot item"

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

        /* Slots */
        val slotsList1: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList1.add(ParkingSpaceItem(1, 1,false, 1))
        slotsList1.add(ParkingSpaceItem(2, 2,false, 1))
        slotsList1.add(ParkingSpaceItem(3, 3,true, 1))

        val slotsList2: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList2.add(ParkingSpaceItem(4, 1, false, 5))
        slotsList2.add(ParkingSpaceItem(5, 2, false, 5))
        slotsList2.add(ParkingSpaceItem(6, 3, false, 5))

        val slotsList3: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList3.add(ParkingSpaceItem(7, 1, false, 2))
        slotsList3.add(ParkingSpaceItem(8, 2, true, 2))
        slotsList3.add(ParkingSpaceItem(9, 3, true, 2))

        val slotsList4: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList4.add(ParkingSpaceItem(10, 1, true, 3))
        slotsList4.add(ParkingSpaceItem(11, 2, false, 3))
        slotsList4.add(ParkingSpaceItem(12, 3, true, 3))

        val slotsList5: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList5.add(ParkingSpaceItem(13, 1, true, 4))
        slotsList5.add(ParkingSpaceItem(14, 2, true, 4))
        slotsList5.add(ParkingSpaceItem(15, 3, true, 4))

        val slotsList6: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList6.add(ParkingSpaceItem(16, 1,false, 7))
        slotsList6.add(ParkingSpaceItem(17, 2,false, 7))
        slotsList6.add(ParkingSpaceItem(18, 3,true, 7))

        val slotsList7: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList7.add(ParkingSpaceItem(19, 1,false, 8))
        slotsList7.add(ParkingSpaceItem(20, 2,false, 8))
        slotsList7.add(ParkingSpaceItem(21, 3,true, 8))

        val slotsList8: ArrayList<ParkingSpaceItem> = ArrayList()
        slotsList8.add(ParkingSpaceItem(22, 1,false, 6))
        slotsList8.add(ParkingSpaceItem(23, 2,false, 6))
        slotsList8.add(ParkingSpaceItem(24, 3,true, 6))

        /* Sections */
        val sectionList1: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList1.add(ParkingSpaceSection(1, "A", slotsList1, 4))
        sectionList1.add(ParkingSpaceSection(2, "B", slotsList3, 4))

        val sectionList2: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList2.add(ParkingSpaceSection(3, "A", slotsList4, 2))
        sectionList2.add(ParkingSpaceSection(4, "B", slotsList5, 2))
        sectionList2.add(ParkingSpaceSection(5, "C", slotsList2, 2))

        val sectionList3: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList3.add(ParkingSpaceSection(6, "A", slotsList8, 1))
        sectionList3.add(ParkingSpaceSection(7, "B", slotsList6, 1))

        val sectionList4: ArrayList<ParkingSpaceSection> = ArrayList()
        sectionList4.add(ParkingSpaceSection(8, "A", slotsList7, 3))

        /* Lots */
        val arrayList: ArrayList<ParkingLotItem> = ArrayList()
        arrayList.add(ParkingLotItem(1, "1º de Maio", R.drawable.pe1, "Viana do Castelo", "Open 24 hours", sectionList3))
        arrayList.add(ParkingLotItem(2, "Campo da Agonia", R.drawable.pe2, "Viana do Castelo", "Open 24 hours", sectionList2))
        arrayList.add(ParkingLotItem(3, "Gil Eanes", R.drawable.pe3, "Viana do Castelo", "Open 24 hours", sectionList4))
        arrayList.add(ParkingLotItem(4, "Braga Parque", R.drawable.pe1, "Braga", "Open 24 hours", sectionList1))


        return arrayList
    }

    // Click on grid item
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val items:ParkingLotItem = arrayList!!.get(position)

        val intent = Intent(this, ParkingSlotsActivity::class.java).apply {
            putExtra(EXTRA_PARKING_LOT, items)
        }
        startActivity(intent)
    }
}