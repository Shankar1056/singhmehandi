package apextechies.singhmehandi.component.activity.order.view

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
        toolbar.setNavigationOnClickListener {
            finish()
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

    override fun onOrderResponseReceived(movieResponse: OrderListResponse) {
        shopRV.adapter = movieResponse.data?.let { OrderListAdapter(this@OrderListActivity, it) }
    }

    override fun invalidUser() {
    }




}