package harkor.mycryptocurrency

import android.content.Context

object SharedPref {
    fun saveDefaultCurrency( currencyId:Int,  context: Context){
        val sharedPref = context.getSharedPreferences("harkor.myCryptocurrency", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("currencyCode", currencyId)
        editor.apply()
    }
    fun getDefaultCurrency(context:Context): Int {
        val sharedPref = context.getSharedPreferences("harkor.myCryptocurrency", Context.MODE_PRIVATE)
        return sharedPref.getInt("currencyCode",1)
    }
}