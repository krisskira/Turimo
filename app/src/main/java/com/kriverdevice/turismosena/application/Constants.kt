package com.kriverdevice.turismosena.application

object Constants {
    val sitesKey = "sites"
    val hotelsKey = "hotels"
    val operatorsKey = "operators"

    internal var SERVER = "https://turismo-sena.herokuapp.com"

    /* Sities, Hotels and Operators */
    val ALL_DATA = Constants.SERVER

    /*  Sites   */
    val SITES = Constants.SERVER + "/" + sitesKey

    /*  Hoteles */
    val HOTELS = Constants.SERVER + "/" + hotelsKey

    /*  Operadores */
    val OPERATORS = Constants.SERVER + "/" + operatorsKey

}