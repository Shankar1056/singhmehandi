package apextechies.singhmehandi.component.activity.order.presenter

import android.content.Context
import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.CommonRequest
import apextechies.singhmehandi.component.activity.order.model.*
import apextechies.singhmehandi.component.activity.order.view.AddOrderView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import apextechies.singhmehandi.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
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
            context?.resources?.getString(R.string.empty_rootname)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (TextUtils.isEmpty(shopName)) {
            context?.resources?.getString(R.string.empty_shopname)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (TextUtils.isEmpty(salesManName)) {
            context?.resources?.getString(R.string.empty_sales_man_name)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (TextUtils.isEmpty(narration)) {
            context?.resources?.getString(R.string.empty_narration)?.let {
                view?.showEmptyStringMessage(it)
                return
            }
        }
        if (radioIOrderType.equals(context?.resources?.getString(R.string.order))) {

            for (i in 0 until descriptionName!!.size) {
                descroptionList.add(DescriptionList(descriptionId!![i], descriptionName!![i]))
            }
            for (i in 0 until quantity!!.size) {
                quantityList.add(QuantityList("" + i, quantity!![i]))
            }
        }
        view!!.showProgress()
        saveOrderObservable.subscribeWith(saveOrderObserver)

    }

    fun getOrderItemList() {
        getItemObservable.subscribeWith(getItemObserver)
    }


    val saveOrderObservable: Observable<Response<Void>>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .visitOrGetShopOrder(
                SaveShopOrder(
                    Utils.getCurrentDate(),
                    routeName,
                    shopName,
                    radioIOrderType,
                    narration,
                    ClsGeneral.getPreferences(context, Constants.USER),
                    ClsGeneral.getPreferences(context, Constants.DB),
                    ClsGeneral.getPreferences(context, Constants.REGION),
                    ClsGeneral.getPreferences(context, Constants.SUPERSTOCKIST),
                    ClsGeneral.getPreferences(context, Constants.STATE),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEENAME),
                    ClsGeneral.getPreferences(context, Constants.EMPLOYEEID),
                    descroptionList,
                    quantityList
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val saveOrderObserver: DisposableObserver<Response<Void>>
        get() = object : DisposableObserver<Response<Void>>() {

            override fun onNext(@NonNull movieResponse: Response<Void>) {
                Log.d(TAG, "OnNext$movieResponse")
               /* if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.noDataAvailable()
                } else {
                    view!!.onaddOrderResponse(movieResponse.message!!)
                }*/
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
                    for (name in movieResponse.data!!) name.cat?.let { orderItemNameList.add(it) }
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