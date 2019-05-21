package apextechies.singhmehandi.component.activity.order.presenter

import android.support.annotation.NonNull
import android.util.Log
import apextechies.singhmehandi.component.activity.CommonRequest
import apextechies.singhmehandi.component.activity.CommonRequestWithDate
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.order.view.OrderView
import apextechies.singhmehandi.component.activity.shop.model.*
import apextechies.singhmehandi.component.activity.shop.preserter.ShopPresenter.view
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

object OrderPresenter {
    val TAG = "OrderPresenter"
    var orderView: OrderView? = null

    fun OrderPresenter(orderView: OrderView) {
        this.orderView = orderView
    }

    fun getAreaList() {
        getAreaObservable.subscribeWith(getAreaObserver)
    }

    fun getRouteList(areaName: String, areaCode: String) {
        getRouteObservable.subscribeWith(getRouteObserver)
    }

    fun getDistributorList() {
        getDistributorObservable.subscribeWith(getDistributorObserver)
    }

    fun getRetailerList() {
        getItemObservable.subscribeWith(getItemObserver)
    }


    val getAreaObservable: Observable<AreaListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getAreaList(
                CommonRequest(
                    "smtest1",
                    "singhsatrangnew",
                    "North Karnataka",
                    "DHANPAL JI JAIN",
                    "Karnataka",
                    "Test SM",
                    "81"
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getAreaObserver: DisposableObserver<AreaListResponse>
        get() = object : DisposableObserver<AreaListResponse>() {

            override fun onNext(@NonNull movieResponse: AreaListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    orderView!!.noDataAvailable()
                } else {
                    orderView!!.onAreaResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                orderView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                orderView!!.hideProgress()
            }
        }


    val getRouteObservable: Observable<RouteListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getRouteList(
                RouteListRequest(
                    "01/16/2019",
                    "5/16/2019",
                    "smtest1",
                    "singhsatrangnew",
                    "North Karnataka",
                    "DHANPAL JI JAIN",
                    "Karnataka",
                    "Test SM",
                    "81"
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getRouteObserver: DisposableObserver<RouteListResponse>
        get() = object : DisposableObserver<RouteListResponse>() {

            override fun onNext(@NonNull movieResponse: RouteListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    orderView!!.noDataAvailable()
                } else {
                    orderView!!.onRouteResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                orderView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                orderView!!.hideProgress()
            }
        }


    val getDistributorObservable: Observable<DistributorListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getDistributorList(
                CommonRequest(
                    "smtest1",
                    "singhsatrangnew",
                    "North Karnataka",
                    "DHANPAL JI JAIN",
                    "Karnataka",
                    "Test SM",
                    "81"
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getDistributorObserver: DisposableObserver<DistributorListResponse>
        get() = object : DisposableObserver<DistributorListResponse>() {

            override fun onNext(@NonNull movieResponse: DistributorListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    orderView!!.noDataAvailable()
                } else {
                    orderView!!.onDistributerResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                orderView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                orderView!!.hideProgress()
            }
        }


    val getItemObservable: Observable<ItemListResponse>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .getItemList(
                CommonRequest(
                    "smtest1",
                    "singhsatrangnew",
                    "North Karnataka",
                    "DHANPAL JI JAIN",
                    "Karnataka",
                    "Test SM",
                    "81"
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getItemObserver: DisposableObserver<ItemListResponse>
        get() = object : DisposableObserver<ItemListResponse>() {

            override fun onNext(@NonNull movieResponse: ItemListResponse) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)) {
                    orderView!!.noDataAvailable()
                } else {
                    orderView!!.onItemResponse(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                orderView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                orderView!!.hideProgress()
            }
        }

}