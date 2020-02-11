package apextechies.singhmehandi.component.activity.order.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderListData
import apextechies.singhmehandi.component.activity.order.model.OrderListResponse
import apextechies.singhmehandi.component.activity.order.presenter.OrderPresenter
import apextechies.singhmehandi.component.activity.order.view.adapter.OrderDetaildItemAdapter
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetailsActivity : AppCompatActivity(), OrderListView {

    var orderListData = OrderListData()
    var presenter = OrderPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        presenter.OrderPresenter(this, this)
        presenter.initWidgit()
        getIntentData()
        setAdapter()
    }

    private fun setAdapter() {
        if (orderListData.item != null) {
            if (orderListData.item!!.size > 0) {
                rv_item.adapter = OrderDetaildItemAdapter(orderListData)
            } else {
                rvCV.visibility = View.GONE
            }
        }
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        orderListData = intent.getParcelableExtra<OrderListData>("list")

        edit.setOnClickListener {
            startActivity(
                Intent(this@OrderDetailsActivity, AddOrderActivity::class.java)
                    .putExtra("salesman",  orderListData.salesman)
                    .putExtra("shop", orderListData.shop)
                    .putExtra("trnum", orderListData.trnum)
                    .putExtra("remarks", orderListData.remarks)
                    .putExtra(
                        "title",
                        resources.getString(R.string.title_update_Order)
                    )
            )
        }

        delete.setOnClickListener {
            presenter.deleteOrder(orderListData.trnum)
        }
    }

    private fun getIntentData() {
        distributorName.text = ":- " + orderListData.distributor
        shopName.text = ":- " + orderListData.shop
        salesmanName.text = ":- " + orderListData.salesman
        superstockName.text = ":- " + orderListData.superstockist
        trnumName.text = ":- " + orderListData.trnum
        date.text = ":- " + orderListData.route
        remarks.text = ":- " + orderListData.remarks
    }


    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun displayError(s: String) {
    }

    override fun onOrderResponseReceived(movieResponse: OrderListResponse) {
    }

    override fun invalidUser() {
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun clearList() {
    }

    override fun onOrderdeleted(movieResponse: String) {
        Toast.makeText(this, movieResponse, Toast.LENGTH_SHORT).show()
        finish()
    }


}