package apextechies.singhmehandi.component.activity.order.view.adapter

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
    private val shopList: ArrayList<OrderDescriptionQuantityModel>,
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
        holder.shopName.text = shopList[position].descriptionName
        // if (shopList.size > position)
        holder.quantity.setText(shopList[position].Quantity)


        /*holder.quantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopList[position].Quantity = (s.toString())
            }
        })*/

        holder.delete.setOnClickListener {
            // holder!!.quantity.setText("")
            listener!!.onClick(position)
            // notifyDataSetChanged()
        }
    }

    fun getQuantityList(): ArrayList<String> {
        var quantityList = ArrayList<String>()
        for (i in 0 until shopList.size) {
            if (holder!!.quantity.text.toString().trim() == "") {
                listener.noQuantityError()
                return quantityList
            } else {
                quantityList.add(shopList[i].Quantity.toString())
            }
        }
        return quantityList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var shopName: TextView = itemView.findViewById(R.id.shopItem)
        var delete: ImageView = itemView.findViewById(R.id.delete)
        var quantity: EditText = itemView.findViewById(R.id.quantity)

        init {

            quantity.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    shopList[adapterPosition].Quantity = (s.toString())
                }
            })
        }


    }

    /* internal class MyViewHolder(itemView: View) :
         RecyclerView.ViewHolder(itemView) {
         protected var editText: EditText

         init {
             editText = itemView.findViewById<View>(R.id.editid) as EditText
             editText.addTextChangedListener(object : TextWatcher {
                 override fun beforeTextChanged(
                     charSequence: CharSequence,
                     i: Int,
                     i1: Int,
                     i2: Int
                 ) {
                 }

                 override fun onTextChanged(
                     charSequence: CharSequence,
                     i: Int,
                     i1: Int,
                     i2: Int
                 ) {
                     editModelArrayList.get(adapterPosition)
                         .setEditTextValue(editText.text.toString())
                 }

                 override fun afterTextChanged(editable: Editable) {}
             })
         }
     }
    */
    interface OrderItemClickListener {
        fun onClick(pos: Int)
        fun noQuantityError()
    }


}
