package kg.tutorialapp.myweather.ui.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.tutorialapp.myweather.models.DailyForeCast

class DailyForeCastAdapter: RecyclerView.Adapter<DailyForeCastVH>(){
    private val items= arrayListOf<DailyForeCast>()
    fun setItem(newItems:List<DailyForeCast>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForeCastVH {
        return DailyForeCastVH.create(parent)
    }


    override fun onBindViewHolder(holder: DailyForeCastVH, position: Int) {
        holder.bind(items[position])
    }


    override fun getItemCount()=items.count()
}
