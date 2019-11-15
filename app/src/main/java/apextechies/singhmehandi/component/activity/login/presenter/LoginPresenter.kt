package apextechies.singhmehandi.component.activity.login.presenter

import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import apextechies.singhmehandi.AppController
import apextechies.singhmehandi.component.activity.login.model.LoginModel
import apextechies.singhmehandi.component.activity.login.model.LoginModelVerifyModel
import apextechies.singhmehandi.component.activity.login.model.OtpRequest
import apextechies.singhmehandi.component.activity.login.model.OtpValidate
import apextechies.singhmehandi.component.activity.login.virew.LoginView
import apextechies.singhmehandi.network.NetworkClient
import apextechies.singhmehandi.network.NetworkInterface
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern


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

    fun parseCode(message: String?) {
        val p = Pattern.compile("\\b\\d{6}\\b")
        val m = p.matcher(message)
        var code = ""
        while (m.find()) {
            code = m.group(0)
        }
        loginView!!.setOtp(code)
    }

    private val getOtpObservable: Observable<LoginModel>
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
                    Constants.VALIDATE,
                    loginModel?.data?.get(0)?.mobile,
                    otp,
                    userId
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
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.DB, movieResponse.data?.get(0)?.db)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.USER, movieResponse.data?.get(0)?.user)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.CLIENT, movieResponse.data?.get(0)?.client)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.PHONE, movieResponse.data?.get(0)?.phone)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.REGION, movieResponse.data?.get(0)?.region)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.SUPERSTOCKIST, movieResponse.data?.get(0)?.superstockist)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.STATE, movieResponse.data?.get(0)?.state)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.EMPLOYEENAME, movieResponse.data?.get(0)?.employeename)
                    ClsGeneral.setPreferences(AppController.getInstance(), Constants.EMPLOYEEID, movieResponse.data?.get(0)?.employeeid)
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