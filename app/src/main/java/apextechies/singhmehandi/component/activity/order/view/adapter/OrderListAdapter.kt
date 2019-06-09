package apextechies.singhmehandi.component.activity.order.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderListData
import apextechies.singhmehandi.component.activity.shop.model.ShopListData
import apextechies.singhmehandi.component.activity.shop.view.adapter.ShopListAdapter

class OrderListAdapter (private val context: Context, private val shopList: ArrayList<OrderListData>, val listener: OrderListClick) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_shop_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shopList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.shopName.setText(shopList[position].shop)
        holder.locationName.setText(shopList[position].distributor)

        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.onItemClick(position)
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopName: TextView = itemView.findViewById(R.id.shopName)
        val locationName: TextView = itemView.findViewById(R.id.locationName)
    }

    interface OrderListClick{
        fun onItemClick(pos: Int)
    }
}
