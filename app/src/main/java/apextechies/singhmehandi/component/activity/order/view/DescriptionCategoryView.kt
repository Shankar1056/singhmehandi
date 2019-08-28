package apextechies.singhmehandi.component.activity.order.view

import apextechies.singhmehandi.component.activity.order.model.ItemListData
import java.util.ArrayList

interface DescriptionCategoryView {
    fun noDataAvailable()
    fun onItemResponse(data: ArrayList<ItemListData>)
    fun displayError(s: String)
    fun hideProgress()
}