package apextechies.singhmehandi.component.activity.shop.model

import com.google.gson.annotations.SerializedName

class SaveShopResponse {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<SaveShopDataResponse>? = null

}

class SaveShopDataResponse {

}
