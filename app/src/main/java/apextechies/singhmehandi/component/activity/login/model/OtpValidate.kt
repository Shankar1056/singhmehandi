package apextechies.singhmehandi.component.activity.login.model

import com.google.gson.annotations.SerializedName

class OtpValidate(

    @SerializedName("type")
    var type: String? = null,
    @SerializedName("mobile")
    var mobile: String? = null,
    @SerializedName("otp")
    var otp: String? = null,
    @SerializedName("user")
    var user: String? = null
)