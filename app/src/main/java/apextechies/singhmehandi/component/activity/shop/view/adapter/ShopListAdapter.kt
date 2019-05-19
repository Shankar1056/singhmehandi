package apextechies.singhmehandi.component.activity.shop.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.shop.model.ShopListData
import com.bumptech.glide.Glide

class ShopListAdapter(private val context: Context, private val shopList: ArrayList<ShopListData>) :
    RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_shop_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = shopList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.shopName.setText(shopList[position].shop)

        holder.itemView.setOnClickListener(View.OnClickListener {
            //clickListener.onClick(position)
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopName: TextView = itemView.findViewById(R.id.shopName)
    }
}
