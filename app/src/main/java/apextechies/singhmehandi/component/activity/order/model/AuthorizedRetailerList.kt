package apextechies.singhmehandi.component.activity.order.model

import com.google.gson.annotations.SerializedName

class AuthorizedRetailerList {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<AuthorizedRetailerDataList>? = null
}

class AuthorizedRetailerDataList {

    @SerializedName("retailername")
    var retailername: String? = null
    @SerializedName("retailercode")
    var retailercode: String? = null

}
