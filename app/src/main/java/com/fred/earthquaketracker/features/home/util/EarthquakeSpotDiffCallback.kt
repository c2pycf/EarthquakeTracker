package com.fred.earthquaketracker.features.home.util

import androidx.recyclerview.widget.DiffUtil
import com.fred.earthquaketracker.features.home.models.EarthquakeSpotModel

/**
 * DiffUtil custom call back in order to compare items from old list and new list
 */

class EarthquakeSpotDiffCallback(
    private val oldList: List<EarthquakeSpotModel>?,
    private val newList: List<EarthquakeSpotModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList?.size ?: 0

    override fun getNewListSize(): Int = newList.size

    //No unique id, so assume item with same datetime lat, magnitude, lng, eqid is the same item
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList?.get(oldItemPosition)?.datetime == newList[newItemPosition].datetime &&
                oldList[oldItemPosition].lat == newList[newItemPosition].lat &&
                oldList[oldItemPosition].magnitude == newList[newItemPosition].magnitude &&
                oldList[oldItemPosition].lng == newList[newItemPosition].lng &&
                oldList[oldItemPosition].eqid == newList[newItemPosition].eqid &&
                oldList[oldItemPosition].depth == newList[newItemPosition].depth &&
                oldList[oldItemPosition].src == newList[newItemPosition].src &&
                oldList[oldItemPosition].id == newList[newItemPosition].id


    //Check display contents
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList?.get(oldItemPosition)?.datetime == newList[newItemPosition].datetime &&
                oldList[oldItemPosition].lat == newList[newItemPosition].lat &&
                oldList[oldItemPosition].magnitude == newList[newItemPosition].magnitude &&
                oldList[oldItemPosition].lng == newList[newItemPosition].lng
        //oldList?.get(oldItemPosition) == newList[newItemPosition]
}