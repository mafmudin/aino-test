package test.ainosi.aplikasiberita.utility

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.net.NetworkCapabilities
import android.net.ConnectivityManager
import java.text.SimpleDateFormat


class Utility {
    companion object{
        @SuppressLint("MissingPermission")
        fun isInternetAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
                return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = cm.allNetworks
                for (n in networks) {
                    val nInfo = cm.getNetworkInfo(n)
                    if (nInfo != null && nInfo.isConnected) return true
                }
            } else {
                val networks = cm.allNetworkInfo
                for (nInfo in networks) {
                    if (nInfo != null && nInfo.isConnected) return true
                }
            }

            return false
        }

        fun reformatDate(dateString :String, oldFormat: String, newFormat: String) : String{
            val oldSdf = SimpleDateFormat(oldFormat)
            val newSdf = SimpleDateFormat(newFormat)

            val date = oldSdf.parse(dateString)
            return newSdf.format(date)
        }
    }
}