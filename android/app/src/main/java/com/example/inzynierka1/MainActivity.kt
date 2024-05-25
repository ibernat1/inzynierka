package com.example.inzynierka1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.inzynierka1.ui.screens.MainActivityScreen
import com.example.inzynierka1.ui.theme.AppTheme
import com.example.inzynierka1.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SavedStateRegistryOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
//                val viewModel = viewModel<MainViewModel>()
//                viewModel.onCreate()
                Navigation()
//                MainActivityScreen()
            }
        }
    }
}
