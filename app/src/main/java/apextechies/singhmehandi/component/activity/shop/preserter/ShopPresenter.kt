package apextechies.singhmehandi.component.activity.shop.preserter

import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import apextechies.singhmehandi.AppController
import apextechies.singhmehandi.component.activity.CommonRequestWithDate
import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse
import apextechies.singhmehandi.component.activity.shop.view.ShopView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


object ShopPresenter {
    private val TAG = "ShopPresenter"
    private var context: Context? = null
    var view: ShopView? = null
    var fromDate: String? = null
    var toDate: String? = null
    fun ShopPresenter(context: Context, view: ShopView) {
        this.view = view;
        this.context = context;
    }

    fun getShopList(fromDate: String, toDate: String) {
        this.fromDate = fromDate
        this.toDate = toDate
        view!!.showProgress()
        Log.d("range_predenter : ", "$fromDate - $toDate")
        geShopObservable.subscribeWith(getShopOobserver)
    }

    fun initWidgit() {
        view!!.initWidgit()
    }

    private val geShopObservable: Observable<ShopListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getShopList(
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


    private val getShopOobserver: DisposableObserver<ShopListResponse>
        get() = object : DisposableObserver<ShopListResponse>() {

            override fun onNext(@NonNull movieResponse: ShopListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    view!!.invalidUser()
                    view!!.clearList()
                } else {
                    Log.d("range response : ", "Area Code: ${movieResponse.data!![0].areacode}")
                    view!!.onReceivedResponse(movieResponse)
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