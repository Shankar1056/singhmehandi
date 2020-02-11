package apextechies.singhmehandi.component.activity.shop.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.shop.model.ShopListData
import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse
import apextechies.singhmehandi.component.activity.shop.preserter.ShopPresenter
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import kotlinx.android.synthetic.main.activity_shop_details.*


class ShopDetailsActivity : AppCompatActivity(), ShopView {
    private lateinit var listItem: ShopListData
    var shopPresenter = ShopPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)
        shopPresenter.ShopPresenter(this, this)
        initWidgit()
        getIntentData()
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        delete.setOnClickListener {
            shopPresenter.deleteShop(listItem.id)
        }


        edit.setOnClickListener {
            startActivity(
                Intent(this, AddShopActivity::class.java)
                    .putExtra("from", "details")
                    .putExtra("list", listItem)
            )
        }

        progressbar.visibility = View.GONE
    }



    private fun getIntentData() {
        listItem = intent.getParcelableExtra("list")
        areaName.text = ":- " + listItem.areaname
        areaCode.text = ":- " + listItem.areacode
        routeName.text = ":- " + listItem.routename
        routeCode.text = ":- " + listItem.routecode
        retailerName.text = ":- " + listItem.retailername
        retailerCode.text = ":- " + listItem.retailercode
        place.text = ":- " + listItem.place
        phone.text = ":- " + listItem.phone
        distributor.text = ":- " + listItem.distributor
        shoptype.text = ":- " + listItem.shoptype
        panno.text = ":- " + listItem.panno
        state.text = ":- "+ClsGeneral.getPreferences(this@ShopDetailsActivity, Constants.STATE)
        region.text = ":- "+ClsGeneral.getPreferences(this@ShopDetailsActivity, Constants.REGION)
    }


    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun displayError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onReceivedResponse(movieResponse: ShopListResponse) {
    }

    override fun invalidUser() {
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun clearList() {
    }

    override fun onshopDeleted(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}