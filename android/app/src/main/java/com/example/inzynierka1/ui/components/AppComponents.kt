package com.example.inzynierka1.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SimpleButton(
    onButtonClick: () -> Unit,
    name: String,
    color: Color,
    textColor: Color){
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 40.dp)
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
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
            fontWeight = FontWeight.Medium,
            fontSize = 27.sp,
            lineHeight = 30.sp,
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}

@Composable
fun DisplayInfo(name: String,
                fontSize: TextUnit = 20.sp,
                lineHeight: TextUnit = 24.sp,
                fontWeight: FontWeight = FontWeight.Medium,
                color: Color = Color(0xFF000000)
) {
    Text(
        modifier = Modifier.padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 0.dp),
        text = name,
        color = color,
        fontSize = fontSize,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center
    )
}


@Composable
fun TextInput(input: MutableState<String>, label: String){
    TextField(
        modifier = Modifier.width(310.dp),
        value = input.value,
        onValueChange = { newValue ->
            input.value = newValue
        },
        label = {
            Text (label)
    })
}

@Composable
fun NumberInput(input: MutableState<String>, label: String){
    TextField(
        modifier = Modifier.width(310.dp),
        value = input.value,
        onValueChange = { newValue ->
            input.value = newValue
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        label = {
            Text (label)
        })
}

@Composable
fun toastMessage(text: String) {
    Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
}

