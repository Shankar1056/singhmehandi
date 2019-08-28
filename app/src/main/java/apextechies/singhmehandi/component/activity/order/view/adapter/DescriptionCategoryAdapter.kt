package apextechies.singhmehandi.component.activity.order.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.ItemListData

class DescriptionCategoryAdapter(val list: ArrayList<ItemListData>, val listener: OrderItemClickListener):
    RecyclerView.Adapter<DescriptionCategoryAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_description, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.catName.setText(list[position].description)
        holder.itemView.setOnClickListener {
            listener.onClick(list[position].description!!, list[position].code!!)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catName: TextView = itemView.findViewById(R.id.catName)
    }

    interface OrderItemClickListener {
        fun onClick(name: String, id: String)
    }
}