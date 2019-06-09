package apextechies.singhmehandi.component.activity.order.view

import apextechies.singhmehandi.component.activity.order.model.OrderListResponse

interface OrderListView {
    fun initWidgit()
    fun getOrderList()
    fun hideProgress()
    fun displayError(s: String)
    fun onOrderResponseReceived(movieResponse: OrderListResponse)
    fun invalidUser()
    fun showProgress()

}