package apextechies.singhmehandi.component.activity.order.model

import com.google.gson.annotations.SerializedName

class SaveShopOrderResponse {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<SaveShopOrderData>? = null
}

class SaveShopOrderData {

}
