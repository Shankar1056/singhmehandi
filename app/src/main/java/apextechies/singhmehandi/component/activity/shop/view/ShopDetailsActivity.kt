package apextechies.singhmehandi.component.activity.shop.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import apextechies.singhmehandi.R
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import kotlinx.android.synthetic.main.activity_shop_details.*


class ShopDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)

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
    }

    private fun getIntentData() {
        areaName.text = ":- "+intent.getStringExtra("areaName")
        areaCode.text = ":- "+intent.getStringExtra("areaCode")
        routeName.text = ":- "+intent.getStringExtra("routeName")
        routeCode.text = ":- "+intent.getStringExtra("routeCode")
        retailerName.text = ":- "+intent.getStringExtra("retailername")
        retailerCode.text = ":- "+intent.getStringExtra("retailercode")
        place.text = ":- "+intent.getStringExtra("place")
        phone.text = ":- "+intent.getStringExtra("phone")
        distributor.text = ":- "+intent.getStringExtra("distributor")
        shoptype.text = ":- "+intent.getStringExtra("shoptype")
        panno.text = ":- "+intent.getStringExtra("panno")
        state.text = ":- "+ClsGeneral.getPreferences(this@ShopDetailsActivity, Constants.STATE)
        region.text = ":- "+ClsGeneral.getPreferences(this@ShopDetailsActivity, Constants.REGION)
    }
}