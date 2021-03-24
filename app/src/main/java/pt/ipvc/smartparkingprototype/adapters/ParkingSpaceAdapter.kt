package pt.ipvc.smartparkingprototype.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_parking_space.view.*
import kotlinx.android.synthetic.main.item_todo.view.*
import pt.ipvc.smartparkingprototype.R
import pt.ipvc.smartparkingprototype.models.ParkingSpaceItem
import pt.ipvc.smartparkingprototype.models.ParkingSpaceSection

class ParkingSpaceAdapter(
    private val parkingSpaces: ArrayList<ParkingSpaceSection>,
    private val listener: OnItemClickListener)
    : RecyclerView.Adapter<ParkingSpaceAdapter.ParkingSpaceViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return parkingSpaces.size
    }
}