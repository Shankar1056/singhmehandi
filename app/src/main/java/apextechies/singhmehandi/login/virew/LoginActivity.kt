package apextechies.singhmehandi.login.virew

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import apextechies.singhmehandi.MainActivity
import apextechies.singhmehandi.R
import apextechies.singhmehandi.login.model.LoginModel
import apextechies.singhmehandi.login.model.LoginModelVerifyModel
import apextechies.singhmehandi.login.presenter.LoginPresenter
import apextechies.singhmehandi.util.Utils
import com.stfalcon.smsverifycatcher.OnSmsCatchListener
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.activity_getotp.*
import kotlinx.android.synthetic.main.activity_getotp.view.*


class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    var smsVerifyCatcher: SmsVerifyCatcher? = null

    var loginPresentor = LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getotp)
        loginPresentor!!.LoginPresenter(this)
        smsVerifyCatcher = SmsVerifyCatcher(this,
            OnSmsCatchListener { message ->
                // val code = parseCode(message)//Parse verification code
                otpET.setText(message)//set code in edit text
            })
        loginPresentor.onViewCreated()

    }

    override fun showGetOtpView() {
        getOtpBTN.text = resources.getString(R.string.title_get_otp)
        verifyLayout.visibility = View.GONE
        userIdET.visibility = View.VISIBLE
        getOtpBTN.setOnClickListener(this)
    }

    override fun showVerifyView() {
        getOtpBTN.text = resources.getString(R.string.title_verify_otp)
        verifyLayout.visibility = View.VISIBLE
        userIdET.visibility = View.GONE
        changeMobile.setOnClickListener(this)
        resendOTP.setOnClickListener(this)
    }

    override fun showProgress() {
        progressAVL.show()
    }

    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun receivedOTP(movieResponse: LoginModel) {
        System.out.print(movieResponse)
        loginPresentor.onOtpReceived()

    }

    override fun verifyOTP() {
        loginPresentor!!.getOtp(userIdET.text.toString().trim())
    }

    override fun emptyField(errorMessage: String) {
        Utils.showAlertDialog(
            this, errorMessage,
            resources.getString(R.string.okBtm),
            "",
            ""
        )
    }

    override fun invalidUser() {
        Utils.showAlertDialog(
            this, resources.getString(R.string.invalid_user),
            resources.getString(R.string.okBtm),
            "",
            ""
        )
    }

    override fun invalidOtp() {
        Utils.showAlertDialog(
            this, resources.getString(R.string.invalid_otp),
            resources.getString(R.string.okBtm),
            "",
            ""
        )

    }

    override fun loginSuccess(movieResponse: LoginModelVerifyModel) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.changeMobile -> loginPresentor.onViewCreated()
            R.id.getOtpBTN -> {
                if (getOtpBTN.text.toString().trim().equals(resources.getString(R.string.title_get_otp))){
                    loginPresentor!!.getOtp(userIdET.text.toString().trim())
                } else{
                    loginPresentor!!.verifyOtp(otpET.text.toString().trim())
                }

            }
        }
    }

    override fun displayError(errore: String) {

    }

    override fun onStart() {
        super.onStart()
        smsVerifyCatcher!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        smsVerifyCatcher!!.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        smsVerifyCatcher!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}