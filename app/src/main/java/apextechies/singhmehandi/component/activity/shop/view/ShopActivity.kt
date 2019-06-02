package apextechies.singhmehandi.component.activity.shop.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse
import apextechies.singhmehandi.component.activity.shop.preserter.ShopPresenter
import apextechies.singhmehandi.component.activity.shop.view.adapter.ShopListAdapter
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity(), ShopView {
    var shopPresenter = ShopPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        shopPresenter.ShopPresenter(this, this)
        shopPresenter.initWidgit()
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        shopRV.layoutManager = LinearLayoutManager(this)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        addShop.setOnClickListener {
            startActivity(Intent(this@ShopActivity, AddShopActivity::class.java))
        }
        shopRV.layoutManager = LinearLayoutManager(this)
    }

    override fun getShopList() {
        shopPresenter.getShopList()
    }

    override fun showProgress() {
        progressAVL.show()
    }

    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun displayError(s: String) {
    }

    override fun onReceivedResponse(movieResponse: ShopListResponse) {
        Log.i("Response", movieResponse.toString())
        shopRV.adapter = movieResponse.data?.let { ShopListAdapter(this@ShopActivity, it, object : ShopListAdapter.OnShopItemClickListener {
            override fun onClick(pos: Int) {
                startActivity(Intent(this@ShopActivity,  ShopDetailsActivity::class.java).
                putExtra("areaName", movieResponse.data!![pos].areaname).
                putExtra("areaCode", movieResponse.data!![pos].areacode).
                putExtra("routeName", movieResponse.data!![pos].routename).
                putExtra("routeCode", movieResponse.data!![pos].routecode).
                putExtra("distributor", movieResponse.data!![pos].distributor).
                putExtra("panno", movieResponse.data!![pos].panno).
                putExtra("phone", movieResponse.data!![pos].phone).
                putExtra("retailercode", movieResponse.data!![pos].retailercode).
                putExtra("shoptype", movieResponse.data!![pos].shoptype).
                putExtra("place", movieResponse.data!![pos].place).
                putExtra("retailername", movieResponse.data!![pos].retailername)
                )
            }

        }) }
    }

    override fun invalidUser() {
    }

}
