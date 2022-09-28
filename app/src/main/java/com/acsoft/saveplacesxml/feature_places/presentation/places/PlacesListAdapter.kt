package com.acsoft.saveplacesxml.feature_places.presentation.places

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acsoft.saveplacesxml.core.BaseViewHolder
import com.acsoft.saveplacesxml.databinding.PlaceItemLayoutBinding
import com.acsoft.saveplacesxml.feature_places.domain.model.Place

class PlacesListAdapter() : RecyclerView.Adapter<BaseViewHolder<*>>()  {

    private var placeList = listOf<Place>()

    fun setPlaceList(placeList: List<Place>) {
        this.placeList = placeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PlaceItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is PlaceViewHolder -> {
                holder.bind(placeList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    private inner class PlaceViewHolder(val binding : PlaceItemLayoutBinding, val context: Context) :
        BaseViewHolder<Place>(binding.root) {
        override fun bind(item: Place) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }
}