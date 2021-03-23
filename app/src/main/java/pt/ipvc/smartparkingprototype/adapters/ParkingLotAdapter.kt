package pt.ipvc.smartparkingprototype.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import pt.ipvc.smartparkingprototype.R
import pt.ipvc.smartparkingprototype.models.ParkingLotItem

class ParkingLotAdapter(var context: Context, var arrayList: ArrayList<ParkingLotItem>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = View.inflate(context, R.layout.card_view_item_grid, null)
        val icons: ImageView = view.findViewById(R.id.icons)
        val names:TextView = view.findViewById((R.id.tvName))
        val location:TextView = view.findViewById((R.id.tvLocation))
        val timeOpen:TextView = view.findViewById((R.id.tvTimeOpen))

        val listItem: ParkingLotItem = arrayList.get(position)

        icons.setImageResource(listItem.image!!)
        names.text = listItem.name
        location.text = listItem.location
        timeOpen.text = listItem.timeOpen

        return view
    }
}