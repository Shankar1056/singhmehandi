package apextechies.singhmehandi.component.activity.order.presenter

import android.content.Context
import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.component.activity.CommonRequest
import apextechies.singhmehandi.component.activity.order.Download_web
import apextechies.singhmehandi.component.activity.order.OnTaskCompleted
import apextechies.singhmehandi.component.activity.order.model.*
import apextechies.singhmehandi.component.activity.order.view.AddOrderView
import apextechies.singhmehandi.component.activity.shop.model.RouteListResponse
import apextechies.singhmehandi.component.activity.shop.model.RouteListdata
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import apextechies.singhmehandi.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response


class AddOrderPresenter {

    var view: AddOrderView? = null
    var context: Context? = null
    val TAG = "AddOrderPresenter"
    var routeName: String? = null
    var shopName: String? = null
    var salesManName: String? = null
    var descriptionName: ArrayList<String>? = null
    var descriptionId: ArrayList<String>? = null
    var quantity: ArrayList<String>? = null
    var radioIOrderType: String? = null
    var narration: String? = null
    var quantityList = ArrayList<QuantityList>()
    var descroptionList = ArrayList<DescriptionList>()
    var soa = SaveShopOrder()


    fun AddOrderPresenter(view: AddOrderView, context: Context) {
        this.view = view
        this.context = context
    }

    fun onCreated() {
        view!!.initWidgit()
    }

