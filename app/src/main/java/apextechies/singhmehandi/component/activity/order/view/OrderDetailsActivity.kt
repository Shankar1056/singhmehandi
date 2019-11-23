package apextechies.singhmehandi.component.activity.order.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import apextechies.singhmehandi.R
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        initWidgit()
        getIntentData()
    }

    fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        edit.setOnClickListener {
            startActivity(
                Intent(this@OrderDetailsActivity, AddOrderActivity::class.java)
                    .putExtra("salesman", intent.getStringExtra("salesman"))
                    .putExtra("shop", intent.getStringExtra("shop"))
                    .putExtra("trnum", intent.getStringExtra("trnum"))
                    .putExtra(
                        "title",
                        resources.getString(apextechies.singhmehandi.R.string.title_update_Order)
                    )
            )
        }
    }

    private fun getIntentData() {
        distributorName.text = ":- " + intent.getStringExtra("distributor")
        shopName.text = ":- " + intent.getStringExtra("shop")
        salesmanName.text = ":- " + intent.getStringExtra("salesman")
        superstockName.text = ":- " + intent.getStringExtra("superstockist")
        trnumName.text = ":- " + intent.getStringExtra("trnum")
        date.text = ":- " + intent.getStringExtra("route_name")
    }


}