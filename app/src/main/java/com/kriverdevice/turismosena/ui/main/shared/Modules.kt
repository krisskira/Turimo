package com.kriverdevice.turismosena.ui.main.shared

interface Modules {
    fun refreshList()
    fun setData(turismoObjects: ArrayList<TurismoObject>): Modules
}

interface TurismoItemListener {
    fun onItemSelected(turismoObject: TurismoObject)
}