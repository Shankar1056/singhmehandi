package apextechies.singhmehandi.component.activity.order.view

import apextechies.singhmehandi.component.activity.order.model.ItemListData
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse

interface AddOrderView {

    fun initWidgit()
    fun showProgress()
    fun hideProgress()
    fun showEmptyStringMessage(string: String)
    fun noDataAvailable()
    fun onItemResponse(movieResponse: ArrayList<String>, listResponse: ArrayList<ItemListData>)
    fun displayError(errorMessage: String) {

    }

}