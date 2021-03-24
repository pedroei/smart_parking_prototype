package pt.ipvc.smartparkingprototype.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_parking_space.view.*
import pt.ipvc.smartparkingprototype.R
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
                val pos = it?.tag as Int
                val data = parkingSpaces[position].slots?.get(pos)
                Toast.makeText(
                    context,
                    "CLICK !! ${data?.slot}",
                    LENGTH_SHORT
                ).show()
            })
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return parkingSpaces.size
    }
}