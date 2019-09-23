package com.kriverdevice.turismosena.ui.main.modules.shared

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kriverdevice.turismosena.R

class Adapter(private var turismoObjects: ArrayList<TurismoObject>, var onItemSelected: RecyclerItemSelected? ) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater
                    .from(parent.context)
                    .inflate( R.layout.layout_share_item_recycler_view, parent,false);

        return   ViewHolder(view, onItemSelected)
    }

    override fun getItemCount(): Int {
        return turismoObjects.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(turismoObjects.get(position))
    }

    class ViewHolder(itemView: View, onItemSelected: RecyclerItemSelected?) : RecyclerView.ViewHolder (itemView){

        var name: TextView
        var address: TextView
        lateinit var turismoObject: TurismoObject

        init {
            this.name = itemView.findViewById(R.id.name_holder)
            this.address = itemView.findViewById(R.id.address_holder)
            itemView.setOnClickListener {
                onItemSelected?.onItemSelected(this.turismoObject)
            }
        }

        fun setData( turismoObject: TurismoObject ){
            this.name.setText(turismoObject.name)
            this.address.setText(turismoObject.address)
            this.turismoObject = turismoObject
        }

        /*
        constructor(itemView: View) : super(itemView) {
            this.name = itemView.findViewById<TextView>(R.id.name_holder)
            this.address = itemView.findViewById<TextView>(R.id.address_holder)

            itemView.setOnClickListener {
                Log.d("***->", "Sera seleccionado un item... " )
                Log.d("***->", "Item Seleccionado: " + turismoObject.name )
            }
        }
        */
    }
}