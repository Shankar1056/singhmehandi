package apextechies.singhmehandi.component.activity.shop.view

import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse

interface ShopView {

    fun initWidgit()
    fun getShopList()
    fun hideProgress()
    fun displayError(s: String)
    fun onReceivedResponse(movieResponse: ShopListResponse)
    fun invalidUser()
    fun showProgress()

}