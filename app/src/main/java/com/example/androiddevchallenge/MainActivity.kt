/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.exp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here! 1
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {

        var expanded by remember{ mutableStateOf(true) }
        var number by remember{ mutableStateOf(1) }

        val scope = rememberCoroutineScope()

        Column() {
            Text(
                text = "Ready... Set... GO!",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 10.dp, start = 100.dp)
            )

            when(number){
                1 -> NumberOne(expanded = expanded, modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally))

                2 -> NumberTwo(expanded = expanded, modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally))

                3 -> NumberThree(expanded = expanded, modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally))

                4 -> NumberFour(expanded = expanded, modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
                )
                5 -> NumberFive(expanded = expanded, modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally))
                else -> {}
            }

           Row() {
               Button(onClick = {
                   scope.launch {
                       expanded = false
                       delay(ANIMATION_DURATION.toLong())
                       number -= 1
                       delay(ANIMATION_DURATION.toLong())
                       expanded = true
                   }
               }) {
                   Text(text = "-")
               }

               Button(onClick = {
                  scope.launch {
                      expanded = false
                      delay(ANIMATION_DURATION.toLong())
                      number += 1
                      delay(ANIMATION_DURATION.toLong())
                      expanded = true
                  }
               }) {
                   Text(text = "+")
               }
           }
        }
    }
}

@Composable
private fun NumberOne(
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }
    val value = remember { Animatable(0f) }

    val path1 = Path()
    val path2 = Path()


    path1.moveTo(value.value, 2.5f)
    path1.lineTo(0f, 30f)

    path2.moveTo(value.value, 0f)
    path2.lineTo(value.value, 90f)



    LaunchedEffect(expanded) {
        value.animateTo(
            targetValue = if (expanded) 20f else 0f,
            animationSpec = animSpec
        )
    }

    Canvas(modifier = modifier) {
        drawPath(
            path = path1,
            color = Color.Red,
            style = Stroke(10f)
        )
        drawPath(
            path = path2,
            color = Color.Red,
            style = Stroke(10f)
        )
    }
}

@Composable
private fun NumberTwo(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }
    val value = remember { Animatable(90f) }
    val value2 = remember { Animatable(0f) }
    val value3 = remember { Animatable(0f) }
    val value4 = remember { Animatable(0f) }
    val value5 = remember { Animatable(0f) }


    val path = Path()
    val path2 = Path()
    val path3 = Path()

    path.addArc(Rect(0f, 0f, 45f, 45f), 180f, value3.value)
    path.lineTo(value2.value, value.value)

    path2.moveTo(0f, 85f)
    path2.lineTo(value4.value, 85f)

    path3.moveTo(0f, 22.5f)
    path3.lineTo(0f,value5.value)



    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 85f else 90f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if(expanded) 4f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value3.animateTo(
                targetValue = if(expanded) 200f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value4.animateTo(
                targetValue = if(expanded) 50f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value5.animateTo(
                targetValue = if(expanded) 22.5f else 0f,
                animationSpec = animSpec
            )
        }
    }

    Canvas(modifier = modifier){
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(10f)
        )
        drawPath(
            path = path2,
            color = Color.Red,
            style = Stroke(10f)
        )
        drawPath(
            path = path3,
            color = Color.Red,
            style = Stroke(10f)
        )
    }
}

@Composable
private fun NumberThree(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }

    val value = remember { Animatable(0f) }
    val value2 = remember { Animatable(90f) }

    val path = Path()

    path.lineTo(0f, value2.value)
    path.moveTo(0f,0f)
    path.addArc(Rect(0f, 0f, 45f, 45f), 180f, value.value)
    path.addArc(Rect(0f, 45f, 45f, 90f), 270f, value.value)

    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 280f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if(expanded) 0f else 90f,
                animationSpec = animSpec
            )
        }
    }

    Canvas(modifier = modifier) {
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(10f)
        )
    }
}

@Composable
private fun NumberFour(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }

    val value = remember { Animatable(0f) }
    val value2 = remember { Animatable(0f) }
    val value3 = remember { Animatable(-10f) }

    val path = Path()
    path.moveTo(value.value,90f)
    path.lineTo(value.value, 0f)
    path.moveTo(value.value,0f)
    path.lineTo(0f,value2.value)
    path.moveTo(0f,value2.value)
    path.lineTo(value2.value, value2.value)

    val path2 = Path()
    path2.moveTo(value3.value,-5f)
    path2.lineTo(50f,-5f)
    path2.moveTo(value3.value,0f)
    path2.lineTo(value3.value,90f)

    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 45f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if(expanded) 65f else 0f,
                animationSpec = animSpec
            )
        }

        launch {
            value3.animateTo(
                targetValue = if(expanded) -5f else -10f,
                animationSpec = animSpec
            )
        }
    }

    Canvas(modifier = modifier) {
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(10f)
        )
        drawPath(
            path = path2,
            color = Color.White,
            style = Stroke(10f)
        )
    }
}

@Composable
private fun NumberFive(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }

    val value = remember { Animatable(90f) }
    val value2 = remember { Animatable(0f) }
    val value3 = remember { Animatable(0f) }
    val value4 = remember { Animatable(0f) }
    val value5 = remember { Animatable(90f) }

    val path = Path()

    path.lineTo(0f, value.value)

    path.moveTo(0f,5f)
    path.lineTo(value2.value, 5f)

    path.moveTo(-5f,35f)
    path.lineTo(value3.value, 35f)

    path.moveTo(22.5f,35f)
    path.addArc(Rect(0f, 35f, 45f, 85f), 270f, value4.value)

    path.moveTo(value3.value,85f)
    path.lineTo(-5f,85f)

    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 35f else 90f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if(expanded) 45f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value3.animateTo(
                targetValue = if(expanded) 22.5f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value4.animateTo(
                targetValue = if(expanded) 180f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value5.animateTo(
                targetValue = if(expanded) 45f else 90f,
                animationSpec = animSpec
            )
        }
    }

    Canvas(modifier = modifier) {
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(10f)
        )
    }
}

const val ANIMATION_DURATION = 500

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
