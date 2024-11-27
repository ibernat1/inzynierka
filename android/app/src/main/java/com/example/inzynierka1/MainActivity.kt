package com.example.inzynierka1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.savedstate.SavedStateRegistryOwner
import com.example.inzynierka1.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

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
