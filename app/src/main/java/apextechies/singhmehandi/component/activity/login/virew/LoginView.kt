package apextechies.singhmehandi.component.activity.login.virew

import apextechies.singhmehandi.component.activity.login.model.LoginModel
import apextechies.singhmehandi.component.activity.login.model.LoginModelVerifyModel

interface LoginView {

    fun showProgress()

    fun hideProgress()

    fun receivedOTP(movieResponse: LoginModel)

    fun verifyOTP()

    fun displayError(errore: String)

    fun showGetOtpView()

    fun showVerifyView()

    fun emptyField(errorMessage: String)

    fun loginSuccess(movieResponse: LoginModelVerifyModel)

    fun invalidUser()

    fun invalidOtp()
    fun setOtp(code: String)
}