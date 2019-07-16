package apextechies.singhmehandi.component.activity.order.presenter

import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import apextechies.singhmehandi.AppController
import apextechies.singhmehandi.component.activity.CommonRequestWithDate
import apextechies.singhmehandi.component.activity.order.model.OrderListResponse
import apextechies.singhmehandi.component.activity.order.view.OrderListView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import apextechies.singhmehandi.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class OrderPresenter {
    var context: Context? = null
    var view: OrderListView? = null
    val TAG = "OrderPresenter"
    var fromDate: String? = null
    var toDate: String? = null

    fun OrderPresenter(context: Context, view: OrderListView) {
        this.context = context
        this.view = view
    }

    fun initWidgit() {
        view!!.initWidgit()
    }

    fun getOrderList(fromDate: String, toDate: String) {
        this.fromDate = fromDate
        this.toDate = toDate
        view!!.showProgress()
        getOrderObservable.subscribeWith(getOrderOobserver)
    }

    fun onCreated() {
        view?.initWidgit()
    }




    val getOrderObservable: Observable<OrderListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getOrderList(
                CommonRequestWithDate(
                    fromDate,
                    toDate,
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


    val getOrderOobserver: DisposableObserver<OrderListResponse>
        get() = object : DisposableObserver<OrderListResponse>() {

            override fun onNext(@NonNull movieResponse: OrderListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.invalidUser()
                } else {
                    view!!.onOrderResponseReceived(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                view!!.displayError("Error fetching Movie Data")
                view!!.hideProgress()
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                view!!.hideProgress()
            }
        }

}