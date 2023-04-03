package com.example.bookfinder.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.bookfinder.util.UserPreferencesManager.PreferencesKeys.SHOULD_SHOW_INFO_POP_UP
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferencesManager @Inject constructor(
        private val dataStore: DataStore<Preferences>
    ) {

        private object PreferencesKeys {
            val SHOULD_SHOW_INFO_POP_UP = booleanPreferencesKey("should_show_info_pop_up")
        }

    //get the saved preferences
    val getShouldShowInfoPopUp: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[SHOULD_SHOW_INFO_POP_UP] ?: true
        }

    //save preference into datastore
    suspend fun setShouldShowInfoPopUp(shouldShowAgain: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOULD_SHOW_INFO_POP_UP] = shouldShowAgain
        }
    }
}
/*

class UserPreferencesManager(private val context: Context) {
    // to make sure there's only one instance
    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("myPreferences")
        val SHOULD_SHOW_INFO_POP_UP = booleanPreferencesKey("should_show_info_pop_up")
    }

    //get the saved preferences
    val getShouldShowInfoPopUp: Flow<Boolean?> = context.dataStoree.data
        .map { preferences ->
            preferences[SHOULD_SHOW_INFO_POP_UP] ?: true
        }

    //save preference into datastore
    suspend fun saveShouldShowInfoPopUp(shouldShowAgain: Boolean) {
        context.dataStoree.edit { preferences ->
            preferences[SHOULD_SHOW_INFO_POP_UP] = shouldShowAgain
        }
    }


}

 */