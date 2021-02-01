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

package com.fred.earthquaketracker.database.earthquakes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.fred.earthquaketracker.features.home.models.EarthquakeSpotModel

@Entity
data class EarthquakeSpot(
    val datetime: String,
    val depth: Float,
    val lng: Float,
    val src: String,
    val eqid: String,
    val magnitude: Float,
    val lat: Float
) {

    @PrimaryKey
    var id: Int = -1

    fun toModel(): EarthquakeSpotModel =
        EarthquakeSpotModel(
            datetime = datetime,
            depth = depth,
            lng = lng,
            src = src,
            eqid = eqid,
            magnitude = magnitude,
            lat = lat,
            id = id
        )

    companion object {
        fun fromModel(id:Int, earthquakeSpot: EarthquakeSpotModel): EarthquakeSpot =
            EarthquakeSpot(
                datetime = earthquakeSpot.datetime,
                depth = earthquakeSpot.depth,
                lng = earthquakeSpot.lng,
                src = earthquakeSpot.src,
                eqid = earthquakeSpot.eqid,
                magnitude = earthquakeSpot.magnitude,
                lat = earthquakeSpot.lat
            ).also { it.id = earthquakeSpot.id ?: id }
    }
}

@Dao
interface EarthquakesDao {
    @Update
    fun updateEarthquakeSpot(earthquakeSpot: EarthquakeSpot)

    @get:Query("select * from EarthquakeSpot")
    val getAllEarthquakeSpotsLiveData: LiveData<List<EarthquakeSpot>>

    @Query("select * from EarthquakeSpot where eqid is :eqid")
    fun findByEqid(eqid: String): List<EarthquakeSpot>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(earthquakeSpot: List<EarthquakeSpot>)
}

@Database(entities = [EarthquakeSpot::class], version = 2021013113, exportSchema = false)
abstract class EarthquakesDatabase : RoomDatabase() {
    abstract val earthquakesDao: EarthquakesDao

    companion object {
        private const val DATABASE_NAME = "application_earthquakes_db"

        @Volatile
        private var instance: EarthquakesDatabase? = null

        fun getInstance(context: Context): EarthquakesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): EarthquakesDatabase =
            Room.databaseBuilder(context, EarthquakesDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}