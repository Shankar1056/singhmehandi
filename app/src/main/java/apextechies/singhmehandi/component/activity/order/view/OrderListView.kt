package apextechies.singhmehandi.component.activity.order.view

import apextechies.singhmehandi.component.activity.order.model.OrderDeleteResponse
import apextechies.singhmehandi.component.activity.order.model.OrderListResponse

interface OrderListView {
    fun initWidgit()
    fun hideProgress()
    fun displayError(s: String)
    fun onOrderResponseReceived(movieResponse: OrderListResponse)
    fun invalidUser()
    fun showProgress()
    fun clearList()
    fun onOrderdeleted(movieResponse: String)

}