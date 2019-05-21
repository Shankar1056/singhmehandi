package apextechies.singhmehandi.component.activity.order.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.order.presenter.OrderPresenter
import apextechies.singhmehandi.component.activity.shop.model.AreaListResponse
import apextechies.singhmehandi.component.activity.shop.model.DistributorListResponse
import apextechies.singhmehandi.component.activity.shop.model.RouteListResponse

class AddOrderActivity : AppCompatActivity(), OrderView {
    override fun displayError(error: String) {
    }

    override fun hideProgress() {
    }

    override fun onRouteResponse(movieResponse: RouteListResponse) {
    }

    override fun onAreaResponse(movieResponse: AreaListResponse) {
    }

    override fun noDataAvailable() {
    }

    override fun onDistributerResponse(movieResponse: DistributorListResponse) {
    }

    override fun onItemResponse(movieResponse: ItemListResponse) {
    }

    var orderPresenter = OrderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_add)


    }

    override fun initWidgit() {
        orderPresenter.OrderPresenter(this)
    }

}