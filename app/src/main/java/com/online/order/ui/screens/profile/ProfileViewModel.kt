package com.online.order.ui.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.online.order.R
import com.online.order.data.ProfileMenu
import com.online.order.data.ThemeType
import com.online.order.preferences.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val themePreferences: ThemePreferences) : ViewModel() {


    val _menuItems = MutableStateFlow<List<ProfileMenu>>(emptyList())
    val menuItems : MutableStateFlow<List<ProfileMenu>> = _menuItems


    private val _themeTypeSelected = MutableStateFlow<ThemeType?>(null)
    val themeTypeSelected : StateFlow<ThemeType?> = _themeTypeSelected

    init {

        getProfileMenuList()
        _themeTypeSelected.value = getSavedThemeId()
    }


    private fun getProfileMenuList(){
        _menuItems.value = listOf(
            ProfileMenu("Appearance", R.drawable.ic_theme,false,""),
            ProfileMenu("My Orders",R.drawable.ic_order,true,"myorders"),
            ProfileMenu("My Address Book",R.drawable.ic_address,true,"address"),
            ProfileMenu("About",R.drawable.ic_above,true, "about"),
             ProfileMenu("Log out",R.drawable.ic_logout,false, ""))
    }

    fun saveThemeId(theme : ThemeType){
        themePreferences.saveThemeId(theme)
        _themeTypeSelected.value = theme

    }

    fun getSavedThemeId() : ThemeType?{
        return themePreferences.getSavedThemeId()
    }


}