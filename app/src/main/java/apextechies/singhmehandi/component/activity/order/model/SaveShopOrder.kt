package apextechies.singhmehandi.component.activity.order.model

import com.google.gson.annotations.SerializedName

class SaveShopOrder {
    @SerializedName("date")
    var date: String?=null
    @SerializedName("route")
    var route: String?=null
    @SerializedName("retailer")
    var retailer: String?=null
    @SerializedName("type")
    var type: String?=null
    @SerializedName("narration")
    var narration: String?=null
    @SerializedName("user")
    var user: String?=null
    @SerializedName("db")
    var db: String?=null
    @SerializedName("description")
    var description: ArrayList<DescriptionList>?=null

}
class DescriptionList {

}