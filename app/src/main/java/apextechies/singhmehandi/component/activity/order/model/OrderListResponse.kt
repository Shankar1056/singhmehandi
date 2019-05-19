package apextechies.singhmehandi.component.activity.order.model

import com.google.gson.annotations.SerializedName

class OrderListResponse {

    @SerializedName("status")
    var status: String?=null
    @SerializedName("message")
    var message: String?=null
    @SerializedName("data")
    var data: ArrayList<OrderListData>?=null
}

class OrderListData {

    @SerializedName("date")
    var date: String?= null
    @SerializedName("trnum")
    var trnum: String?= null
    @SerializedName("superstockist")
    var superstockist: String?= null
    @SerializedName("distributor")
    var distributor: String?= null
    @SerializedName("shop")
    var shop: String?= null
    @SerializedName("salesman")
    var salesman: String?= null
    @SerializedName("addempname")
    var addempname: String?= null
    @SerializedName("updated")
    var updated: String?= null

}
