package apextechies.singhmehandi.component.activity.order.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderDescriptionQuantityModel


class OrderItemListAdapter(
    private val context: Context,
    private val shopList: ArrayList<OrderDescriptionQuantityModel>,
    private val quan_list: ArrayList<String>,
    val listener: OrderItemClickListener
) :
    RecyclerView.Adapter<OrderItemListAdapter.ViewHolder>() {
    var holder: ViewHolder? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_order_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shopList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.holder = holder
        holder.shopName.setText(shopList[position].descriptionName)
       // if (shopList.size > position)
            holder.quantity.setText(shopList[position].Quantity)


        holder.quantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopList.get(position).Quantity = (s.toString())
            }
        })

        holder.delete.setOnClickListener {
            holder!!.quantity.setText("")
            listener!!.onClick(position)
            notifyDataSetChanged()
        }
    }

    fun getQuantityList(): ArrayList<String> {
        var quantityList = ArrayList<String>()
        for (i in 0 until shopList.size) {
            if (holder!!.quantity.text.toString().trim().equals("")) {
                listener.noQuantityError()
                return quantityList
            } else {
                quantityList.add(holder!!.quantity.getText().toString())
            }
        }
        return quantityList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopName: TextView = itemView.findViewById(R.id.shopItem)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        val quantity: EditText = itemView.findViewById(R.id.quantity)
    }

    interface OrderItemClickListener {
        fun onClick(pos: Int)
        fun noQuantityError()
    }


}
