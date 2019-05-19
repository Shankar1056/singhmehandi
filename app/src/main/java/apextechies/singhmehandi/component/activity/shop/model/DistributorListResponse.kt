package apextechies.singhmehandi.component.activity.shop.model

import com.google.gson.annotations.SerializedName

class DistributorListResponse {

    @SerializedName("status")
    var status: String?=null
    @SerializedName("message")
    var message: String?=null
    @SerializedName("data")
    var data: ArrayList<DistributorListData>?=null
}

class DistributorListData {

    @SerializedName("distributorname")
    var distributorname: String?=null
    @SerializedName("distributorcode")
    var distributorcode: String?=null
    @SerializedName("areaname")
    var areaname: String?=null
    @SerializedName("areacode")
    var areacode: String?=null

}
