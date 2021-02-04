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

package com.fred.earthquaketracker.features.home.controller

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.fred.earthquaketracker.MyApplication
import com.fred.earthquaketracker.R
import com.fred.earthquaketracker.features.home.di.HomeComponent
import com.fred.earthquaketracker.features.home.ui.EarthquakeSpotListFragment
import com.fred.earthquaketracker.features.home.ui.EarthquakeSpotListFragmentDirections
import com.fred.earthquaketracker.features.home.ui.EarthquakeSpotMapFragment
import com.fred.earthquaketracker.features.home.viewmodels.EarthquakeListViewModel
import com.fred.earthquaketracker.features.home.viewmodels.HomeNavigationEvent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var homeComponent: HomeComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EarthquakeListViewModel by viewModels { viewModelFactory }

    private val earthquakeTitle by lazy { resources.getString(R.string.earthquake_list_title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        //Inject dependencies here
        homeComponent = (application as MyApplication).appComponent
            .homeComponent().create()
        homeComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureView(savedInstanceState)

        viewModel.homeNavigationLiveData.observe(this, {
            when (it) {
                HomeNavigationEvent.OnListItemClickedEvent -> {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) -> {
                            addEarthquakeSpotMapFragment()
                        }
                        else -> {
                            launchRequestPermission()
                        }
                    }
                }
            }
        })
    }

    private fun launchRequestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun addEarthquakeSpotMapFragment() {
        val direction =
            EarthquakeSpotListFragmentDirections.actionEarthquakeSpotListFragmentToEarthquakeSpotMapFragment()
        findNavController(R.id.nav_host_fragment).navigate(direction)

        action_bar.setExpanded(true, false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configureView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = earthquakeTitle
            setDisplayHomeAsUpEnabled(false)
        }

        if (savedInstanceState == null) {
            viewModel.reloadEarthquakeList()
        }
    }

    private fun getEarthquakeSpotMapFragment(): Fragment =
        supportFragmentManager.findFragmentByTag(EarthquakeSpotMapFragment.TAG)
            ?: EarthquakeSpotMapFragment()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Check when request permissions have returned
     * and check the result again
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    addEarthquakeSpotMapFragment()
                } else {
                    viewModel.showNotification(this.getString(R.string.permission_error))
                }
            }
            else -> Unit//Not handled yet
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }
}