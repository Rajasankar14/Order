package com.online.order

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel
import com.online.order.navigation.MyAuthNavGraph
import com.online.order.theme.AppTheme
import com.online.order.ui.screens.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profileViewModel : ProfileViewModel = hiltViewModel()
            val themeType by profileViewModel.themeTypeSelected.collectAsState()

            var isCustomDarkTheme = isSystemInDarkTheme()
            if(themeType != null && themeType!!.isThemeSelected != -1){
                isCustomDarkTheme = if(themeType!!.isThemeSelected == 1){
                    false
                }else if(themeType!!.isThemeSelected == 2){
                    true
                }else{
                    isSystemInDarkTheme()
                }

            }

                AppTheme(isCustomDarkTheme) {
                    MyAuthNavGraph(profileViewModel)
                }




        }
    }
}