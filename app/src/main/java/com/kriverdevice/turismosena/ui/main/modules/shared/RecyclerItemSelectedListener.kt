package com.kriverdevice.turismosena.ui.main.modules.shared

interface RecyclerItemSelectedListener {
    fun sendEmail(email:String)
    fun goToWebSide(web:String)
    fun call(phone:String)
    fun goToForm(turismoObject: TurismoObject)
}