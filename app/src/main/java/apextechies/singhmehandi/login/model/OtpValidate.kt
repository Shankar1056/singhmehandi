package apextechies.singhmehandi.login.model

import com.google.gson.annotations.SerializedName

class OtpValidate(

    @SerializedName("token")
    var token: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("mobile")
    var mobile: String? = null,
    @SerializedName("otp")
    var otp: String? = null
)