    fun validateField(
        routeName: String,
        shopName: String,
        salesManName: String,
        selectedRouteName: ArrayList<String>,
        selectedRouteCode: ArrayList<String>,
        radioIOrderType: String,
        quantity: ArrayList<String>,
        narration: String
    ) {
        this.routeName = routeName
        this.shopName = shopName
        this.salesManName = salesManName
        this.descriptionName = selectedRouteName
        this.descriptionId = selectedRouteCode
        this.radioIOrderType = radioIOrderType
        this.quantity = quantity
        this.narration = narration

        if (TextUtils.isEmpty(routeName)) {
            context?.resources?.getString(apextechies.singhmehandi.R.string.empty_rootname)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (TextUtils.isEmpty(shopName)) {
            context?.resources?.getString(apextechies.singhmehandi.R.string.empty_shopname)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (TextUtils.isEmpty(salesManName)) {
            context?.resources?.getString(apextechies.singhmehandi.R.string.empty_sales_man_name)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (TextUtils.isEmpty(narration)) {
            context?.resources?.getString(apextechies.singhmehandi.R.string.empty_narration)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (radioIOrderType.equals(context?.resources?.getString(apextechies.singhmehandi.R.string.order))) {

            for (i in 0 until descriptionName!!.size) {
                var descList = DescriptionList()
                descList.description = descriptionName!![i]
                descList.description_id = descriptionId!![i]
                descroptionList.add(descList)
            }
            for (i in 0 until quantity!!.size) {
                var list = QuantityList()
                list.quantity = quantity!![i]
                list.quantity_id = i
                quantityList.add(list)
            }
        }
        view!!.showProgress()


        soa.date = Utils.getCurrentDateForOrder()
        soa.route = routeName
        soa.retailer = shopName
        soa.type = radioIOrderType
        soa.narration = narration
        soa.descriptin = descroptionList
        soa.quantity = quantityList
        soa.user = ClsGeneral.getPreferences(context, Constants.USER)
        soa.db = ClsGeneral.getPreferences(context, Constants.DB)
        soa.region = ClsGeneral.getPreferences(context, Constants.REGION)
        soa.superstockist = ClsGeneral.getPreferences(context, Constants.SUPERSTOCKIST)
        soa.state = ClsGeneral.getPreferences(context, Constants.STATE)
        soa.employeeid = ClsGeneral.getPreferences(context, Constants.EMPLOYEEID)
        soa.employeename = ClsGeneral.getPreferences(context, Constants.EMPLOYEENAME)

/*
        var web = Download_web(object : OnTaskCompleted {
            override fun onTaskCompleted(response: String?) {
                Log.i("response", response)
            }
        })
        var obj = JSONObject()
        obj.put("date", Utils.getCurrentDate())
        obj.put("route", routeName)
        obj.put("retailer", shopName)
        obj.put("type", radioIOrderType)
        obj.put("narration", narration)
        obj.put("user", ClsGeneral.getPreferences(context, Constants.USER))
        obj.put("db", ClsGeneral.getPreferences(context, Constants.DB))
        obj.put("region", ClsGeneral.getPreferences(context, Constants.REGION))
        obj.put("superstockist", ClsGeneral.getPreferences(context, Constants.SUPERSTOCKIST))
        obj.put("state", ClsGeneral.getPreferences(context, Constants.STATE))
        obj.put("employeename", ClsGeneral.getPreferences(context, Constants.EMPLOYEEID))
        obj.put("employeeid", ClsGeneral.getPreferences(context, Constants.EMPLOYEENAME))
        var array = JSONArray()
        var quanObj = JSONObject()
        for (i in 0 until quantity!!.size) {
            quanObj.put("quantity_id", i)
            quanObj.put("quantity", quantity!![i])
        }
        array.put(quanObj)
        obj.put("quantity", array)

        var decsarray = JSONArray()
        var decsObj = JSONObject()
        for (i in 0 until descriptionName!!.size) {
            decsObj.put("description_id", descriptionId!![i])
            decsObj.put("description", descriptionName!![i])
        }
        decsarray.put(decsObj)
        obj.put("description", decsarray)

        web.setData(obj)

        web.execute("https://ssm.smocglobal.com/androidApp/salesOrderAPI.php");*/

         saveOrderObservable.subscribeWith(saveOrderObserver)

    }

    fun getOrderItemList() {
        getItemObservable.subscribeWith(getItemObserver)
    }

    fun getAuthorisedRoute() {
        getAuthorisedRouteItemObservable.subscribeWith(getAuthorisedRouteItemObserver)
    }

    fun onAuthorizedRouteReceived(message: ArrayList<RouteListdata>) {
        Log.e("Order Presenter", "onAuthorizedRouteReceived")
        getRouteNameAndCodeFromList(message)
    }

    private fun getRouteNameAndCodeFromList(routeList: ArrayList<RouteListdata>?) {
        Log.e("Order Presenter", "getRouteNameAndCodeFromList")
        var routeNameList = ArrayList<String>()
        for (name in routeList!!) {
            name.routename?.let { routeNameList.add(it) }
        }
        view!!.addRouteNameListInSpinner(routeNameList)
    }

    fun getAreaNameFound(stringExtra: String, orderRouteList: java.util.ArrayList<RouteListdata>) {

        for (i in 0 until orderRouteList!!.size) {
            if (stringExtra.equals(orderRouteList[i].routename)) {
                view?.selectSpinnerPosition(i)
            }
        }
    }


    val getAuthorisedRouteItemObservable: Observable<RouteListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getRouteList(
                CommonRequest(
                    ClsGeneral.getPreferences(context, Constants.USER),
                    ClsGeneral.getPreferences(context, Constants.DB),
                    ClsGeneral.getPreferences(context, Constants.REGION),
                    ClsGeneral.getPreferences(context, Constants.SUPERSTOCKIST),
                    ClsGeneral.getPreferences(context, Constants.STATE),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEENAME),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEEID)
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getAuthorisedRouteItemObserver: DisposableObserver<RouteListResponse>
        get() = object : DisposableObserver<RouteListResponse>() {

            override fun onNext(@NonNull movieResponse: RouteListResponse) {
                Log.e("Order Presenter", "onNext")
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.noDataAvailable()
                } else {
                    view!!.onAuthorisedDealerOrderResponse(movieResponse.data)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                Log.e("Order Presenter", "onError")
                e.printStackTrace()
                view!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                Log.e("Order Presenter", "onComplete")
                view!!.hideProgress()
            }
        }


    val saveOrderObservable: Observable<SaveShopOrderResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .visitOrGetShopOrder(
                soa
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val saveOrderObserver: DisposableObserver<SaveShopOrderResponse>
        get() = object : DisposableObserver<SaveShopOrderResponse>() {

            override fun onNext(@NonNull movieResponse: SaveShopOrderResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                 if (movieResponse.status.equals(Constants.FAIL)) {
                     view!!.noDataAvailable()
                 } else {
                     view!!.onaddOrderResponse(movieResponse.message!!)
                 }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                view!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                view!!.onCompleted()
                view!!.hideProgress()
            }
        }

    val getItemObservable: Observable<ItemListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getItemList(
                CommonRequest(
                    ClsGeneral.getPreferences(context, Constants.USER),
                    ClsGeneral.getPreferences(context, Constants.DB),
                    ClsGeneral.getPreferences(context, Constants.REGION),
                    ClsGeneral.getPreferences(context, Constants.SUPERSTOCKIST),
                    ClsGeneral.getPreferences(context, Constants.STATE),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEENAME),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEEID)
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getItemObserver: DisposableObserver<ItemListResponse>
        get() = object : DisposableObserver<ItemListResponse>() {

            override fun onNext(@NonNull movieResponse: ItemListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.noDataAvailable()
                } else {
                    var orderItemNameList = ArrayList<String>()
                    for (name in movieResponse.data!!) name.description?.let { orderItemNameList.add(it) }
                    view!!.onItemResponse(orderItemNameList, movieResponse.data!!)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                view!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                view!!.hideProgress()
            }
        }
}