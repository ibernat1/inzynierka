package com.example.inzynierka1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    private val TAG: String = "UserPreferencesRepo"

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val COLLECTING_TIME = stringPreferencesKey("collecting_time")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { preferences ->
        val userName = preferences[PreferencesKeys.USER_NAME] ?: "brak danych"
        val collectingTime = preferences[PreferencesKeys.COLLECTING_TIME] ?: "brak danych"

        UserPreferences(userName, collectingTime)
    }

    suspend fun updateUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = userName
        }
    }

    suspend fun updateCollectingTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.COLLECTING_TIME] = time
        }
    }


}