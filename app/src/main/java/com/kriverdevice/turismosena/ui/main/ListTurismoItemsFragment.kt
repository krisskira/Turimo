package com.kriverdevice.turismosena.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kriverdevice.turismosena.R
import com.kriverdevice.turismosena.ui.main.shared.*

class ListTurismoItemsFragment : Fragment(), Modules, RecyclerItemSelectedListener {

    val FLAG_LOG = "***-> FragmentList"
    val BUNDLE = "BUNDLE"

    var turismoItemListener: TurismoItemListener? = null
    var recycler: RecyclerView? = null
    var adapter: Adapter? = null
    var turismoObjects = ArrayList<TurismoObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_turismo_items, container, false)
        this.recycler = view.findViewById<RecyclerView>(R.id.shared_recycler_view)
        this.recycler?.layoutManager = LinearLayoutManager(this.context)

        if (savedInstanceState != null) {
            turismoObjects = savedInstanceState.getParcelableArrayList(BUNDLE)
        }

        this.adapter = Adapter(turismoObjects, this)
        this.recycler?.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        this.adapter?.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(BUNDLE, turismoObjects)
        super.onSaveInstanceState(outState)
    }

    override fun refreshList() {
        this.adapter?.notifyDataSetChanged()
    }

    override fun setData(turismoObjects: ArrayList<TurismoObject>): Modules {
        this.turismoObjects.clear()
        this.turismoObjects.addAll(turismoObjects)
        Log.d(FLAG_LOG, "Count total items to list: " + turismoObjects.count())
        return this
    }

    override fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")
        emailIntent.type = "message/rfc822"
        startActivity(Intent.createChooser(emailIntent, getString(R.string.msg_user_email)))
    }

    override fun goToWebSide(web: String) {
        var url = web
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://$url"

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun call(phone: String) {

        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (checkSelfPermission(
                        context!!,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
                    return
                }
            }
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        } catch (ex: Exception) {
            Snackbar.make(this.view!!, ex.message.toString(), Snackbar.LENGTH_LONG).show()
            ex.printStackTrace()
        }
    }

    override fun goToForm(turismoObject: TurismoObject) {
        turismoItemListener?.onItemSelected(turismoObject)
    }

}
