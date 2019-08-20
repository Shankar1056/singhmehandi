package apextechies.singhmehandi.component.activity.shop.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse
import apextechies.singhmehandi.component.activity.shop.model.*
import apextechies.singhmehandi.component.activity.shop.preserter.AddShopPresenter
import kotlinx.android.synthetic.main.activity_shop_add.*


class AddShopActivity : AppCompatActivity(), AddShopView, AdapterView.OnItemSelectedListener {

    var addShopPresenter = AddShopPresenter
    var areaList = ArrayList<AreaListData>()
    var routeList = ArrayList<RouteListdata>()
    var shopType: String? = null
    var radioSexButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_add)
        addShopPresenter.AddShopPresenter(this, this)
        addShopPresenter.onCreated()
    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()

        }

        addShopPresenter.getAreaList()
        routeName.setOnItemSelectedListener(this)
        areaName.setOnItemSelectedListener(this)
        submit.setOnClickListener {
            val selectedId = radioGrp.getCheckedRadioButtonId()
            radioSexButton = findViewById(selectedId)
            addShopPresenter.onSubmitClick(
                areaName.selectedItem.toString(),
                areaCode.selectedItem.toString(),
                routeName.selectedItem.toString(),
                routeCode.selectedItem.toString(),
                shopNameET.text.toString(),
                placeET.text.toString(),
                mobileET.text.toString(),
                gstET.text.toString(),
                radioSexButton!!.getText().toString(),
                addressET.text.toString(),
                panTinET.text.toString(),
                noteET.text.toString()
            )
        }
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.getId()) {
            R.id.areaName -> {
                addShopPresenter.areaSelected(areaList, position)
                addShopPresenter.getRouteList(
                    areaList?.get(position)!!.areaname!!,
                    areaList?.get(position)!!.areacode!!
                )
            }

            R.id.routeName -> {
                addShopPresenter.routeSelected(routeList, position)
            }
        }
    }

    override fun displayError(error: String) {
    }

    override fun hideProgress() {
    }

    override fun onRouteResponse(movieResponse: RouteListResponse) {
        routeList.clear()
        var routeListdata = RouteListdata()
        routeListdata.routename = resources.getString(R.string.selectroutename)
        routeListdata.routecode = resources.getString(R.string.selectroutecode)
        routeList.add(routeListdata)
        movieResponse.data?.let { routeList?.addAll(it) }
        addShopPresenter.onRouteResponceReceived(routeList, 0)
    }

    override fun onRouteEmptyResponse() {
        routeList.clear()
        var routeListdata = RouteListdata()
        routeListdata.routename = resources.getString(R.string.selectroutename)
        routeListdata.routecode = resources.getString(R.string.selectroutecode)
        routeList.add(routeListdata)
        addShopPresenter.onRouteResponceReceived(routeList, 0)
    }

    override fun addRouteNameListInSpinner(routeNameList: ArrayList<String>, defaultPosition: Int) {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, routeNameList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        routeName.setAdapter(aa)
        routeName.setSelection(defaultPosition)

    }

    override fun addRouteCodeListInSpinner(routeCodeList: ArrayList<String>, defaultPosition: Int) {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, routeCodeList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        routeCode.setAdapter(aa)
        routeCode.setSelection(defaultPosition)
    }


    override fun onAreaResponse(movieResponse: AreaListResponse) {
        areaList.clear()
        var areaListData = AreaListData()
        areaListData.areaname = resources.getString(R.string.selectareaname)
        areaListData.areacode = resources.getString(R.string.selectareacode)
        areaList?.add(areaListData)
        movieResponse!!.data?.let { areaList?.addAll(it) }

        addShopPresenter.onAreaResponceReceived(areaList, 0)
    }

    override fun addAreaNameListInSpinner(areaList: ArrayList<String>, selectedposition: Int) {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaName.setAdapter(aa)
        areaName.setSelection(selectedposition)
    }

    override fun addAreaCodeListInSpinner(areaList: ArrayList<String>, selectedposition: Int) {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaCode.setAdapter(aa)
        areaCode.setSelection(selectedposition)
    }

    override fun noDataAvailable() {
    }

    override fun onDistributerResponse(movieResponse: DistributorListResponse) {
    }

    override fun onItemResponse(movieResponse: ItemListResponse) {
    }

    override fun emptyShopValue() {

        Toast.makeText(this@AddShopActivity, getString(R.string.title_empty_shop_value), Toast.LENGTH_SHORT)
            .show()
    }

    override fun emptyPlaceValue() {
        Toast.makeText(this, resources.getString(R.string.title_empty_place_value), Toast.LENGTH_SHORT).show()
    }

    override fun emptyMobileValue() {
        Toast.makeText(this@AddShopActivity, "hi", Toast.LENGTH_SHORT).show()
    }

    override fun invalidMobileValue() {
        Toast.makeText(this@AddShopActivity, getString(R.string.title_invalid_mobile_value), Toast.LENGTH_SHORT).show()
    }

    override fun emptyGstValue() {
        Toast.makeText(this@AddShopActivity, getString(R.string.title_empty_gst_value), Toast.LENGTH_SHORT).show()
    }

    override fun emptyAddressValue() {
        Toast.makeText(this@AddShopActivity, getString(R.string.title_empty_shop_address_value), Toast.LENGTH_SHORT)
            .show()
    }

    override fun emptyPinTinValue() {
        Toast.makeText(this@AddShopActivity, getString(R.string.title_empty_pintin_value), Toast.LENGTH_SHORT).show()
    }

    override fun selectAreaName() {
        Toast.makeText(this@AddShopActivity, getString(R.string.selectareaname), Toast.LENGTH_SHORT).show()
    }

    override fun selectAreaCode() {
        Toast.makeText(this@AddShopActivity, getString(R.string.selectareacode), Toast.LENGTH_SHORT).show()
    }

    override fun selectRouteName() {
        Toast.makeText(this@AddShopActivity, getString(R.string.selectroutename), Toast.LENGTH_SHORT).show()
    }

    override fun selectRouteCode() {
        Toast.makeText(this@AddShopActivity, getString(R.string.selectroutecode), Toast.LENGTH_SHORT).show()
    }

    override fun displaySavedShopMessage(movieResponse: SaveShopResponse) {
        Toast.makeText(this@AddShopActivity, movieResponse.message.toString(), Toast.LENGTH_SHORT).show()
        finish()
    }

}