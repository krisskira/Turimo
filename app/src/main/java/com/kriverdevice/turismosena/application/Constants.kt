package com.kriverdevice.turismosena.application

object Constants {

    internal var SERVER = "https://www.sena.tricky.kriverdevice.com/turismo-valle"

    /*  TurismoObjectList   */
    var FETCH_SITES = Constants.SERVER + "/sitios/listSitios.php"
    var FETCH_EDIT_SITES = Constants.SERVER + "/sitios/editSites.php"
    var FETCH_ADD_SITES = Constants.SERVER + "/sitios/addSites.php"
    var FETCH_DELETE_SITES = Constants.SERVER + "/sitios/deleteSite.php"

    /*  Hoteles */
    var FETCH_HOTELS = Constants.SERVER + "/hoteles/listHotels.php"
    var FETCH_EDIT_HOTELS = Constants.SERVER + "/hoteles/editHotels.php"
    var FETCH_ADD_HOTELS = Constants.SERVER + "/hoteles/addHotels.php"
    var FETCH_DELETE_HOTELS = Constants.SERVER + "/hoteles/deleteHotels.php"

    /*  Operadores */
    var FETCH_OPERATORS = Constants.SERVER + "/operadores/listOperators.php"
    var FETCH_EDIT_OPERATORS = Constants.SERVER + "/operadores/editOperators.php"
    var FETCH_ADD_OPERATORS = Constants.SERVER + "/operadores/addOperators.php"
    var FETCH_DELETE_OPERATORS = Constants.SERVER + "/operadores/deleteOperators.php"
}