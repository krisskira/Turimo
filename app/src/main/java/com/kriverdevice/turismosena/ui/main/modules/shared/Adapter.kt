package com.kriverdevice.turismosena.ui.main.modules.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        RecyclerView.ViewHolder(itemView) {

        var item: CardView
        var description: TextView
        var address: TextView
        var phoneButtom: ImageButton
        var localPhoneButtom: ImageButton
        var emailButtom: ImageButton
        var webButtom: ImageButton

        lateinit var turismoObject: TurismoObject

        init {

            this.item = itemView.findViewById(R.id.shared_card_holder)
            this.description = itemView.findViewById(R.id.name_holder)
            this.address = itemView.findViewById(R.id.address_holder)
            this.phoneButtom = itemView.findViewById(R.id.mobile_phone_buttom)
            this.localPhoneButtom = itemView.findViewById(R.id.local_phone_buttom)
            this.emailButtom = itemView.findViewById(R.id.email_buttom)
            this.webButtom = itemView.findViewById(R.id.web_buttom)

            this.phoneButtom.setOnClickListener{
                onItemSelectedListener?.call(this.turismoObject.mobile_hone)
            }
            this.localPhoneButtom.setOnClickListener{
                onItemSelectedListener?.call(this.turismoObject.local_phone)
            }
            this.emailButtom.setOnClickListener{
                onItemSelectedListener?.sendEmail(this.turismoObject.email)
            }
            this.webButtom.setOnClickListener{
                onItemSelectedListener?.goToWebSide(this.turismoObject.web)
            }

            this.item.setOnClickListener {
                onItemSelectedListener?.goToForm(this.turismoObject)
            }
        }

        fun setData( turismoObject: TurismoObject ){
            this.description.setText(turismoObject.description)
            this.address.setText(turismoObject.address)
            this.turismoObject = turismoObject
        }

    }
}