package apextechies.singhmehandi.component.activity.order.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.*
import apextechies.singhmehandi.AppController
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.AuthorizedRetailerDataList
import apextechies.singhmehandi.component.activity.order.model.ItemListData
import apextechies.singhmehandi.component.activity.order.presenter.AddOrderPresenter
import apextechies.singhmehandi.component.activity.order.view.adapter.OrderItemListAdapter
import apextechies.singhmehandi.component.activity.shop.model.RouteListdata
import apextechies.singhmehandi.util.ClsGeneral
import apextechies.singhmehandi.util.Constants
import kotlinx.android.synthetic.main.activity_order_add.*


class AddOrderActivity : AppCompatActivity(), AddOrderView, AdapterView.OnItemSelectedListener {

    var presenter = AddOrderPresenter()
    var radioType: String? = null
    var selectedArea: String? = null
    var itemList: ArrayList<ItemListData>? = null
    var descriptionName = ArrayList<String>()
    var descriptionId = ArrayList<String>()
    var quantityLst = ArrayList<String>()
    var orderAdapter: OrderItemListAdapter? = null
    var orderRouteList = ArrayList<RouteListdata>()
    var routeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_add)
        titleTV.setText(intent.getStringExtra("title"))
        presenter.AddOrderPresenter(this, this)
        presenter.onCreated()

    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        salesManET.setText(ClsGeneral.getPreferences(AppController.getInstance(), Constants.USER))

        itemSpinnerRV.layoutManager = LinearLayoutManager(this)
        routeName.setOnClickListener {
            startActivityForResult(
                Intent(
                    this@AddOrderActivity, DiscriptionCategoryActivity::class.java
                ), 2
            )
        }
        routeET.setOnItemSelectedListener(this)

        presenter.getAuthorisedRoute()
        save.setOnClickListener {
            quantityLst = orderAdapter!!.getQuantityList()
            if (quantityLst.size == descriptionName.size) {
                if (radioType == null || radioType.equals("")) {
                    radioType = resources.getString(R.string.order)
                }
                presenter.validateField(
                    routeET.selectedItem.toString().trim(),
                    shopET.selectedItem.toString().trim(),
                    salesManET.text.toString().trim(),
                    descriptionName,
                    descriptionId,
                    radioType.toString(),
                    quantityLst,
                    narrationET.text.toString().trim()
                )
            }
        }

        radioGrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                radioType = radio.text as String?
                Toast.makeText(
                    applicationContext, " On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT
                ).show()
                if (radio.text.equals(resources.getString(R.string.order))) {
                    routeName.visibility = View.VISIBLE
                    description.visibility = View.VISIBLE
                    itemSpinnerRV.visibility = View.VISIBLE

                } else {
                    routeName.visibility = View.GONE
                    description.visibility = View.GONE
                    itemSpinnerRV.visibility = View.GONE
                }
            })


        orderAdapter = OrderItemListAdapter(this, descriptionName, object :
            OrderItemListAdapter.OrderItemClickListener {
            override fun noQuantityError() {
                Toast.makeText(this@AddOrderActivity, "Enter quantity please", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onClick(pos: Int) {
                descriptionName.removeAt(pos)
                descriptionId.removeAt(pos)
            }

        })
        itemSpinnerRV.adapter = orderAdapter


        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            val description_name = data!!.getStringExtra("description_name")
            val description_id = data!!.getStringExtra("description_id")

            if (descriptionName.size > 0) {
                var isFound = false
                for (i in 0 until descriptionName.size) {
                    if (descriptionName[i].equals(description_name)) {
                        Toast.makeText(
                            this@AddOrderActivity,
                            "This descriptionist is already added",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return
                    } else {
                        isFound = true
                    }
                }
                if (isFound) {
                    descriptionName.add(description_name)
                    descriptionId.add(description_id)
                }
            } else {
                descriptionName.add(description_name)
                descriptionId.add(description_id)
            }

            orderAdapter?.notifyDataSetChanged()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.getId()) {
            R.id.routeET -> {
                for (i in 0 until orderRouteList.size) {
                    if (routeET.selectedItem.toString().equals(orderRouteList[i].routename)) {
                        routeId = orderRouteList[i].routecode
                        break
                    }
                }
                presenter.getAuthorisedShop(routeET.selectedItem.toString(), routeId)
            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onAuthorisedDealerOrderResponse(message: ArrayList<RouteListdata>?) {
        Log.e("Order Presenter", "onAuthorisedDealerOrderResponse")
        if (message != null) {
            orderRouteList = message
            if (intent.getStringExtra("trnum") != null && intent.getStringExtra("trnum").length > 0) {
                presenter.onAuthorizedRouteReceived(message, intent.getStringExtra("trnum"))
            }
            presenter.onAuthorizedRouteReceived(message, "")
        }

    }

    override fun addRouteNameListInSpinner(routeNameList: ArrayList<String>, pos: Int) {
        Log.e("Order Presenter", "addRouteNameListInSpinner")
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, routeNameList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        routeET.setAdapter(aa)
        routeET.setSelection(pos)

    }


    override fun onAuthorisedShopResponse(data: ArrayList<AuthorizedRetailerDataList>?) {
        presenter.getOnlyShop(intent.getStringExtra("shop"), data)
    }

    override fun AuthorizedShopWithSelectedPosition(shopList: ArrayList<String>, pos: Int) {
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, shopList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shopET.setAdapter(aa)
        shopET.setSelection(pos)
    }


    override fun showProgress() {
        progressAVL.show()
    }

    override fun hideProgress() {
        progressAVL.hide()
    }

    override fun showEmptyStringMessage(errorMessage: String) {
        Toast.makeText(this@AddOrderActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun noDataAvailable() {
    }

    override fun onItemResponse(movieResponse: ArrayList<String>, list: ArrayList<ItemListData>) {
        /* itemList = list
         val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, movieResponse)
         aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
         routeName.setAdapter(aa)*/
    }

    override fun displayError(errorMessage: String) {
        Toast.makeText(this, "" + String, Toast.LENGTH_SHORT).show()

    }

    override fun onaddOrderResponse(data: String) {
        Toast.makeText(this, "Shop Added", Toast.LENGTH_SHORT).show()
    }

    override fun onCompleted() {
        Toast.makeText(this, "Shop Added", Toast.LENGTH_SHORT).show()
        finish()
    }


}