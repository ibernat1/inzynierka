package com.example.inzynierka1.ui.screens

import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inzynierka1.R
import com.example.inzynierka1.ui.components.DisplayInfo
import com.example.inzynierka1.ui.components.NumberInput
import com.example.inzynierka1.ui.components.SimpleButton
import com.example.inzynierka1.ui.components.TextInput
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun PreferencesScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current
//    var showToast = false
    var wrongNumber by remember { mutableStateOf(false) }
    var noData by remember { mutableStateOf(false) }

    val backgroundColor = MaterialTheme.colorScheme.surfaceDim
    val buttonColor = MaterialTheme.colorScheme.primary
    val buttonText = MaterialTheme.colorScheme.onPrimary
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DisplayInfo(
            name = stringResource(id = R.string.preferences_msg),
            fontSize = 35.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        TextInput(
            input = viewModel.userName,
            label = stringResource(id = R.string.name))
        Spacer(modifier = Modifier.weight(0.1f))
        NumberInput(
            input = viewModel.collectingTimeString,
            label = stringResource(id = R.string.time))
        Spacer(modifier = Modifier.weight(1f))
        SimpleButton(
            onButtonClick = {
                if(viewModel.userName.value.isNotEmpty() and viewModel.collectingTimeString.value.isNotEmpty()){
                    if (viewModel.isTimeValid()){
                        viewModel.savePreferences()
                        navHostController.navigate("Menu")
                    }
                    else {
                        wrongNumber = true
                    }
                }
                else{
                    noData = true
                }
//                if (viewModel.userName.value.isNotEmpty() and viewModel.collectingTimeString.value.isNotEmpty()) {
//                    viewModel.savePreferences()
//                    navHostController.navigate("Menu")
//                } else {
//                    showToast =true
//                }
            },
            name = stringResource(id = R.string.save),
            color = buttonColor,
            textColor = buttonText)
        if (wrongNumber){
            Toast.makeText(context, stringResource(id = R.string.formToast), Toast.LENGTH_SHORT).show()
            wrongNumber = false
        }
        if (noData){
            Toast.makeText(context, stringResource(id = R.string.formToast), Toast.LENGTH_SHORT).show()
            noData = false
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreferencesScreenPreview(){
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    PreferencesScreen(navHostController = navController, viewModel = viewModel)
}