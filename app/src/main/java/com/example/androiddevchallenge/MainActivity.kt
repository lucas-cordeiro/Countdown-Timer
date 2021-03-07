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
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import java.util.*
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

val LongToVector: TwoWayConverter<Long, AnimationVector1D> = TwoWayConverter({ AnimationVector1D(it.toFloat()) }, { it.value.toLong() })

val time = 2 * 60 * 1000L


// Start building your app here! 1
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {

        var play by remember { mutableStateOf(false) }

        val timeAnimated: Int by animateIntAsState(
            targetValue = if (play) 0 else time.toInt(),
            animationSpec = tween(
                durationMillis = time.toInt()
            )
        )

        val cal = remember(timeAnimated){
            Calendar.getInstance().apply {
                timeInMillis = timeAnimated.toLong()
            }
        }

        val minute = remember(cal.get(Calendar.MINUTE)){
            cal.get(Calendar.MINUTE)
        }
        var minuteExpanded by remember{ mutableStateOf(true) }

        val (secondTen, secondUnit) = remember(cal.get(Calendar.SECOND)){
            val seconds = cal.get(Calendar.SECOND).toString().padStart(2,'0')
            Pair(seconds[0].toString().toInt(),seconds[1].toString().toInt())
        }
        var secondTenExpanded by remember{ mutableStateOf(true) }
        var secondUnitExpanded by remember{ mutableStateOf(true) }

        LaunchedEffect(Unit){
            play = true
        }

        LaunchedEffect(minute){
            minuteExpanded = false
            delay(ANIMATION_DURATION.toLong() + 100)
            minuteExpanded = true
        }
        LaunchedEffect(secondTen){
            secondTenExpanded = false
            delay(ANIMATION_DURATION.toLong() + 100)
            secondTenExpanded = true
        }
        LaunchedEffect(secondUnit){
            secondUnitExpanded = false
            delay(ANIMATION_DURATION.toLong() + 100)
            secondUnitExpanded = true
        }
        
        Row() {
            Number(number = minute, expanded = minuteExpanded)
            Number(number = secondTen, expanded = secondTenExpanded)
            Number(number = secondUnit, expanded = secondUnitExpanded)
        }

        // var expanded by remember{ mutableStateOf(true) }
        // var number by remember{ mutableStateOf(1) }
        //
        // val scope = rememberCoroutineScope()
        //
        // Column() {
        //     Text(
        //         text = "Ready... Set... GO!",
        //         style = MaterialTheme.typography.body1.copy(
        //             fontWeight = FontWeight.Bold
        //         ),
        //         modifier = Modifier
        //             .padding(top = 10.dp, start = 100.dp)
        //     )
        //
        //     Row(Modifier.align(Alignment.CenterHorizontally)) {
        //         Number(
        //             number = number-1,
        //             expanded = expanded,
        //             modifier = Modifier.align(Alignment.CenterVertically)
        //         )
        //         Number(
        //             number = number,
        //             expanded = expanded,
        //             modifier = Modifier.align(Alignment.CenterVertically)
        //         )
        //         Number(
        //             number = number+1,
        //             expanded = expanded,
        //             modifier = Modifier.align(Alignment.CenterVertically)
        //         )
        //     }
        //
        //    Row() {
        //        Button(onClick = {
        //            scope.launch {
        //                expanded = false
        //                delay(ANIMATION_DURATION.toLong())
        //                number -= 1
        //                delay(ANIMATION_DURATION.toLong())
        //                expanded = true
        //            }
        //        }) {
        //            Text(text = "-")
        //        }
        //
        //        Button(onClick = {
        //           scope.launch {
        //               expanded = false
        //               delay(ANIMATION_DURATION.toLong())
        //               number += 1
        //               delay(ANIMATION_DURATION.toLong())
        //               expanded = true
        //           }
        //        }) {
        //            Text(text = "+")
        //        }
        //    }
        // }
    }
}

