package apextechies.singhmehandi.component.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import apextechies.singhmehandi.MainActivity
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.login.virew.LoginActivity
import apextechies.singhmehandi.component.activity.shop.view.ShopActivity
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            /*if (ClsGeneral.getStrPreferences(Constants.USER).equals("")) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }*/
            startActivity(Intent(this, ShopActivity::class.java))
            finish()
        }, 3000)
    }
}