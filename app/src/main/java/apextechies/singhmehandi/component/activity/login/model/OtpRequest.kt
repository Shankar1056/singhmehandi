package apextechies.singhmehandi.component.activity.login.model

import com.google.gson.annotations.SerializedName

class OtpRequest(
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("user")
    var user: String? = null
)
