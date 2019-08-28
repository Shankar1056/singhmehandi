package apextechies.singhmehandi.component.activity.order.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.ItemListData
import apextechies.singhmehandi.component.activity.order.presenter.DescriptionCategoryPresenter
import apextechies.singhmehandi.component.activity.order.view.adapter.DescriptionCategoryAdapter
import apextechies.singhmehandi.util.Constants
import kotlinx.android.synthetic.main.fragment_mehandi.*
import java.util.ArrayList

class PouchesFragment : Fragment() , DescriptionCategoryView {

    var presenter = DescriptionCategoryPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mehandi, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionRV.layoutManager = LinearLayoutManager(activity)
        activity?.let { presenter.DescriptionCategoryPresenter(this, it) }

        presenter.getDescriptionList(Constants.POUCHES)
    }

    override fun noDataAvailable() {}

    override fun onItemResponse(
        data: ArrayList<ItemListData>
    ) {
        descriptionRV.adapter = DescriptionCategoryAdapter(data, object : DescriptionCategoryAdapter.OrderItemClickListener {
            override fun onClick(description: String, id: String) {

                val intent = Intent()
                intent.putExtra("description_name", description)
                intent.putExtra("description_id", id)
                activity?.setResult(2, intent)
                activity?.finish()
            }

        })
    }

    override fun displayError(s: String) {}

    override fun hideProgress() {}

}