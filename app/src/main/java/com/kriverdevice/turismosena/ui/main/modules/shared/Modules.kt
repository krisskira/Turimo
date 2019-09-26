package com.kriverdevice.turismosena.ui.main.modules.shared

interface Modules {
    fun refreshList()
    fun setData(turismoObjects: ArrayList<TurismoObject>): Modules
}