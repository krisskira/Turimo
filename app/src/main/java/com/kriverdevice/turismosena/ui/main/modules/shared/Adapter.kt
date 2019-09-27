package com.kriverdevice.turismosena.ui.main.modules.shared

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kriverdevice.turismosena.R


class Adapter(
    private var turismoObjects: ArrayList<TurismoObject>,
    var onItemSelectedListener: RecyclerItemSelectedListener?
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater
                    .from(parent.context)
                    .inflate( R.layout.layout_share_item_recycler_view, parent,false);

        return ViewHolder(view, onItemSelectedListener)
    }

    override fun getItemCount(): Int {
        return turismoObjects.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(turismoObjects.get(position))
    }

    class ViewHolder(itemView: View, onItemSelectedListener: RecyclerItemSelectedListener?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(view: View?) {
            try {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${turismoObject.mobile_hone}")
                startActivity(view!!.context, callIntent, null)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        var description: TextView
        var address: TextView
        var phoneButtom: ImageButton
        lateinit var turismoObject: TurismoObject

        init {
            this.description = itemView.findViewById(com.kriverdevice.turismosena.R.id.name_holder)
            this.address = itemView.findViewById(R.id.address_holder)
            this.phoneButtom = itemView.findViewById(R.id.mobile_phone_buttom)


            this.phoneButtom.setOnClickListener(this)

            itemView.setOnClickListener {
                onItemSelectedListener?.onItemSelected(this.turismoObject)
            }
        }

        fun setData( turismoObject: TurismoObject ){
            this.description.setText(turismoObject.description)
            this.address.setText(turismoObject.address)
            this.turismoObject = turismoObject
        }


    }
}