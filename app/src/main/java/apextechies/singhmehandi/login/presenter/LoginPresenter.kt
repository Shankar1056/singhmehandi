package apextechies.singhmehandi.login.presenter

import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.login.model.LoginModel
import apextechies.singhmehandi.login.model.LoginModelVerifyModel
import apextechies.singhmehandi.login.model.OtpRequest
import apextechies.singhmehandi.login.model.OtpValidate
import apextechies.singhmehandi.login.virew.LoginView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


object LoginPresenter {
    var loginView: LoginView? = null
    private val TAG = "LoginPresenter"
    private var userId: String? = null
    private var otp: String? = null
    private var loginModel: LoginModel? = null

    fun LoginPresenter(loginView: LoginView) {
        this.loginView = loginView
    }

    fun getOtp(userId: String) {

        if (TextUtils.isEmpty(userId)) {
            loginView!!.emptyField("Invalid User Id")
        } else {
            LoginPresenter.userId = userId
            loginView!!.showProgress()
            getOtpFromServer()
        }
    }

    private fun getOtpFromServer() {
        getOtpObservable.subscribeWith(getOtpOobserver)
    }

    private fun verifyOtpFromServer() {
        verifyOtpObservable.subscribeWith(verifyOtpObserver)
    }

    fun onViewCreated() {
        loginView!!.showGetOtpView()
    }

    fun onOtpReceived() {
        loginView!!.showVerifyView()
    }

    fun verifyOtp(otp: String) {
        if (TextUtils.isEmpty(otp)) {
            loginView!!.emptyField("Invalid OTP")
        } else {
            LoginPresenter.otp = otp
            loginView!!.showProgress()
            verifyOtpFromServer()
        }

    }

    val getOtpObservable: Observable<LoginModel>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .userLogin(
                OtpRequest(
                    Constants.TOKEN,
                    Constants.LOGIN,
                    userId
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val getOtpOobserver: DisposableObserver<LoginModel>
        get() = object : DisposableObserver<LoginModel>() {

            override fun onNext(@NonNull movieResponse: LoginModel) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)){
                    loginView!!.invalidUser()
                }else {
                    loginModel = movieResponse
                    loginView!!.receivedOTP(movieResponse)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                loginView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                loginView!!.hideProgress()
            }
        }


    val verifyOtpObservable: Observable<LoginModelVerifyModel>
        get() = NetworkClient.getRetrofit().create(NetworkInterface::class.java)
            .verifyOtp(
                OtpValidate(
                    Constants.TOKEN,
                    Constants.VALIDATE,
                    loginModel!!.mobile,
                    otp
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val verifyOtpObserver: DisposableObserver<LoginModelVerifyModel>
        get() = object : DisposableObserver<LoginModelVerifyModel>() {

            override fun onNext(@NonNull movieResponse: LoginModelVerifyModel) {
                Log.d(TAG, "OnNext$movieResponse")
                if (movieResponse.status.equals(Constants.FAIL)){
                    loginView!!.invalidOtp()
                }else {
                    loginView!!.loginSuccess(movieResponse)
                }

            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                loginView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                loginView!!.hideProgress()
            }
        }


}