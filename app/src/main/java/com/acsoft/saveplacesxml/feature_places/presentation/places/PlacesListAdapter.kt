package com.acsoft.saveplacesxml.feature_places.presentation.places

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.acsoft.saveplacesxml.R
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
            binding.ivShare.setOnClickListener {
                shareLocation(context,item)
            }
        }
    }

    private fun shareDataIntent(context: Context, place: Place) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE,"Titulo: ${place.title} ")
            putExtra(Intent.EXTRA_TEXT,"Descripción: ${place.description} con coordenadas: ${place.latitude} / ${place.longitude}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent,"Save Places")
        startActivity(context,shareIntent,null)
    }

    private fun shareLocation(context: Context, place: Place) {
        val gmmIntentUri = Uri.parse("geo:${place.latitude},${place.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(context,mapIntent,null)
    }
}