package pt.ipvc.smartparkingprototype.adapters

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_parking_slots.view.*
import kotlinx.android.synthetic.main.item_parking_space.view.*
import kotlinx.android.synthetic.main.item_parking_space_slot.view.*
import pt.ipvc.smartparkingprototype.R
import pt.ipvc.smartparkingprototype.data.ParkingLotData
import pt.ipvc.smartparkingprototype.dt
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceSection

class ParkingSpaceAdapter(
    private val parkingSpaces: ArrayList<ParkingSpaceSection>,
    private val listener: OnItemClickListener)
    : RecyclerView.Adapter<ParkingSpaceAdapter.ParkingSpaceViewHolder>(){

    private val viewPool = RecyclerView.RecycledViewPool()

    inner class ParkingSpaceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            // click line
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) { // avoid clicking while animation occurring
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingSpaceViewHolder {
        return ParkingSpaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_parking_space,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ParkingSpaceViewHolder, position: Int) {
        val curSpace = parkingSpaces[position]
        holder.itemView.apply {
            tvParkingSpaceTitle.text = "Section ${curSpace.code}"
        }

        val childLayoutManager = LinearLayoutManager(holder.itemView.rviewSlots.context, RecyclerView.VERTICAL, false)

        holder.itemView.rviewSlots.apply {
            layoutManager = childLayoutManager
            adapter = ParkingSlotAdapter(parkingSpaces.get(position), View.OnClickListener {
                val lot = dt.getParkingLotById(parkingSpaces[position].idLot).name
                val section = parkingSpaces[position].code
                val slot = it.tvParkingSlotTitle.text.removePrefix("Slot ")

                MaterialAlertDialogBuilder(context)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to book the space $section-$slot in $lot?")
                    .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                        Snackbar.make(holder.itemView,"Canceled", Snackbar.LENGTH_SHORT).show()
                    }
                    .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                        val intSlot = Integer.parseInt(slot.toString())
                        val spaceToBeReserved: ParkingSpaceItem = parkingSpaces[position].slots?.get(intSlot - 1)!!
                        val res = dt.reserveSlot(spaceToBeReserved)
                        if (res) Snackbar.make(holder.itemView, "Booked $section-$intSlot", Snackbar.LENGTH_SHORT).show()
                        else Snackbar.make(holder.itemView, "You already have a space reserved", Snackbar.LENGTH_SHORT).show()
                    }
                    .show()
            })
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return parkingSpaces.size
    }
}