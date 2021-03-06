package apextechies.singhmehandi.component.activity.order.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class OrderListResponse {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: ArrayList<OrderListData>? = null
}

@Parcelize
class OrderListData(
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("trnum")
    var trnum: String? = null,
    @SerializedName("superstockist")
    var superstockist: String? = null,
    @SerializedName("distributor")
    var distributor: String? = null,
    @SerializedName("shop")
    var shop: String? = null,
    @SerializedName("salesman")
    var salesman: String? = null,
    @SerializedName("route")
    var route: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("addempname")
    var addempname: String? = null,
    @SerializedName("updated")
    var updated: String? = null,
    @SerializedName("remarks")
    var remarks: String? = null,
    @SerializedName("item")
    var item: ArrayList<OrderItem>? = null,
    @SerializedName("quantity")
    var quantity: ArrayList<Orderquantity>? = null

) : Parcelable


@Parcelize
class OrderItem(
    @SerializedName("item_id")
    var item_id: String? = null,
    @SerializedName("item")
    var item: String? = null
) : Parcelable


@Parcelize
class Orderquantity(
    @SerializedName("quantity_id")
    var quantity_id: String? = null,
    @SerializedName("quantity")
    var quantity: String? = null
) : Parcelable
