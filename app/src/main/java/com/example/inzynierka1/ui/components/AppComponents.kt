package com.example.inzynierka1.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleButton(onButtonClick: () -> Unit, name: String, color: Color, textColor: Color){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            Log.d("SimpleButton", "Button clicked")
            onButtonClick()
        },
        colors = ButtonDefaults.buttonColors(color)
        ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = name,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}

@Composable
fun displaySensor(name: String, fontSize: TextUnit = 20.sp) {
    Text(
        text = name,
        fontSize = fontSize
    )
}