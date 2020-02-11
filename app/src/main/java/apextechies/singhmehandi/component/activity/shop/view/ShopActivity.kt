package apextechies.singhmehandi.component.activity.shop.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.shop.model.ShopListData
import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse
import apextechies.singhmehandi.component.activity.shop.preserter.ShopPresenter
import apextechies.singhmehandi.component.activity.shop.view.adapter.ShopListAdapter
import apextechies.singhmehandi.util.Utils
import kotlinx.android.synthetic.main.activity_shop.*


class ShopActivity : AppCompatActivity(), ShopView,
    DateRangePickerFragment.OnDateRangeSelectedListener {
    var shopPresenter = ShopPresenter
    private var shopList = ArrayList<ShopListData>()
    private lateinit var shopAdpter: ShopListAdapter

    companion object {
        var fromDate: String? = null
        var toDate: String? = null
    }


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
        titleTV.text = resources.getString(R.string.title_shop_list)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        callenderLL.setOnClickListener {
            var dateRangePickerFragment =
                DateRangePickerFragment.newInstance(this@ShopActivity, false)
            dateRangePickerFragment.show(supportFragmentManager, "datePicker")
        }

        fab.setOnClickListener {
            startActivity(
                Intent(this@ShopActivity, AddShopActivity::class.java)
                    .putExtra("from", "shoplist")
            )
        }

        shopRV.layoutManager = LinearLayoutManager(this)

        shopAdpter = ShopListAdapter(shopList, object : ShopListAdapter.OnShopItemClickListener {
            override fun onClick(pos: Int) {
                startActivity(
                    Intent(this@ShopActivity, ShopDetailsActivity::class.java).putExtra(
                        "list",
                        shopList[pos]
                    )/*.putExtra("areaCode", shopList[pos].areacode).putExtra(
                        "routeName",
                        shopList[pos].routename
                    ).putExtra("routeCode", shopList[pos].routecode).putExtra(
                        "distributor",
                        shopList[pos].distributor
                    ).putExtra("panno", shopList[pos].panno).putExtra(
                        "phone",
                        shopList[pos].phone
                    ).putExtra(
                        "retailercode",
                        shopList[pos].retailercode
                    ).putExtra(
                        "shoptype",
                        shopList[pos].shoptype
                    ).putExtra("place", shopList[pos].place).putExtra(
                        "retailername",
                        shopList[pos].retailername
                    )*/
                )
            }

        })

        shopRV.adapter = shopAdpter

    }

    override fun onResume() {
        super.onResume()
        if (selectedDateRange.text.toString().trim() == resources.getString(R.string.title_select_date)) {
            fromDate = Utils.getCurrentDateWithDash()
            toDate = Utils.getCurrentDateWithDash()
            selectedDateRange.text =
                Utils.getCurrentDateWithhifun() + " to " + Utils.getCurrentDateWithhifun()
            shopPresenter.getShopList(
                Utils.getCurrentDateWithDash(),
                Utils.getCurrentDateWithDash()
            )
        } else {
            shopPresenter.ShopPresenter(this, this)
            shopPresenter.getShopList(
                fromDate.toString(),
                toDate.toString()
            )
        }
    }


    override fun showProgress() {
        progressAVL.show()
    }


    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun displayError(s: String) {
    }

    override fun clearList() {
        shopList.clear()
        shopAdpter.notifyDataSetChanged()
    }

    override fun onshopDeleted(message: String?) {

    }

    override fun onReceivedResponse(shopResponse: ShopListResponse) {
        Log.i("Response", shopResponse.toString())
        shopList.clear()
        shopList.addAll(shopResponse.data!!)
        if (shopResponse != null && shopList.size!! > 0) {
            shopCountShop.text = "" + shopList.size + " - Shops found"
        }
        shopAdpter.notifyDataSetChanged()

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
        fromDate = "${startMonth + 1}/$startDay/$startYear"
        toDate = "${endMonth + 1}/$endDay/$endYear"
        selectedDateRange.text =
            "$startDay-${startMonth + 1}-$startYear to  $endDay-${endMonth + 1}-$endYear"
        shopPresenter.getShopList(
            "${startMonth + 1}/$startDay/$startYear",
            "${endMonth + 1}/$endDay/$endYear"
        )
    }
}
