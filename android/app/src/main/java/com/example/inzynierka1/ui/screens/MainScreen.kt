package com.example.inzynierka1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inzynierka1.R
import com.example.inzynierka1.ui.components.SimpleButton
import com.example.inzynierka1.ui.components.DisplayInfo
import com.example.inzynierka1.uiState.UserState
import com.example.inzynierka1.viewmodels.MainViewModel

@Composable
fun MainActivityScreen(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    val backgroundColor = MaterialTheme.colorScheme.surfaceDim
    val buttonColor = MaterialTheme.colorScheme.primary
    val buttonText = MaterialTheme.colorScheme.onPrimary
    val textColor = MaterialTheme.colorScheme.onSurface


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        if (uiState.userState == UserState.STANDING){
            DisplayInfo(
                stringResource(id = R.string.standing_msg),
                fontSize = 35.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
        else if (uiState.userState == UserState.WALKING){
            DisplayInfo(
                stringResource(id = R.string.collecting_data_msg),
                fontSize = 35.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
        else {
            DisplayInfo(
                stringResource(id = R.string.collected_data_msg),
                fontSize = 35.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val imageResource = if (uiState.userState == UserState.WALKING) {
                painterResource(R.drawable.walking)
            } else {
                painterResource(R.drawable.standing)
            }
            Image(
                painter = imageResource,
                contentDescription = "User State Image",
                modifier = Modifier.size(250.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.userState == UserState.STANDING) {
            SimpleButton({ viewModel.writeSensors() } ,
                name = stringResource(id = R.string.standing_button),
                buttonColor,
                buttonText
            )
        }
        else if (uiState.userState == UserState.COLLECTED_DATA){
            SimpleButton({ viewModel.writeSensors() } ,
                name = stringResource(id = R.string.collected_data_button),
                buttonColor,
                buttonText
            )
        }
        else {
            LinearProgressIndicator(
                progress = { viewModel.progress.value },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MainActivityScreenPreview(){
//    MainActivityScreen()
//}