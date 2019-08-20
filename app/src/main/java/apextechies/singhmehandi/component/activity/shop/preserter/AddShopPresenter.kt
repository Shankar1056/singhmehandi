package apextechies.singhmehandi.component.activity.shop.preserter

import android.content.Context
import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.CommonRequest
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.shop.model.*
import apextechies.singhmehandi.component.activity.shop.view.AddShopView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import apextechies.singhmehandi.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

object AddShopPresenter {
    val TAG = "AddShopPresenter"
    var shopView: AddShopView? = null
    var context: Context? = null
    var areaCode: String? = null
    var areaName: String? = null
    var routeCode: String? = null
    var routeName: String? = null
    var shopName: String? = null
    var place: String? = null
    var phone: String? = null
    var gestNo: String? = null
    var shopType: String? = null
    var pintin: String? = null
    var address: String? = null
    var note: String? = null

    fun AddShopPresenter(context: Context, shopView: AddShopView) {
        AddShopPresenter.context = context
        AddShopPresenter.shopView = shopView
    }

    fun onCreated() {
        shopView!!.initWidgit()
    }


    fun getAreaList() {
        getAreaObservable.subscribeWith(
            getAreaObserver
        )
    }

    fun getRouteList(areaName: String, areaCode: String) {
        AddShopPresenter.areaName = areaName
        AddShopPresenter.areaCode = areaCode
        if (areaName.equals(context?.resources?.getString(R.string.selectareaname))) {
            shopView!!.onRouteEmptyResponse()
        } else {
            getRouteObservable.subscribeWith(
                getRouteObserver
            )
        }
    }

    fun getDistributorList() {
        getDistributorObservable.subscribeWith(
            getDistributorObserver
        )
    }

    fun getRetailerList() {
        getItemObservable.subscribeWith(
            getItemObserver
        )
    }

    fun onAreaResponceReceived(areaList: ArrayList<AreaListData>?, defaultPosition: Int) {
        getAreaNameAndCodeFromList(
            areaList,
            defaultPosition
        )
    }

    private fun getAreaNameAndCodeFromList(areaList: ArrayList<AreaListData>?, defaultPosition: Int) {
        var areaNameList = ArrayList<String>()
        var areaCodeList = ArrayList<String>()
        for (name in areaList!!) {
            name.areaname?.let { areaNameList.add(it) }
            name.areacode?.let { areaCodeList.add(it) }
        }
        shopView!!.addAreaNameListInSpinner(areaNameList, defaultPosition)
        shopView!!.addAreaCodeListInSpinner(areaCodeList, defaultPosition)
    }

    fun onRouteResponceReceived(routeList: ArrayList<RouteListdata>?, defaultPosition: Int) {
        getRouteNameAndCodeFromList(
            routeList,
            defaultPosition
        )

    }

    private fun getRouteNameAndCodeFromList(routeList: ArrayList<RouteListdata>?, defaultPosition: Int) {
        var routeNameList = ArrayList<String>()
        var routeCodeList = ArrayList<String>()
        for (name in routeList!!) {
            name.routename?.let { routeNameList.add(it) }
            name.routecode?.let { routeCodeList.add(it) }
        }
        shopView!!.addRouteNameListInSpinner(routeNameList, defaultPosition)
        shopView!!.addRouteCodeListInSpinner(routeCodeList, defaultPosition)
    }


    fun areaSelected(
        areaList: ArrayList<AreaListData>?,
        position: Int
    ) {
        var areaCodeList = ArrayList<String>()
        for (name in areaList!!) {
            name.areacode?.let { areaCodeList.add(it) }
        }
        shopView!!.addAreaCodeListInSpinner(areaCodeList, position)

    }

    fun routeSelected(
        routeList: ArrayList<RouteListdata>?,
        position: Int
    ) {

        var routeCodeList = ArrayList<String>()
        for (name in routeList!!) {
            name.routecode?.let { routeCodeList.add(it) }
        }
        shopView!!.addRouteCodeListInSpinner(routeCodeList, position)
    }

