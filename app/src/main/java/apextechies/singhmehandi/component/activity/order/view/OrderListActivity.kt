package apextechies.singhmehandi.component.activity.order.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderListResponse
import apextechies.singhmehandi.component.activity.order.presenter.OrderPresenter
import apextechies.singhmehandi.component.activity.order.view.adapter.OrderListAdapter
import kotlinx.android.synthetic.main.activity_shop.*


class OrderListActivity : AppCompatActivity(), OrderListView {

    var presenter = OrderPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        presenter.OrderPresenter(this, this)
        presenter.initWidgit()
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        shopRV.layoutManager = LinearLayoutManager(this)
        addShop.text = resources.getString(R.string.title_add_order)
        addShop.visibility = View.GONE
        toolbar.setNavigationOnClickListener {
            finish()
        }

        addShop.setOnClickListener {
            startActivity(Intent(this@OrderListActivity, AddOrderActivity::class.java))
        }
    }


    override fun getOrderList() {
        presenter.getOrderList()
    }

    override fun showProgress() {
        progressAVL.show()
    }

    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun displayError(s: String) {
    }

    override fun onOrderResponseReceived(orderList: OrderListResponse) {
        shopRV.adapter = orderList.data?.let {
            OrderListAdapter(this@OrderListActivity, it, object : OrderListAdapter.OrderListClick {
                override fun onItemClick(pos: Int) {

                    startActivity(
                        Intent(this@OrderListActivity, AddOrderActivity::class.java).putExtra(
                            "salesman",
                            orderList.data!![pos].salesman
                        ).putExtra("shop", orderList.data!![pos].shop).putExtra("trnum", orderList.data!![pos].trnum)
                    )
                }

            })
        }
    }

    override fun invalidUser() {
    }


}