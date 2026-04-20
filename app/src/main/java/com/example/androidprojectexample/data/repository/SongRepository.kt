package com.example.androidprojectexample.data.repository

import android.util.Log
import com.example.androidprojectexample.data.local.SongLocalDataSource
import com.example.androidprojectexample.data.model.Song
import com.example.androidprojectexample.data.remote.SongRemoteDataSource

// SSOT for app data + business logic (The repository orchestrates everything
// - DataSources + entity class -> pushes upwards to ViewModel. The ViewModel does not know
// what the source of the data is)
//
// Data layer:
// The data layer of an app contains the business logic. Business logic is what gives value to
// your app—it comprises rules that determine how your app creates, stores, and changes data.
//
//The data layer is made up of repositories, each of which can contain zero to many data sources.
// Create a repository class for each different type of data you handle in your app. For example,
// you might create a MoviesRepository class for data related to movies or a PaymentsRepository class
// for data related to payments.
//In a typical architecture, the data layer's repositories provide data to the rest of the app and
// depend on the data sources.
//Figure 3. The role of the data layer in app architecture.
//
//Repository classes are responsible for the following:
//
//Exposing data to the rest of the app - SongRepository.getSongs()
//Centralizing changes to the data - only repository modifies data flow
//Resolving conflicts between multiple data sources - local vs remote decision
//Abstracting sources of data from the rest of the app - ViewModel doesn’t know:  API file memory
//Containing business logic - caching, fallback, merging
//
//Each data source class has the responsibility of working with only one source of data, which can
// be a file, a network source, or a local database. Data-source classes are the bridge between the
// application and the system for data operations.
class SongRepository(
    private val api: SongRemoteDataSource = SongRemoteDataSource(),
    private val dao: SongLocalDataSource = SongLocalDataSource()
) {
    fun getSongs(): List<Song> {
        val local = dao.getSongs()

        return local.ifEmpty {
            Log.d("BOYKO", "SongRepository: No songs found in local storage, fetching from API")
            val remote = api.fetchSongs()
            dao.saveSongs(remote)
            remote
        }
    }
}