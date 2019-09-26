package com.kriverdevice.turismosena.ui.main.modules.shared

interface FormListener {
    fun onUpdateItem(turismoObject: TurismoObject)
    fun onDeleteItem(id: Int)
    fun onSave(turismoObject: TurismoObject)
}