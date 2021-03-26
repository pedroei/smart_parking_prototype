package pt.ipvc.smartparkingprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pt.ipvc.smartparkingprototype.models.ParkingLotItem

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var selected_parking_lot: ParkingLotItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        selected_parking_lot = (intent.getSerializableExtra(EXTRA_PARKING_GEO) as? ParkingLotItem)!!

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val pe_1_maio = LatLng(41.69546913970562, -8.82892591713366)
        mMap.addMarker(MarkerOptions().position(pe_1_maio).title("PE - 1º de Maio"))

        val pe_campo_agonia = LatLng(41.691572004150586, -8.836688477030924)
        mMap.addMarker(MarkerOptions().position(pe_campo_agonia).title("PE - Campo da Agonia"))

        val pe_gil_eanes = LatLng(41.68979067792167, -8.830456039883426)
        mMap.addMarker(MarkerOptions().position(pe_gil_eanes).title("PE - Gil Eanes"))

        val pe_braga_parque = LatLng(41.55766747202138, -8.407030445902803)
        mMap.addMarker(MarkerOptions().position(pe_braga_parque).title("PE - Braga Parque"))

        if (selected_parking_lot?.id != -1) {
            val latLng = LatLng(selected_parking_lot?.coordinates?.latitude!!, selected_parking_lot?.coordinates?.longitude!!)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f))
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.69226378793108, -8.832979027357172), 15.0f))
        }
    }
}