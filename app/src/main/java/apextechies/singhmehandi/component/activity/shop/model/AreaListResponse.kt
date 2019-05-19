package apextechies.singhmehandi.component.activity.shop.model

import com.google.gson.annotations.SerializedName

class AreaListResponse {

    @SerializedName("status")
    var status: String?=null
    @SerializedName("message")
    var message: String?=null
    @SerializedName("data")
    var data: ArrayList<AreaListData>?=null
}

class AreaListData {

    @SerializedName("areaname")
    var areaname: String?=null
    @SerializedName("areacode")
    var areacode: String?=null
}
