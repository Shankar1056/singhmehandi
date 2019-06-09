package apextechies.singhmehandi.component.activity.order.presenter

import android.content.Context
import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.CommonRequest
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.order.view.AddOrderView
import apextechies.singhmehandi.component.activity.shop.preserter.AddShopPresenter.context
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AddOrderPresenter {

    var view: AddOrderView? = null
    var context: Context? = null
    val TAG = "AddOrderPresenter"
    var routeName: String? = null
    var shopName: String? = null
    var salesManName: String? = null
    var selectedRouteName: ArrayList<String>? = null
    var selectedRouteCode: ArrayList<String>? = null
    var radioIOrderType: String? = null
    var quantity: String? = null
    var narration: String? = null


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
        quantity: String,
        narration: String
    ) {
        this.routeName = routeName
        this.shopName = shopName
        this.salesManName = salesManName
        this.selectedRouteName = selectedRouteName
        this.selectedRouteCode = selectedRouteCode
        this.radioIOrderType = radioIOrderType
        this.quantity = quantity
        this.narration = narration

        if (TextUtils.isEmpty(routeName)) {
            context?.resources?.getString(R.string.empty_rootname)?.let { view?.showEmptyStringMessage(it) }
        } else if (TextUtils.isEmpty(shopName)) {
            context?.resources?.getString(R.string.empty_shopname)?.let { view?.showEmptyStringMessage(it) }
        } else if (TextUtils.isEmpty(salesManName)) {
            context?.resources?.getString(R.string.empty_sales_man_name)?.let { view?.showEmptyStringMessage(it) }
        } else if (TextUtils.isEmpty(quantity)) {
            context?.resources?.getString(R.string.empty_quantity)?.let { view?.showEmptyStringMessage(it) }
        } else if (radioIOrderType.equals(context?.resources?.getString(R.string.order))) {
            if (TextUtils.isEmpty(narration)) {
                context?.resources?.getString(R.string.empty_narration)?.let { view?.showEmptyStringMessage(it) }
            }
        } else {
            view!!.showProgress()
            // saveOrderObservable.subscribeWith(saveOrderObserver)
        }
    }

    fun getOrderItemList() {
        getItemObservable.subscribeWith(getItemObserver)
    }


    /*val saveOrderObservable: Observable<OrderListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getOrderList(
                CommonRequestWithDate(
                    "01/16/2019",
                    "5/16/2019",
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.USER),
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.DB),
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.REGION),
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.SUPERSTOCKIST),
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.STATE),
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.EMPLOYEENAME),
                    ClsGeneral.getPreferences(AppController.getInstance(), Constants.EMPLOYEEID)
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val saveOrderObserver: DisposableObserver<OrderListResponse>
        get() = object : DisposableObserver<OrderListResponse>() {

            override fun onNext(@NonNull movieResponse: OrderListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.invalidUser()
                } else {
                    view!!.onOrderResponseReceived(movieResponse)
                }
            }*/
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