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
    private var context: Context?=null
    var view: ShopView? = null
    fun ShopPresenter(context: Context, view: ShopView) {
        this.view = view;
        this.context = context;
    }

    fun getShopList() {
        view!!.showProgress()
        getOtpObservable.subscribeWith(getOtpOobserver)
    }

    fun initWidgit() {
        view!!.initWidgit()
        view!!.getShopList()
    }


    val getOtpObservable: Observable<ShopListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getShopList(
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


    val getOtpOobserver: DisposableObserver<ShopListResponse>
        get() = object : DisposableObserver<ShopListResponse>() {

            override fun onNext(@NonNull movieResponse: ShopListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)){
                    view!!.invalidUser()
                }else {
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