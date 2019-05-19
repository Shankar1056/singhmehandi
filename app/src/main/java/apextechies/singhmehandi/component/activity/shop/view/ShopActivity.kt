package apextechies.singhmehandi.component.activity.shop.view

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
        shopPresenter.ShopPresenter(this,this)
        shopPresenter.initWidgit()
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        shopRV.layoutManager = LinearLayoutManager(this)
    }

    override fun getShopList() {

        shopPresenter.getShopList()
    }

    override fun hideProgress() {

    }

    override fun displayError(s: String) {
    }

    override fun onReceivedResponse(movieResponse: ShopListResponse) {
        Log.i("Response", movieResponse.toString())
        shopRV.adapter = movieResponse.data?.let { ShopListAdapter(this@ShopActivity, it) }
    }

    override fun invalidUser() {
    }

}