@Composable
private fun Number(
    number: Int,
    expanded: Boolean,
    modifier: Modifier = Modifier
){
   Box(modifier.size(70.dp)) {
       when(number){
           0 -> NumberZero(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           1 -> NumberOne(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           2 -> NumberTwo(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           3 -> NumberThree(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           4 -> NumberFour(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           5 -> NumberFive(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           6 -> NumberSix(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           7 -> NumberSeven(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           8 -> NumberEight(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           9 -> NumberNine(expanded = expanded, modifier = Modifier.align(Alignment.Center))
           else -> {}
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

    path.moveTo(-5f,40f)
    path.lineTo(value3.value, 40f)

    path.moveTo(22.5f,40f)
    path.addArc(Rect(0f, 40f, 45f, 85f), 270f, value4.value)

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

@Composable
private fun NumberSix(
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

    val path = Path()

    path.moveTo(0f,90f)
    path.lineTo(0f,value2.value)
    path.moveTo(0f,40f)
    path.addArc(Rect(0f, 40f, 45f, 90f), 180f, value.value)
    path.moveTo(0f, 67.5f)
    path.lineTo(0f, 40f)
    path.addArc(Rect(0f, 0f, 90f, 90f), 180f, value2.value)

    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 360f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if(expanded) 90f else 0f,
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
private fun NumberSeven(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }

    val value = remember { Animatable(0f) }

    val path = Path()

    path.moveTo(0f,0f)
    path.lineTo(value.value, 0f)
    path.moveTo((value.value-5f).coerceAtLeast(0f), 2.5f)
    path.lineTo(0f, 90f)

    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 55f else 0f,
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
private fun NumberEight(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val duration = remember{ (ANIMATION_DURATION / 3)*2 }
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = duration,
        )
    }

    val duration2 = remember{ (ANIMATION_DURATION / 3) }
    val animSpec2: AnimationSpec<Float> = remember {
        tween(
            durationMillis = duration2,
        )
    }

    val value = remember { Animatable(0f) }
    val value2 = remember { Animatable(0f) }
    val value3 = remember { Animatable(90f) }

    val path = Path()
    path.addArc(Rect(0f, 0f, 45f, 45f), 90f, value.value)
    path.addArc(Rect(0f, 45f, 45f, 90f), 270f, value.value)

    val path2 = Path()
    path2.moveTo(value2.value,45f)
    path2.lineTo(value2.value,90 - value3.value)

    path2.moveTo(value2.value,45f)
    path2.lineTo(value2.value,value3.value)

    LaunchedEffect(expanded) {
        launch {
            if(!expanded)
                delay(duration.toLong())
            value2.animateTo(
                targetValue = if (expanded) 22.5f else 0f,
                animationSpec = animSpec2
            )
        }
        launch {
            if(!expanded)
                delay(duration.toLong())
            value3.animateTo(
                targetValue = if (expanded) 45f else 90f,
                animationSpec = animSpec2
            )
        }
        launch {
            if(expanded)
                delay(duration2.toLong())
            value.animateTo(
                targetValue = if (expanded) 360f else 0f,
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
            color = Color.Red,
            style = Stroke(10f)
        )
    }
}

@Composable
private fun NumberNine(
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
    val value3 = remember { Animatable(22.5f) }

    val path = Path()

    path.moveTo(0f,90f)
    path.lineTo(0f,value2.value)
    path.moveTo(0f,0f)
    path.addArc(Rect(0f, 0f, 45f, 45f), 180f, value.value)
    path.moveTo(45f, 22.5f)
    path.lineTo(45f, value3.value)
    path.addArc(Rect(-45f, 0f, 45f, 90f), 0f, value2.value)

    LaunchedEffect(expanded){
        launch {
            value.animateTo(
                targetValue = if(expanded) 360f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if(expanded) 90f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value3.animateTo(
                targetValue = if(expanded) 50f else 22.5f,
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
private fun NumberZero(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    val animSpec: AnimationSpec<Float> = remember {
        tween(
            durationMillis = ANIMATION_DURATION,
        )
    }

    val value = remember { Animatable(0f) }
    val value2 = remember { Animatable(22.5f) }
    val value3 = remember { Animatable(0f) }

    val path = Path()
    path.moveTo(0f,22.5f)
    path.addArc(Rect(0f, 0f, 45f, 45f), 180f, value.value)

    val path2 = Path()
    path2.moveTo(0f,67.5f)
    path2.addArc(Rect(0f, 45f, 45f, 90f), 180-value.value, value.value)

    val path3 = Path()
    path3.moveTo(45f,22.5f)
    path3.lineTo(45f,value2.value)

    val path4 = Path()
    path4.moveTo(0f,22.5f)
    path4.lineTo(0f,67.5f)
    path4.moveTo(0f,22.5f)
    path4.lineTo(0f,value3.value)
    path4.moveTo(0f,67.5f)
    path4.lineTo(0f,90f - value3.value)

    LaunchedEffect(expanded) {
        launch {
            value.animateTo(
                targetValue = if (expanded) 180f else 0f,
                animationSpec = animSpec
            )
        }
        launch {
            value2.animateTo(
                targetValue = if (expanded) 67.5f else 22.5f,
                animationSpec = animSpec
            )
        }
        launch {
            value3.animateTo(
                targetValue = if (expanded) 22.5f else 0f,
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
            color = Color.Red,
            style = Stroke(10f)
        )
        drawPath(
            path = path3,
            color = Color.Red,
            style = Stroke(10f)
        )
        drawPath(
            path = path4,
            color = Color.Red,
            style = Stroke(10f)
        )
    }
}

const val ANIMATION_DURATION = 200

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
