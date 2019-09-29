package com.kriverdevice.turismosena

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kriverdevice.turismosena.R.*
import com.kriverdevice.turismosena.application.Constants
import com.kriverdevice.turismosena.ui.main.shared.TurismoObject


class FormActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    DialogInterface.OnClickListener {


    val FLAG_LOG = "***-> FormActivity"

    var id: String? = null
    lateinit var description: TextInputEditText
    lateinit var mobilePhone: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var localPhone: TextInputEditText
    lateinit var web: TextInputEditText
    lateinit var address: TextInputEditText
    lateinit var saveButton: FloatingActionButton
    lateinit var deleteButton: Button
    lateinit var toolbar: Toolbar
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var imageBackGround: ImageView

    // Usada para determinar si es un Insert o un Update
    lateinit var actionRequest: Constants.ACTIONS

    // Usada para configurar la imagen del header del Form
    // y para notificarle a activity main a que endpoint debe
    // enviar la peticion.
    private lateinit var moduleRequest: String

    // Guarda la instancia del contenido de los Inputs
    var turimoObject: TurismoObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_form)

        description = findViewById(R.id.input_name)
        mobilePhone = findViewById(R.id.input_mobile_phone)
        email = findViewById(R.id.input_email)
        localPhone = findViewById(R.id.input_local_phone)
        web = findViewById(R.id.input_web)
        address = findViewById(R.id.input_address)
        saveButton = findViewById(R.id.save_button)
        deleteButton = findViewById(R.id.delete_button)
        toolbar = findViewById(R.id.toolbar)
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout)
        imageBackGround = findViewById(R.id.module_image_background)

        description.onFocusChangeListener = this
        mobilePhone.onFocusChangeListener = this
        email.onFocusChangeListener = this
        localPhone.onFocusChangeListener = this
        web.onFocusChangeListener = this
        address.onFocusChangeListener = this

        toolbar.setNavigationOnClickListener { finish() }
        saveButton.setOnClickListener(this)
        deleteButton.setOnClickListener(this)

        val bundle = when (savedInstanceState) {
            null -> intent.extras
            else -> savedInstanceState
        }

        actionRequest = Constants.ACTIONS.valueOf(bundle!!.getString("ACTION"))
        moduleRequest = bundle.getString("MODULE")
        collapsingToolbarLayout.title = moduleRequest

        when (moduleRequest) {
            getString(string.tab_text_sitios) -> imageBackGround.setImageResource(mipmap.sitios)
            getString(string.tab_text_hoteles) -> imageBackGround.setImageResource(mipmap.hoteles)
            getString(string.tab_text_operadores) -> imageBackGround.setImageResource(mipmap.operadores)
        }
        imageBackGround.scaleType = ImageView.ScaleType.CENTER_CROP

        when (actionRequest) {

            // Asegura que el valor de los inputs este vacio.
            Constants.ACTIONS.ADD -> {
                id = null
                description.setText("")
                mobilePhone.setText("")
                localPhone.setText("")
                web.setText("")
                address.setText("")
                email.setText("")
                deleteButton.visibility = View.GONE
            }

            // Extrae el contenido del objeto parcelable para poblar los inputs
            Constants.ACTIONS.UPDATE -> {
                turimoObject = bundle.getParcelable<TurismoObject>("DATA")
                id = turimoObject?.id
                description.setText(turimoObject?.description)
                mobilePhone.setText(turimoObject?.mobile_hone)
                localPhone.setText(turimoObject?.local_phone)
                web.setText(turimoObject?.web)
                address.setText(turimoObject?.address)
                email.setText(turimoObject?.email)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        turimoObject = TurismoObject(
            id = id,
            description = description.text.toString(),
            address = address.text.toString(),
            email = email.text.toString(),
            mobile_hone = mobilePhone.text.toString(),
            local_phone = localPhone.text.toString(),
            web = web.text.toString()
        )
        outState.putParcelable("DATA", turimoObject)
        outState.putString("ACTION", actionRequest.name)
        outState.putString("MODULE", moduleRequest)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        turimoObject = savedInstanceState.getParcelable("DATA")
        actionRequest = Constants.ACTIONS.valueOf(savedInstanceState.getString("ACTION"))
        moduleRequest = savedInstanceState.getString("MODULE")
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.save_button -> {

                if (!validate()) return
                var data = TurismoObject(
                    id,
                    description.text.toString(),
                    address.text.toString(),
                    email.text.toString(),
                    mobilePhone.text.toString(),
                    localPhone.text.toString(),
                    web.text.toString()
                )
                Log.d(FLAG_LOG, "Objecto a almacenar: " + data.toString())
                Log.d(FLAG_LOG, "Accion a ejecutar: ${actionRequest.name}")

                val i = Intent(this, MainActivity::class.java).apply {
                    putExtra("ACTION", actionRequest.name)
                    putExtra("MODULE", moduleRequest)
                    putExtra("DATA", data)
                }
                setResult(Constants.FORM_SAVE, i)
                finish()
            }

            R.id.delete_button -> {
                AlertDialog.Builder(this)
                    .setTitle(string.Eliminar)
                    .setCancelable(true)
                    .setMessage(getString(R.string.delete_message).format(turimoObject?.description, moduleRequest))
                    .setNegativeButton(R.string.menu_delete, this)
                    .setPositiveButton(R.string.menu_cancelar, this)
                    .show()
            }
        }
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        when (p1) {
            DialogInterface.BUTTON_NEGATIVE -> {
                val i = Intent(this, MainActivity::class.java).apply {
                    putExtra("ACTION", Constants.ACTIONS.DELETE.name)
                    putExtra("MODULE", moduleRequest)
                    putExtra("DATA", turimoObject?.id)
                }
                setResult(Constants.FORM_DELETE, i)
                finish()
            }
            DialogInterface.BUTTON_POSITIVE -> {
                p0?.dismiss()
            }
        }
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1) deleteError(p0 as TextInputEditText)
    }

    private fun validate(): Boolean {
        var resultValidation = true

        if (description.text.toString().isEmpty()) {
            resultValidation = false
            setError(description)
        }
        if (address.text.toString().isEmpty()) {
            resultValidation = false
            setError(address)
        }
        if (email.text.toString().isEmpty()) {
            resultValidation = false
            setError(email)
        }
        if (mobilePhone.text.toString().isEmpty()) {
            resultValidation = false
            setError(mobilePhone)
        }
        if (localPhone.text.toString().isEmpty()) {
            resultValidation = false
            setError(localPhone)
        }
        if (web.text.toString().isEmpty()) {
            resultValidation = false
            setError(web)
        }
        return resultValidation
    }

    fun setError(field: TextInputEditText) {
        val textInputLayout = field.parent.parent as TextInputLayout
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = getString(string.msg_required)
    }// Fin setError

    fun deleteError(field: TextInputEditText) {
        val textInputLayout = field.parent.parent as TextInputLayout
        textInputLayout.isErrorEnabled = false
        textInputLayout.error = null
    }
}
