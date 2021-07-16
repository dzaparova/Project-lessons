package kg.tutorialapp.myweather.ui.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.tutorialapp.myweather.models.HourlyForeCast

class HourlyForeCastAdapter: RecyclerView.Adapter<HourlyForeCastVH>() {
    private val items = arrayListOf<HourlyForeCast>()

    fun setItemsHourly(newItems: List<HourlyForeCast>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForeCastVH {
        return HourlyForeCastVH.create(parent)
    }


    override fun onBindViewHolder(holder: HourlyForeCastVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount()=items.count()

}
