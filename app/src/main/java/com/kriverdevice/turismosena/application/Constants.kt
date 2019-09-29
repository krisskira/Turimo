package com.kriverdevice.turismosena.application

object Constants {

    val FORM: Int = 1000
    val FORM_SAVE: Int = 1001
    val FORM_DELETE: Int = 1002

    enum class ACTIONS {
        ADD, UPDATE, DELETE
    }

    enum class MODULES {
        sites, hotels, operators
    }

    internal var SERVER = "https://turismo-sena.herokuapp.com"

    /* Sities, Hotels and Operators */
    val ALL_DATA = Constants.SERVER

    /*  Sites   */
    val SITES = Constants.SERVER + "/" + MODULES.sites.name

    /*  Hoteles */
    val HOTELS = Constants.SERVER + "/" + MODULES.hotels.name

    /*  Operadores */
    val OPERATORS = Constants.SERVER + "/" + MODULES.operators.name
}