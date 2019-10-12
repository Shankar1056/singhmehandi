package apextechies.singhmehandi.component.activity.order.presenter

import android.content.Context
import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.component.activity.CommonRequest
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


class AddOrderPresenter {

    var view: AddOrderView? = null
    var context: Context? = null
    val TAG = "AddOrderPresenter"
    var routeName: String? = null
    var routeId: String? = null
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
        this.radioIOrderType = radioIOrderType.toLowerCase()
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


        soa.date = Utils.getCurrentDateWithDash()
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


        saveOrderObservable.subscribeWith(saveOrderObserver)

    }


    fun getAuthorisedRoute() {
        getAuthorisedRouteItemObservable.subscribeWith(getAuthorisedRouteItemObserver)
    }

    fun onAuthorizedRouteReceived( routeList: ArrayList<RouteListdata>, locationName: String ) {
        var pos = 0
        var routeNameList = ArrayList<String>()
        for (i in 0 until   routeList.size) {
            routeList[i].routename?.let { routeNameList.add(it) }
            if (locationName?.trim() != "" && locationName?.length!! > 0) {
                if (locationName.equals(routeList[i].routename)) {
                    pos = i
                }
            }

        }
        view!!.addRouteNameListInSpinner(routeNameList, pos)
    }


    fun getAuthorisedShop(routeName: String, routeId: String?) {
        this.routeName = routeName
        this.routeId = routeId
        getAuthorisedShopObservable.subscribeWith(getAuthorisedShopObserver)
    }

    fun getOnlyShop(selectedShop: String?, data: java.util.ArrayList<AuthorizedRetailerDataList>?) {
        var shopList = ArrayList<String>()
        var pos = 0

        if (data != null) {
            for (i in 0 until data.size) {
                data[i].retailername?.let { shopList.add(it) }
                if (selectedShop?.trim() != null && selectedShop?.length!! > 0) {
                    if (selectedShop.equals(data[i].retailername)) {
                        pos = i
                    }
                }
            }

            view?.AuthorizedShopWithSelectedPosition(shopList, pos)
        }
    }


    val getAuthorisedShopObservable: Observable<AuthorizedRetailerList>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getAuthorisedRetailer(
                AuthorizedRetailerRequest(
                    ClsGeneral.getPreferences(context, Constants.USER),
                    ClsGeneral.getPreferences(context, Constants.DB),
                    ClsGeneral.getPreferences(context, Constants.REGION),
                    ClsGeneral.getPreferences(context, Constants.SUPERSTOCKIST),
                    ClsGeneral.getPreferences(context, Constants.STATE),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEENAME),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEEID),
                    routeId,
                    routeName
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getAuthorisedShopObserver: DisposableObserver<AuthorizedRetailerList>
        get() = object : DisposableObserver<AuthorizedRetailerList>() {

            override fun onNext(@NonNull movieResponse: AuthorizedRetailerList) {
                Log.e("Order Presenter", "onNext")
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.noDataAvailable()
                } else {
                    view!!.onAuthorisedShopResponse(movieResponse.data)
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


}