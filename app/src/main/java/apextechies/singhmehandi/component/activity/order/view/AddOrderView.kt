package apextechies.singhmehandi.component.activity.order.view

import apextechies.singhmehandi.component.activity.order.model.ItemListData
import apextechies.singhmehandi.component.activity.shop.model.RouteListdata

interface AddOrderView {

    fun initWidgit()
    fun showProgress()
    fun hideProgress()
    fun showEmptyStringMessage(string: String)
    fun noDataAvailable()
    fun onItemResponse(movieResponse: ArrayList<String>, listResponse: ArrayList<ItemListData>)
    fun displayError(errorMessage: String)
    fun onaddOrderResponse(message: String)
    fun onCompleted()
    fun onAuthorisedDealerOrderResponse(message: ArrayList<RouteListdata>?)
    fun addRouteNameListInSpinner(routeNameList: ArrayList<String>)
    fun selectSpinnerPosition(i: Int)
}