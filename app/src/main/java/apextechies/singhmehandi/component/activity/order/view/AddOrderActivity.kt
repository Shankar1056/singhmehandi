package apextechies.singhmehandi.component.activity.order.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.*
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.ItemListData
import apextechies.singhmehandi.component.activity.order.presenter.AddOrderPresenter
import apextechies.singhmehandi.component.activity.order.view.adapter.OrderItemListAdapter
import kotlinx.android.synthetic.main.activity_order_add.*

class AddOrderActivity : AppCompatActivity(), AddOrderView, AdapterView.OnItemSelectedListener {

    var presenter = AddOrderPresenter()
    var radioorderType: RadioButton? = null
    var selectedArea: String? = null
    var itemList: ArrayList<ItemListData>? = null
    var adapterItemName = ArrayList<String>()
    var adapterItemCode = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_add)

        presenter.AddOrderPresenter(this, this)
        presenter.onCreated()

    }

    override fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        salesManET.setText(intent.getStringExtra("salesman"))
        shopET.setText(intent.getStringExtra("shop"))
        routeET.setText(intent.getStringExtra("trnum"))

        itemSpinnerRV.layoutManager = LinearLayoutManager(this)
        routeName.setOnItemSelectedListener(this)
        presenter.getOrderItemList()
        save.setOnClickListener {
            /*presenter.validateField(
                routeET.text.toString().trim(),
                shopET.text.toString().trim(),
                salesManET.text.toString().trim(),
                adapterItemName,
                adapterItemCode,
                radioorderType!!.getText().toString(),
                quantityET.text.toString().trim(),
                narrationET.text.toString().trim()
            )*/
        }

        radioGrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(
                    applicationContext, " On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT
                ).show()
                if (radio.text.equals(resources.getString(R.string.order))) {
                    routeName.visibility = View.VISIBLE
                    quantityET.visibility = View.VISIBLE
                    description.visibility = View.VISIBLE
                    itemSpinnerRV.visibility = View.VISIBLE
                    quantity.visibility = View.VISIBLE

                } else {
                    routeName.visibility = View.GONE
                    quantityET.visibility = View.GONE
                    description.visibility = View.GONE
                    itemSpinnerRV.visibility = View.GONE
                    quantity.visibility = View.GONE
                }
            })
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedArea = routeName.selectedItem.toString()
        if (adapterItemName.size > 0) {
            var isFound = false
            for (i in 0 until adapterItemName.size) {
                if (adapterItemName[i].equals(itemList!![position].cat)) {
                    Toast.makeText(this@AddOrderActivity, "This list is already added", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    isFound = true
                }
            }
            if (isFound) {
                itemList!![position].cat?.let { adapterItemName.add(it) }
                itemList!![position].code?.let { adapterItemCode.add(it) }
            }
        } else {
            itemList!![position].cat?.let { adapterItemName.add(it) }
            itemList!![position].code?.let { adapterItemCode.add(it) }
        }

        itemSpinnerRV.adapter = OrderItemListAdapter(this, adapterItemName, object :
            OrderItemListAdapter.OrderItemClickListener {
            override fun onClick(pos: Int) {
                adapterItemName.removeAt(pos)
                adapterItemCode.removeAt(pos)
            }

        })

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


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
        itemList = list
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, movieResponse)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        routeName.setAdapter(aa)
    }

}