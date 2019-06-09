package apextechies.singhmehandi.component.activity.order.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.singhmehandi.R

class OrderItemListAdapter (private val context: Context, private val shopList: ArrayList<String>, val listener : OrderItemClickListener) :
    RecyclerView.Adapter<OrderItemListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_order_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shopList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.shopName.setText(shopList[position])

        holder.delete.setOnClickListener(View.OnClickListener {
            listener!!.onClick(position)
            notifyDataSetChanged()
        })

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopName: TextView = itemView.findViewById(R.id.shopItem)
        val delete: ImageView = itemView.findViewById(R.id.delete)
    }

    interface OrderItemClickListener {
        fun onClick(pos: Int)
    }
}