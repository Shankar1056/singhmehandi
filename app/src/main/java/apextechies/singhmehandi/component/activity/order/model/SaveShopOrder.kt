package apextechies.singhmehandi.component.activity.order.model

import com.google.gson.annotations.SerializedName

 class SaveShopOrder {
     @SerializedName("trnum")
     var trnum: String? = null
     @SerializedName("date")
     var date: String? = null
     @SerializedName("route")
     var route: String? = null
     @SerializedName("retailer")
     var retailer: String? = null
     @SerializedName("type")
     var type: String? = null
     @SerializedName("narration")
     var narration: String? = null
     @SerializedName("user")
     var user: String? = null
     @SerializedName("db")
     var db: String? = null
     @SerializedName("region")
     var region: String? = null
     @SerializedName("superstockist")
     var superstockist: String? = null
     @SerializedName("state")
     var state: String? = null
     @SerializedName("employeename")
     var employeename: String? = null
     @SerializedName("employeeid")
     var employeeid: String? = null
     @SerializedName("description")
     var descriptin: ArrayList<DescriptionList>? = null
     @SerializedName("quantity")
     var quantity: ArrayList<QuantityList>? = null
 }
 class DescriptionList {
     @SerializedName("description_id")
     var description_id: String? = null
     @SerializedName("description")
     var description: String? = null
 }

 class QuantityList {

    @SerializedName("quantity_id")
    var quantity_id: Int? = null
    @SerializedName("quantity")
    var quantity: String? = null
}