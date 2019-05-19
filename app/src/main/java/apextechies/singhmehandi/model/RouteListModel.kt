package apextechies.singhmehandi.model

import com.google.gson.annotations.SerializedName

class RouteListModel {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<RouteListDataModel>? = null
}

class RouteListDataModel {
    @SerializedName("routename")
    var routename: String? = null
    @SerializedName("routecode")
    var routecode: String? = null
}
