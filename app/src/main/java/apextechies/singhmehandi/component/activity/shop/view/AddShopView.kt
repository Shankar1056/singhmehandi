package apextechies.singhmehandi.component.activity.shop.view

import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.shop.model.*

interface AddShopView {

    fun initWidgit()
    fun displayError(error: String)
    fun hideProgress()
    fun onRouteResponse(movieResponse: RouteListResponse)
    fun onRouteEmptyResponse()
    fun onAreaResponse(movieResponse: AreaListResponse)
    fun noDataAvailable()
    fun onDistributerResponse(movieResponse: DistributorListResponse)
    fun onItemResponse(movieResponse: ItemListResponse)
    fun addAreaNameListInSpinner(areaList: ArrayList<String>, defaultPosition:Int)
    fun addAreaCodeListInSpinner(areacode: ArrayList<String>, defaultPosition: Int)
    fun addRouteNameListInSpinner(routeNameList: ArrayList<String>, defaultPosition: Int)
    fun addRouteCodeListInSpinner(routeCodeList: ArrayList<String>, defaultPosition: Int)
    fun emptyShopValue()
    fun emptyPlaceValue()
    fun emptyMobileValue()
    fun invalidMobileValue()
    fun emptyGstValue()
    fun emptyAddressValue()
    fun emptyPinTinValue()
    fun displaySavedShopMessage(movieResponse: SaveShopResponse)
    fun selectAreaName()
    fun selectAreaCode()
    fun selectRouteName()
    fun selectRouteCode()
}