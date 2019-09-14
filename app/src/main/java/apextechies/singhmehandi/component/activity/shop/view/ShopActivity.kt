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
import apextechies.singhmehandi.util.Utils
import kotlinx.android.synthetic.main.activity_shop.*


class ShopActivity : AppCompatActivity(), ShopView, DateRangePickerFragment.OnDateRangeSelectedListener {
    var shopPresenter = ShopPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apextechies.singhmehandi.R.layout.activity_shop)
        shopPresenter.ShopPresenter(this, this)
        shopPresenter.initWidgit()
        shopPresenter.getShopList(Utils.getCurrentDateWithDash(), Utils.getCurrentDateWithDash())
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        shopRV.layoutManager = LinearLayoutManager(this)
        titleTV.setText(resources.getString(R.string.title_shop_list))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        callenderLL.setOnClickListener {
            var dateRangePickerFragment = DateRangePickerFragment.newInstance(this@ShopActivity, false)
            dateRangePickerFragment.show(getSupportFragmentManager(), "datePicker")
        }

        fab.setOnClickListener {
            startActivity(Intent(this@ShopActivity, AddShopActivity::class.java))
        }

        shopRV.layoutManager = LinearLayoutManager(this)

    }


    override fun showProgress() {
        progressAVL.show()
    }

    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun displayError(s: String) {
    }

    override fun onReceivedResponse(shopResponse: ShopListResponse) {
        Log.i("Response", shopResponse.toString())
        if (shopResponse != null && shopResponse.data?.size!! > 0) {
            shopCountShop.setText(""+ shopResponse.data!!.size+" - Shops found")
        }
        shopRV.adapter = shopResponse.data?.let {
            ShopListAdapter(this@ShopActivity, it, object : ShopListAdapter.OnShopItemClickListener {
                override fun onClick(pos: Int) {
                    startActivity(
                        Intent(this@ShopActivity, ShopDetailsActivity::class.java).putExtra(
                            "areaName",
                            shopResponse.data!![pos].areaname
                        ).putExtra("areaCode", shopResponse.data!![pos].areacode).putExtra(
                            "routeName",
                            shopResponse.data!![pos].routename
                        ).putExtra("routeCode", shopResponse.data!![pos].routecode).putExtra(
                            "distributor",
                            shopResponse.data!![pos].distributor
                        ).putExtra("panno", shopResponse.data!![pos].panno).putExtra(
                            "phone",
                            shopResponse.data!![pos].phone
                        ).putExtra("retailercode", shopResponse.data!![pos].retailercode).putExtra(
                            "shoptype",
                            shopResponse.data!![pos].shoptype
                        ).putExtra("place", shopResponse.data!![pos].place).putExtra(
                            "retailername",
                            shopResponse.data!![pos].retailername
                        )
                    )
                }

            })
        }
    }

    override fun invalidUser() {
    }

    override fun onDateRangeSelected(
        startDay: Int,
        startMonth: Int,
        startYear: Int,
        endDay: Int,
        endMonth: Int,
        endYear: Int
    ) {
        Log.d("range : ", "from: $startDay-$startMonth-$startYear to : $endDay-$endMonth-$endYear")
        selectedDateRange.setText("$startDay-${startMonth+1}-$startYear - $endDay-${endMonth+1}-$endYear")
        shopPresenter.getShopList("${startMonth+1}/$startDay/$startYear", "${startMonth+1}/$endDay/$endYear")
    }
}
