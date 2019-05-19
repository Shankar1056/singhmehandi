package apextechies.singhmehandi.model

import com.google.gson.annotations.SerializedName

class FeedbackRequest {

    @SerializedName("date")
    var date: String? = null
    @SerializedName("docno")
    var docno: String? = null
    @SerializedName("department")
    var department: String? = null
    @SerializedName("selected_region")
    var selected_region: String? = null
    @SerializedName("areaname")
    var areaname: String? = null
    @SerializedName("complaintant")
    var complaintant: String? = null
    @SerializedName("mode")
    var mode: String? = null
    @SerializedName("otherfeedback")
    var otherfeedback: String? = null
    @SerializedName("user")
    var user: String? = null
    @SerializedName("db")
    var db: String? = null
    @SerializedName("region")
    var region: String? = null
    @SerializedName("superstockist")
    var superstockist: String? = null
    @SerializedName("state")
    var state: String? = null
    @SerializedName("employeename")
    var employeename: String? = null
    @SerializedName("employeeid")
    var employeeid: String? = null
    @SerializedName("catgroup")
    var catgroup: ArrayList<FeedbackCategoryGroup>? = null
    @SerializedName("cattype")
    var cattype: ArrayList<FeedbackCategoryType>? = null
    @SerializedName("selection")
    var selection: ArrayList<FeedbackSelection>? = null
    @SerializedName("itemcat")
    var itemcat: ArrayList<FeedbackItemCategory>? = null
    @SerializedName("itemdesc")
    var itemdesc: ArrayList<FeedbackItemDescription>? = null
    @SerializedName("narration")
    var narration: ArrayList<Narration>? = null
}

class FeedbackCategoryGroup {

}

class FeedbackCategoryType {

}
class FeedbackSelection {}
class FeedbackItemCategory {}
class FeedbackItemDescription {}
class Narration {}
