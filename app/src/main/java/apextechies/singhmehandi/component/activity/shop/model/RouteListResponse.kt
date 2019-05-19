package apextechies.singhmehandi.component.activity.shop.model

import com.google.gson.annotations.SerializedName

class RouteListResponse {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<RouteListdata>? = null
}

class RouteListdata {

    @SerializedName("routename")
    var routename: String? = null
    @SerializedName("routecode")
    var routecode: String? = null
}
