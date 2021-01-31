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