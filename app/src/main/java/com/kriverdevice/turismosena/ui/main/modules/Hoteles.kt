package com.kriverdevice.turismosena.ui.main.modules


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kriverdevice.turismosena.R
import com.kriverdevice.turismosena.application.*
import com.kriverdevice.turismosena.ui.main.modules.shared.Adapter
import com.kriverdevice.turismosena.ui.main.modules.shared.RecyclerItemSelected
import com.kriverdevice.turismosena.ui.main.modules.shared.TurismoObject
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class Hoteles : Fragment(), RecyclerItemSelected, HttpRequest.OnHttpRequestComplete {

    lateinit var recycler: RecyclerView
    lateinit var adapter: Adapter
    var turismoObjects = ArrayList<TurismoObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hoteles, container, false)
        recycler = view.findViewById<RecyclerView>(R.id.shared_recycler_view);
        val layoutManager = LinearLayoutManager(this.context)
        recycler.layoutManager = layoutManager
        adapter = Adapter(turismoObjects, this)
        recycler.adapter = adapter
        setDataInRecycler()
        return view
    }

    override fun onItemSelected(turismoObject: TurismoObject) {
        Toast.makeText(this.context, "***-> Item Selected: " + turismoObject.name, Toast.LENGTH_LONG).show();
    }


    fun setDataInRecycler() {
        HttpRequest(Constants.FETCH_HOTELS, this, null)
    }

    override fun onComplete(response: Result) {

        when (response) {
            is Success -> {
                val i = response.data.getJSONObject(0)
                var turismoObject = TurismoObject(i.getString("id"),"Nombre:",i.getString("direccion"), "Calle 1", "a", "363", "sds")
                turismoObjects.add(turismoObject)
                adapter.notifyDataSetChanged()
            }
            is Fail -> {

            }

        }


    }


}

