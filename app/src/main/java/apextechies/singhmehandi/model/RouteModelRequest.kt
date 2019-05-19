package apextechies.singhmehandi.model

import com.google.gson.annotations.SerializedName

class RouteModelRequest {
    @SerializedName("user")
    var user: String?=null
    @SerializedName("db")
    var db: String?=null
    @SerializedName("region")
    var region: String?=null
    @SerializedName("superstockist")
    var superstockist: String?=null
    @SerializedName("state")
    var state: String?=null
    @SerializedName("employeename")
    var employeename: String?=null
    @SerializedName("employeeid")
    var employeeid: String?=null
}