    fun onSubmitClick(
        areaName: String,
        areaCode: String,
        routeName: String,
        routeCode: String,
        shopName: String,
        place: String,
        mobile: String,
        gst: String,
        shopType: String?,
        address: String,
        pintin: String,
        note: String
    ) {
        this.areaName = areaName
        this.areaCode = areaCode
        this.routeName = routeName
        this.routeCode = routeCode
        this.shopName = shopName
        this.place = place
        this.phone = mobile
        this.gestNo = gst
        this.shopType = shopType
        this.address = address
        this.pintin = pintin
        this.note = note
        if (areaName.equals(context?.resources?.getString(R.string.selectareaname))) {
            shopView!!.selectAreaName()
        }else if (areaCode.equals(context?.resources?.getString(R.string.selectareacode))) {
            shopView!!.selectAreaCode()
        }else if (routeName.equals(context?.resources?.getString(R.string.selectroutename))) {
            shopView!!.selectRouteName()
        } else if (routeCode.equals(context?.resources?.getString(R.string.selectroutecode))) {
            shopView!!.selectRouteCode()
        } else if (shopName.trim().equals("")) {
            shopView!!.emptyShopValue()
        } else if (place.trim().equals("")) {
            shopView!!.emptyPlaceValue()
        } else if (mobile.trim().equals("")) {
            shopView!!.emptyMobileValue()
        } else if (mobile.trim().length < 10) {
            shopView!!.invalidMobileValue()
        } /*else if (TextUtils.isEmpty(gst)) {
            shopView!!.emptyGstValue()
        } */ else if (address.trim().equals("")) {
            shopView!!.emptyAddressValue()
        } /*else if (TextUtils.isEmpty(pintin)) {
            shopView!!.emptyPinTinValue()
        } */ else {
            addShopObservable.subscribeWith(addShopaObserver)
        }
    }


    val getAreaObservable: Observable<AreaListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getAreaList(
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


    val getAreaObserver: DisposableObserver<AreaListResponse>
        get() = object : DisposableObserver<AreaListResponse>() {

            override fun onNext(@NonNull movieResponse: AreaListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    shopView!!.noDataAvailable()
                } else {

                    shopView!!.onAreaResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                shopView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                shopView!!.hideProgress()
            }
        }


    val getRouteObservable: Observable<RouteListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getRouteList(
                RouteListRequest(
                    areaName,
                    areaCode,
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


    val getRouteObserver: DisposableObserver<RouteListResponse>
        get() = object : DisposableObserver<RouteListResponse>() {

            override fun onNext(@NonNull movieResponse: RouteListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    shopView!!.noDataAvailable()
                } else {
                    shopView!!.onRouteResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                shopView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                shopView!!.hideProgress()
            }
        }


    val getDistributorObservable: Observable<DistributorListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getDistributorList(
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


    val getDistributorObserver: DisposableObserver<DistributorListResponse>
        get() = object : DisposableObserver<DistributorListResponse>() {

            override fun onNext(@NonNull movieResponse: DistributorListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    shopView!!.noDataAvailable()
                } else {
                    shopView!!.onDistributerResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                shopView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                shopView!!.hideProgress()
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
                    shopView!!.noDataAvailable()
                } else {
                    shopView!!.onItemResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                shopView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                shopView!!.hideProgress()
            }
        }


    val addShopObservable: Observable<SaveShopResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .addShop(
                SaveShopDetailsRequest(
                    Utils.getCurrentDate(),
                    areaCode,
                    areaName,
                    routeCode,
                    routeName,
                    shopName,
                    place,
                    phone,
                    gestNo,
                    shopType,
                    pintin,
                    address,
                    note,
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


    val addShopaObserver: DisposableObserver<SaveShopResponse>
        get() = object : DisposableObserver<SaveShopResponse>() {

            override fun onNext(@NonNull movieResponse: SaveShopResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    shopView!!.noDataAvailable()
                } else {
                    shopView!!.displaySavedShopMessage(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                shopView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                shopView!!.hideProgress()
            }
        }


}