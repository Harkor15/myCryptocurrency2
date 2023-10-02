package harkor.mycryptocurrency.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import harkor.mycryptocurrency.adapters.RecyclerAdapter
import harkor.mycryptocurrency.databinding.ActivityMainBinding
import harkor.mycryptocurrency.viewmodel.MainActivityViewModel
import java.util.*

class MainActivity : AppCompatActivity(), NoticeAddDialogListener, NotifyDataDelete, NotifyCurrencyChange /*implements ListRefresh,OverallPrice,InterfaceOfMainActivity*/ {

    private val adapter = RecyclerAdapter(ArrayList(), this)
    private var mainActivityViewModel: MainActivityViewModel? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel!!.getAmount()
                .observe(this, Observer { s -> binding.textMoneyAmount.text = s })
        mainActivityViewModel!!.isDataDownloaded().observe(this, Observer { dataFlag ->
            if (!dataFlag) {
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.progressBar.visibility = View.VISIBLE
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.progressBar.visibility = View.INVISIBLE
                mainActivityViewModel!!.getAllPrice()
            }
        })

        binding.imageAdd.setOnClickListener {
            val dialogAdd = DialogAdd(this)
            dialogAdd.show(supportFragmentManager, "adddialog")
        }

        binding.imageSettings.setOnClickListener {
            val dialogSettings = DialogSettings(this)
            dialogSettings.show(supportFragmentManager, "settingsdialog")
        }

        val recyclerView = binding.list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        mainActivityViewModel!!.getCryptoData().observe(this, Observer { data ->
            adapter.dataSet = data
            adapter.notifyDataSetChanged()
            mainActivityViewModel!!.calculateAmount()
        })

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