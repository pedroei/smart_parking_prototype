package pt.ipvc.smartparkingprototype.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_parking_space.view.*
import kotlinx.android.synthetic.main.item_parking_space_slot.view.*
import pt.ipvc.smartparkingprototype.R
import pt.ipvc.smartparkingprototype.models.ParkingSpaceSection

class ParkingSlotAdapter(
    private val parkingSection: ParkingSpaceSection,
    private val listener: View.OnClickListener
)
    : RecyclerView.Adapter<ParkingSlotAdapter.ParkingSlotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingSlotViewHolder {
        return ParkingSlotViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_parking_space_slot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ParkingSlotViewHolder, position: Int) {
        val curSlot = parkingSection.slots!![position]
        holder.itemView.apply {
            setOnClickListener(listener)
            tvParkingSlotTitle.text = "Slot ${curSlot.slot}"
        }
    }

    override fun getItemCount(): Int {
        return parkingSection.slots!!.size
    }

    class ParkingSlotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSlotTitle = view.tvParkingSlotTitle
    }
}