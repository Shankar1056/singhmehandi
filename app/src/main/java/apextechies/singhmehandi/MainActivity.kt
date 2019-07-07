package apextechies.singhmehandi

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import apextechies.singhmehandi.component.activity.login.virew.LoginActivity
import apextechies.singhmehandi.component.activity.order.view.OrderListActivity
import apextechies.singhmehandi.component.activity.shop.view.ShopActivity
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        shopLL.setOnClickListener {
            startActivity(Intent(this@MainActivity, ShopActivity::class.java))
        }

        orderLL.setOnClickListener {
            startActivity(Intent(this@MainActivity, OrderListActivity::class.java))
        }
        logout.setOnClickListener {
            ClsGeneral.setPreferences(this@MainActivity, Constants.USER, "")
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}
