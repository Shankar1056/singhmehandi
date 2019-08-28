package apextechies.singhmehandi.component.activity.order.presenter

import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import apextechies.singhmehandi.component.activity.CommonRequest
import apextechies.singhmehandi.component.activity.order.model.DescriptionCategoryRequestModel
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.order.view.DescriptionCategoryView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DescriptionCategoryPresenter {
    var view: DescriptionCategoryView? = null
    var context: Context? = null
    var category: String? = null
    val TAG = "DescriptionCategory"


    fun DescriptionCategoryPresenter(view: DescriptionCategoryView, context: Context) {
        this.view = view
        this.context = context
    }

    fun getDescriptionList(category: String) {
        this.category = category
        getItemObservable.subscribeWith(getItemObserver)
    }

    val getItemObservable: Observable<ItemListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getItemList(
                DescriptionCategoryRequestModel(
                    category,
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
                    view!!.onItemResponse(movieResponse.data!!)
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