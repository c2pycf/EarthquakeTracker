/*
 * MIT License
 *
 * Copyright (c) 2021 Fang Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.fred.earthquaketracker.features.home.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fred.earthquaketracker.R
import com.fred.earthquaketracker.common.extensions.resolveColorReference
import com.fred.earthquaketracker.features.home.models.EarthquakeSpotModel
import com.fred.earthquaketracker.features.home.util.EarthquakeSpotDiffCallback
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_earthquake_spot.view.*
import kotlinx.android.synthetic.main.item_earthquake_spot.view.warning_iv
import kotlinx.android.synthetic.main.item_empty.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class EarthquakeSpotListAdapter(
    private val onClickListener: (EarthquakeSpotModel?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var earthquakeSpotList = mutableListOf<EarthquakeSpotModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_SPOT -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_earthquake_spot, parent, false).let {
                    return EarthquakeSpotViewHolder(it, onClickListener)
                }
            VIEW_TYPE_EMPHASIZE -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_earthquake_spot, parent, false).let {
                    return EarthquakeSpotViewHolder(it, onClickListener, true)
                }
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_empty, parent, false).let {
                    return GeneralEmptyListViewHolder(it)
                }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EarthquakeSpotViewHolder ->
                holder.onBindView(earthquakeSpotList.getOrNull(position))
            else -> Unit //Empty View Holder
        }
    }

    override fun getItemViewType(position: Int): Int {
        earthquakeSpotList.let {
            return when {
                it.isEmpty() -> VIEW_TYPE_EMPTY
                it[position].magnitude >= 8 -> VIEW_TYPE_EMPHASIZE
                else -> VIEW_TYPE_SPOT
            }
        }
    }


    override fun getItemCount(): Int =
        if (earthquakeSpotList.isEmpty()) DEFAULT_LIST_SIZE else earthquakeSpotList.size

    fun loadList(
        earthquakeSpots: List<EarthquakeSpotModel>,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        val mutex = Mutex()
//        if (earthquakeSpotList.isEmpty()) notifyItemChanged(0)
        val diffCallback = EarthquakeSpotDiffCallback(this.earthquakeSpotList, earthquakeSpots)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        lifecycleScope.launch(Dispatchers.IO) {
            mutex.withLock {
                this@EarthquakeSpotListAdapter.earthquakeSpotList.clear()
                this@EarthquakeSpotListAdapter.earthquakeSpotList.addAll(earthquakeSpots)
            }
        }
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_SPOT = 1
        const val VIEW_TYPE_EMPHASIZE = 2
        const val DEFAULT_LIST_SIZE = 1
    }
}

class EarthquakeSpotViewHolder(
    private val view: View,
    onClickListener: (EarthquakeSpotModel?) -> Unit,
    private val isEmphasize: Boolean = false
) : RecyclerView.ViewHolder(view) {

    private val magnitudeTv: TextView = view.magnitude_tv
    private val datetimeTv: TextView = view.datetime_tv
    private val card: MaterialCardView = view.item_card
    private val latTv: TextView = view.lat_tv
    private val lngTv: TextView = view.lng_tv


    private var earthquakeSpot: EarthquakeSpotModel? = null


    init {
        card.setOnClickListener {
            onClickListener.invoke(earthquakeSpot)
        }

        if (isEmphasize) {
            magnitudeTv.setTextColor(view.context.resolveColorReference(R.attr.colorError))
            card.strokeColor = view.context.resolveColorReference(R.attr.colorError)
            view.warning_iv.visibility = View.VISIBLE
        }
    }

    fun onBindView(earthquakeSpotModel: EarthquakeSpotModel?) {
        earthquakeSpotModel?.run {
            earthquakeSpot = this
            magnitudeTv.text =
                view.context.getString(R.string.earthquake_magnitude_string, magnitude)
            datetimeTv.text = this.datetime
            latTv.text = view.context.getString(R.string.earthquake_lat_string, lat)
            lngTv.text = view.context.getString(R.string.earthquake_lng_string, lng)

            card.contentDescription = configureCardAda(this)


        }
    }

    private fun configureCardAda(earthquakeSpotModel: EarthquakeSpotModel): CharSequence =
        if (isEmphasize) {
            view.context.getString(
                R.string.earthquake_spot_item_emphasized_ada,
                earthquakeSpotModel.lng,
                earthquakeSpotModel.lat,
                earthquakeSpotModel.magnitude,
                earthquakeSpotModel.datetime
            )
        } else {
            view.context.getString(
                R.string.earthquake_spot_item_ada,
                earthquakeSpotModel.lng,
                earthquakeSpotModel.lat,
                earthquakeSpotModel.magnitude,
                earthquakeSpotModel.datetime
            )
        }
}

class GeneralEmptyListViewHolder(view: View, @StringRes message: Int? = null) :
    RecyclerView.ViewHolder(view) {

    private val warningTv = view.warning_tv
    private val warningCard = view.warning_card

    init {
        message?.let {
            warningTv.text = view.context.getString(message)
        }
        warningCard.contentDescription = warningTv.text
    }


}