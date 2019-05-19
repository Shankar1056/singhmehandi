package apextechies.singhmehandi.component.activity.order.model

import com.google.gson.annotations.SerializedName

class ItemListResponse {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<ItemListData>? = null
}

class ItemListData {

    @SerializedName("cat")
    var cat: String? = null
    @SerializedName("code")
    var code: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("cunits")
    var cunits: String? = null
}
