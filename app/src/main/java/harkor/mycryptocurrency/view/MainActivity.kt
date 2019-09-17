package harkor.mycryptocurrency.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import harkor.mycryptocurrency.R
import harkor.mycryptocurrency.adapters.RecyclerAdapter
import harkor.mycryptocurrency.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NoticeAddDialogListener, NotifyDataDelete, NotifyCurrencyChange /*implements ListRefresh,OverallPrice,InterfaceOfMainActivity*/ {

    private val adapter = RecyclerAdapter(ArrayList(), this)
    private var mainActivityViewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel!!.getAmount()
                .observe(this, Observer { s -> text_money_amount!!.text = s })
        MobileAds.initialize(this, resources.getString(R.string.banner_ad_unit_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        mainActivityViewModel!!.isDataDownloaded().observe(this, Observer { dataFlag ->
            if (!dataFlag) {
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                progress_bar.visibility = View.VISIBLE
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                progress_bar.visibility = View.INVISIBLE
            }
        })

        image_add.setOnClickListener {
            val dialogAdd = DialogAdd(this)
            dialogAdd.show(supportFragmentManager, "adddialog")
        }

        image_settings.setOnClickListener {
            val dialogSettings = DialogSettings(this)
            dialogSettings.show(supportFragmentManager, "settingsdialog")
        }

        val recyclerView = list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        mainActivityViewModel!!.getCryptoData().observe(this, Observer { data ->
            adapter.dataSet = data
            adapter.notifyDataSetChanged()
            mainActivityViewModel!!.calculateAmount()
        })
        mainActivityViewModel!!.getAllPrice()
    }

    override fun addNewCrypto(name: String, amount: Double) {
        mainActivityViewModel!!.addNewCrypto(name, amount)
    }

    override fun deleted() {
        mainActivityViewModel!!.calculateAmount()
    }

    override fun change() {
        mainActivityViewModel!!.calculateAmount()
        adapter.notifyDataSetChanged()
        adapter.notifyDataSetChanged()
    }
}

interface NotifyDataDelete {
    fun deleted()
}

interface NotifyCurrencyChange {
    fun change()
}