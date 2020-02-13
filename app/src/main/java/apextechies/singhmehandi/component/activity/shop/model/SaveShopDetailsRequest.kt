package apextechies.singhmehandi.component.activity.shop.model

import com.google.gson.annotations.SerializedName

class SaveShopDetailsRequest (

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("areacode")
    var areacode: String? = null,
    @SerializedName("areaname")
    var areaname: String? = null,
    @SerializedName("routecode")
    var routecode: String? = null,
    @SerializedName("routename")
    var routename: String? = null,
    @SerializedName("shopname")
    var shopname: String? = null,
    @SerializedName("place")
    var place: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("gstno")
    var gstno: String? = null,
    @SerializedName("shoptype")
    var shoptype: String? = null,
    @SerializedName("pan")
    var pan: String? = null,
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("narration")
    var narration: String? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("user")
    var user: String? = null,
    @SerializedName("db")
    var db: String? = null,
    @SerializedName("region")
    var region: String? = null,
    @SerializedName("superstockist")
    var superstockist: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("employeename")
    var employeename: String? = null,
    @SerializedName("employeeid")
    var employeeid: String? = null
)