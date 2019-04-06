package apextechies.singhmehandi.login.model

import com.google.gson.annotations.SerializedName

class LoginModelVerifyModel {

    @SerializedName("status")
    var status: String?=null
    @SerializedName("message")
    var message: String?=null
    @SerializedName("data")
    var data: ArrayList<LoginModelData>?=null
}

class LoginModelData {
    @SerializedName("db")
    var db: String?=null
    @SerializedName("user")
    var user: String?=null
    @SerializedName("client")
    var client: String?=null
    @SerializedName("phone")
    var phone: String?=null
}
