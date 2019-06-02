package apextechies.singhmehandi.component.activity.shop.model

import com.google.gson.annotations.SerializedName

class ShopListResponse {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<ShopListData>? = null
}

class ShopListData {

    @SerializedName("retailername")
    var retailername: String? = null
    @SerializedName("retailercode")
    var retailercode: String? = null
    @SerializedName("routename")
    var routename: String? = null
    @SerializedName("routecode")
    var routecode: String? = null
    @SerializedName("areaname")
    var areaname: String? = null
    @SerializedName("areacode")
    var areacode: String? = null
    @SerializedName("place")
    var place: String? = null
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("distributor")
    var distributor: String? = null
    @SerializedName("shoptype")
    var shoptype: String? = null
    @SerializedName("panno")
    var panno: String? = null
    @SerializedName("auth")
    var auth: String? = null
}
