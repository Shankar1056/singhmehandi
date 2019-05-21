package apextechies.singhmehandi.component.activity.order.view

import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.shop.model.AreaListResponse
import apextechies.singhmehandi.component.activity.shop.model.DistributorListResponse
import apextechies.singhmehandi.component.activity.shop.model.RouteListResponse
import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse

interface OrderView {

    fun initWidgit()
    fun displayError(error: String)
    fun hideProgress()
    fun onRouteResponse(movieResponse: RouteListResponse)
    fun onAreaResponse(movieResponse: AreaListResponse)
    fun noDataAvailable()
    fun onDistributerResponse(movieResponse: DistributorListResponse)
    fun onItemResponse(movieResponse: ItemListResponse)
}