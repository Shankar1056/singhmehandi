package apextechies.singhmehandi.component.activity.order.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apextechies.singhmehandi.component.activity.order.model.OrderListData


class OrderListAdapter(
    private val context: Context,
    private var shopList: ArrayList<OrderListData>,
    val listener: OrderListClick
) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    private var copyList = ArrayList<OrderListData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(apextechies.singhmehandi.R.layout.item_order_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shopList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.shopName.text = shopList[position].shop
        holder.locationName.text = shopList[position].distributor

        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.onItemClick(position)
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopName: TextView = itemView.findViewById(apextechies.singhmehandi.R.id.shopName)
        val locationName: TextView = itemView.findViewById(apextechies.singhmehandi.R.id.locationName)
    }

    init {
        copyList = shopList
    }

    interface OrderListClick {
        fun onItemClick(pos: Int)
    }

    fun filter(queryText: String) {
            shopList = if (queryText.isEmpty()) {
                copyList
            } else {
                val filteredList = ArrayList<OrderListData>()
                copyList.forEach {
                    if (it.shop!!.toLowerCase().contains(queryText.toLowerCase())) {
                        filteredList.add(it)
                    }
                }
                filteredList
            }

            notifyDataSetChanged()
        }

}
