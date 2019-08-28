package apextechies.singhmehandi.component.activity.order.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import apextechies.singhmehandi.R
import kotlinx.android.synthetic.main.activity_description_category.*
import kotlinx.android.synthetic.main.activity_description_category.titleTV
import kotlinx.android.synthetic.main.activity_description_category.toolbar

class DiscriptionCategoryActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_category)
        initViews()
        setStatePageAdapter()
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // setAdapter();


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                //   viewPager.notifyAll();
            }
        })

    }

    private fun  initViews(){

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        titleTV.setText("Description List")

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setStatePageAdapter(){
        val myViewPageStateAdapter:MyViewPageStateAdapter = MyViewPageStateAdapter(supportFragmentManager)
        myViewPageStateAdapter.addFragment(CosmeticsFragment(),"Cosmetics")
        myViewPageStateAdapter.addFragment(FinishGoodsFragment(),"FINISH GOODS")
        myViewPageStateAdapter.addFragment(MehandiConeFragment(),"Mehandi Cone")
        myViewPageStateAdapter.addFragment(PouchesFragment(),"Pouches")
        myViewPageStateAdapter.addFragment(RawMeterialFragment(),"Raw Material")
        pager.adapter=myViewPageStateAdapter
        tabs.setupWithViewPager(pager,true)

    }
    class MyViewPageStateAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm){
        val fragmentList:MutableList<Fragment> = ArrayList<Fragment>()
        val fragmentTitleList:MutableList<String> = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList.get(position)
        }

        fun addFragment(fragment:Fragment,title:String){
            fragmentList.add(fragment)
            fragmentTitleList.add(title)

        }
    }
}