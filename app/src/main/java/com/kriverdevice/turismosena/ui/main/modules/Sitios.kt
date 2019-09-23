package com.kriverdevice.turismosena.ui.main.modules


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kriverdevice.turismosena.R


/**
 * A simple [Fragment] subclass.
 *
 */
class Sitios : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitios, container, false)
    }


}
