package apextechies.singhmehandi.component.activity.order.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderListData
import apextechies.singhmehandi.component.activity.order.model.OrderListResponse
import apextechies.singhmehandi.component.activity.order.presenter.OrderPresenter
import apextechies.singhmehandi.component.activity.order.view.adapter.OrderListAdapter
import apextechies.singhmehandi.component.activity.shop.view.DateRangePickerFragment
import apextechies.singhmehandi.util.Utils
import kotlinx.android.synthetic.main.activity_order_add.*
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.activity_shop.progressAVL
import kotlinx.android.synthetic.main.activity_shop.titleTV
import kotlinx.android.synthetic.main.activity_shop.toolbar


class OrderListActivity : AppCompatActivity(), OrderListView,
    DateRangePickerFragment.OnDateRangeSelectedListener {

    var presenter = OrderPresenter()
    var orderAdapter: OrderListAdapter? = null
    private var orderList = ArrayList<OrderListData>()

    companion object {
        var fromDate: String? = null
        var toDate: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        presenter.OrderPresenter(this, this)
        presenter.initWidgit()

    }

    override fun onResume() {
        super.onResume()


        if (selectedDateRange.text.toString().trim() == resources.getString(R.string.title_select_date)) {
            fromDate = Utils.getCurrentDateWithDash()
            toDate = Utils.getCurrentDateWithDash()
            selectedDateRange.text =
                Utils.getCurrentDateWithhifun() + " - " + Utils.getCurrentDateWithhifun()
            presenter.getOrderList(Utils.getCurrentDateWithDash(), Utils.getCurrentDateWithDash())

        } else {
            presenter.getOrderList(fromDate.toString(), toDate.toString())
        }


    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        shopRV.layoutManager = LinearLayoutManager(this)
        titleTV.setText(resources.getString(apextechies.singhmehandi.R.string.title_order_list))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        callenderLL.setOnClickListener {
            var dateRangePickerFragment =
                DateRangePickerFragment.newInstance(this@OrderListActivity, false)
            dateRangePickerFragment.show(getSupportFragmentManager(), "datePicker");
        }

        fab.setOnClickListener {
            startActivity(
                Intent(this@OrderListActivity, AddOrderActivity::class.java)
                    .putExtra(
                        "title",
                        resources.getString(apextechies.singhmehandi.R.string.title_add_order)
                    )
            )
        }


        orderAdapter = orderList?.let {
            orderList.addAll(it)
            OrderListAdapter(this@OrderListActivity, it, object : OrderListAdapter.OrderListClick {
                override fun onItemClick(pos: Int) {

                    startActivity(
                        Intent(this@OrderListActivity, OrderDetailsActivity::class.java)
                            .putExtra("list", orderList!![pos])
                    )
                }

            })

        }
        shopRV.adapter = orderAdapter

    }


    override fun showProgress() {
        progressAVL.show()
    }

    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun displayError(s: String) {
    }

    override fun onOrderResponseReceived(ordrList: OrderListResponse) {
        if (ordrList != null && ordrList.data?.size!! > 0) {
            shopCountShop.text = "" + ordrList.data!!.size + " - Orders found"
        }

        ordrList.data?.let { orderList.addAll(it) }
        orderAdapter!!.notifyDataSetChanged()


    }

    override fun clearList() {
        orderList.clear()
        orderAdapter!!.notifyDataSetChanged()
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
        toDate =  "${endMonth + 1}/$endDay/$endYear"
        selectedDateRange.text =
            "$startDay-${startMonth + 1}-$startYear - $endDay-${endMonth + 1}-$endYear"
        presenter.getOrderList(
            "${startMonth + 1}/$startDay/$startYear",
            "${endMonth + 1}/$endDay/$endYear"
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.dashboard, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchManager =
            this@OrderListActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem!!.getActionView() as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(this@OrderListActivity.getComponentName()))
        }

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                //orderAdapter!!.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                orderAdapter?.filter(query)
                return false
            }
        })
        return true
        // return super.onCreateOptionsMenu(menu)
    }


}