package com.kriverdevice.turismosena.application

import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

sealed class Result {}
data class Success(val data: String) : Result()
data class Fail(val error: Error) : Result()

class HttpRequest(
    private val target: String,
    private val onRequestCompleteListener: OnHttpRequestComplete,
    private val dataTosend: String?
) :
    AsyncTask<String, String, Result>() {

    private var bufferResponse: StringBuilder = StringBuilder()

    init {
        this.execute(target)
    }

    override fun doInBackground(vararg urlServer: String?): Result {

        try {

            Log.d("***->", "Url requested: " + urlServer.first())

            // Populate the URL object with the location of the PHP script or web page.
            val url = URL(urlServer.first())

            // This is the point where the connection is opened.
            val connection = url.openConnection() as HttpURLConnection

            // "(true)" here allows the POST action to happen.
            connection.doOutput = true
            connection.setRequestProperty("Accept-Charset", "UTF-8")
            connection.setRequestProperty("Content-Type", "Application/json")

            connection.readTimeout = 10000
            connection.connectTimeout = 10000

            // set the request method.
            connection.requestMethod = if (dataTosend != null) "POST" else "GET"
            connection.connect()

            if (dataTosend != null) {
                val wr = DataOutputStream(connection.outputStream)
                wr.writeBytes(dataTosend)
                wr.flush()
                wr.close()
            }

            val `in` = BufferedInputStream(connection.inputStream)
            val reader = BufferedReader(InputStreamReader(`in`))

            // Recover all data from body response http request
            while (buildStringResponse(reader.readLine()));

            // Parce response to json array object
            //val data = JSONArray(bufferResponse.toString())
            connection.disconnect()
            return Success(bufferResponse.toString())

        } catch (e: IOException) {
            e.printStackTrace()
            return Fail(Error(e.message, e.cause))
        }
    }

    private fun buildStringResponse(s: String?): Boolean {
        if (s == null) return false
        this.bufferResponse.append(s)
        return true
    }

    override fun onPostExecute(result: Result) {
        super.onPostExecute(result)
        onRequestCompleteListener.onComplete(result)
    }

    interface OnHttpRequestComplete {
        fun onComplete(response: Result)
    }


}