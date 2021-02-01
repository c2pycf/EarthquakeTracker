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