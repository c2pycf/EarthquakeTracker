package com.fred.earthquaketracker

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fred.earthquaketracker.database.earthquakes.EarthquakeSpot
import com.fred.earthquaketracker.database.earthquakes.EarthquakesDao
import com.fred.earthquaketracker.database.earthquakes.EarthquakesDatabase
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EarthquakeRoomTest {
    private lateinit var testDao: EarthquakesDao
    private lateinit var testDb: EarthquakesDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDb = Room.inMemoryDatabaseBuilder(
            context, EarthquakesDatabase::class.java
        ).build()
        testDao = testDb.earthquakesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        testDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeEarthquakeSpotThenGetByEqid() {
        val testEarthquakeSpot = EarthquakeSpot("", 0f, 0f, "us", "test", 0f, 0f)
        testDao.insertAll(earthquakeSpot = listOf(testEarthquakeSpot))
        val byName = testDao.findByEqid("test")
        Assert.assertThat(byName[0], CoreMatchers.equalTo(testEarthquakeSpot))
    }

    @Test
    @Throws(Exception::class)
    fun writeEarthquakeSpotThenUpdateEarthquakeSpot() {
        val testEarthquakeSpot = EarthquakeSpot("", 0f, 0f, "us", "test", 0f, 0f)
        testDao.insertAll(earthquakeSpot = listOf(testEarthquakeSpot))

        val updatedSpot = testEarthquakeSpot.copy(eqid = "update test")
        testDao.updateEarthquakeSpot(updatedSpot)
        val byName = testDao.findByEqid("update test")
        Assert.assertThat(byName[0], CoreMatchers.equalTo(updatedSpot))
    }
}