package apextechies.singhmehandi.component.activity.order.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderListData

class OrderDetaildItemAdapter(val list: OrderListData) :
    RecyclerView.Adapter<OrderDetaildItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_order_details, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.quantity!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv__item.text = list.item?.get(position)!!.item
        holder.tv_quantity.text = list.quantity?.get(position)!!.quantity

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv__item: TextView = itemView.findViewById(R.id.tv__item)
        val tv_quantity: TextView = itemView.findViewById(R.id.tv_quantity)
    }


}