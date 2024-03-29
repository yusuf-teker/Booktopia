package com.yusufteker.bookfinder.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.yusufteker.bookfinder.util.UserPreferencesManager.PreferencesKeys.SHOULD_SHOW_INFO_POP_UP
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
