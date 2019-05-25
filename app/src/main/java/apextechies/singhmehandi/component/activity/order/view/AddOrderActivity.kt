package apextechies.singhmehandi.component.activity.order.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.order.presenter.OrderPresenter
import kotlinx.android.synthetic.main.activity_order_add.*
import android.R.attr.country
import android.widget.ArrayAdapter
import apextechies.singhmehandi.component.activity.shop.model.*


class AddOrderActivity : AppCompatActivity(), OrderView, AdapterView.OnItemSelectedListener {

    var orderPresenter = OrderPresenter
    var areaList: ArrayList<AreaListData> ?= null
    var routeList: ArrayList<RouteListdata> ?= null
    var listArea: ArrayList<String> ?= null
    var routeRoute: ArrayList<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_add)
        orderPresenter.OrderPresenter(this,this)
        orderPresenter.onCreated()
    }

    override fun initWidgit() {
        areaCode.setOnItemSelectedListener(this)
        orderPresenter.getRouteList("", "")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(getApplicationContext(), listArea?.get(position), Toast.LENGTH_LONG).show();
    }

    override fun displayError(error: String) {
    }

    override fun hideProgress() {
    }

    override fun onRouteResponse(movieResponse: RouteListResponse) {
        routeList = movieResponse.data
        orderPresenter.onRouteResponceReceived(routeList)
    }
    override fun addRouteListInSpinner(areaList: ArrayList<String>) {
        routeRoute = areaList
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, routeRoute)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaCode.setAdapter(aa)

    }

    override fun onAreaResponse(movieResponse: AreaListResponse) {
        areaList = movieResponse!!.data
        orderPresenter.onAreaResponceReceived(areaList)
    }

    override fun addAreaListInSpinner(areaList: ArrayList<String>) {
        listArea = areaList
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listArea)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaCode.setAdapter(aa)
    }

    override fun noDataAvailable() {
    }

    override fun onDistributerResponse(movieResponse: DistributorListResponse) {
    }

    override fun onItemResponse(movieResponse: ItemListResponse) {
    }

}