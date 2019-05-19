package apextechies.singhmehandi.component.activity.login.model

import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("status")
    var status: String?=null
    @SerializedName("message")
    var message: String?=null
    @SerializedName("mobile")
    var mobile: String?=null

}