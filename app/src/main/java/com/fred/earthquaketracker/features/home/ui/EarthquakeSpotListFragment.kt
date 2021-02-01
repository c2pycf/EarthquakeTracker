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

package com.fred.earthquaketracker.features.home.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.fred.earthquaketracker.R
import com.fred.earthquaketracker.features.home.controller.MainActivity
import com.fred.earthquaketracker.features.home.util.ItemLinearDecorator
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeListViewModel
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeSpotViewModel
import com.fred.earthquaketracker.features.home.viewmodels.HomeNavigationEvent
import com.fred.earthquaketracker.features.home.views.EarthquakeSpotListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_earthquake_spot_list.*
import javax.inject.Inject

class EarthquakeSpotListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EarthquakeListViewModel by activityViewModels { viewModelFactory }

    private val spotViewModel: EarthquakeSpotViewModel by activityViewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).homeComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_earthquake_spot_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingStateLiveData.observe(viewLifecycleOwner, {
            it.second?.let { message ->
                Snackbar.make(earthquake_spots_refresh_recycler_view, message, Snackbar.LENGTH_LONG)
                    .show()
            }

            earthquake_spots_refresh_recycler_view.isRefreshing = it.first
        })

        earthquake_spots_refresh_recycler_view.setOnRefreshListener {
            viewModel.loadEarthquakeList()
        }

        val adapter = EarthquakeSpotListAdapter {
            it?.run {
                spotViewModel.updateCurrentPickedLocation(this.lng, this.lat)
                viewModel.navigationEvent(HomeNavigationEvent.OnListItemClickedEvent)
            }
        }

        viewModel.earthquakeListLiveData.observe(viewLifecycleOwner, {
            it.map { earthquake ->
                earthquake.toModel()
            }.let { earthquakeModelList ->
                adapter.loadList(earthquakeModelList)
            }
        })

        earthquake_spots_recycler_view.addItemDecoration(
            ItemLinearDecorator(
                resources.getDimensionPixelSize(
                    R.dimen.padding_16
                )
            )
        )
        earthquake_spots_recycler_view.adapter = adapter

    }


    companion object {
        val TAG = EarthquakeSpotListFragment::class.simpleName
    }
}