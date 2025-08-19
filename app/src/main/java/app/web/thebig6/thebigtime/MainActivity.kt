package app.web.thebig6.thebigtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.web.thebig6.thebigtime.ui.theme.TheBigTimeTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheBigTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    )

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
 Timer(modifier)
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Timer(modifier: Modifier) {
    val showTimer = remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("") }
    if (!showTimer.value)
    Box(modifier) {
        TextField(
            value = time,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() })
                    time = newValue
            },
            label = { Text("Enter number of seconds.") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = !showTimer.value,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Button(onClick = { showTimer.value = true }, modifier = Modifier.align(Alignment.CenterEnd)) { Text("Start Timer") }
    }
    if (showTimer.value)
    Box(modifier) {
        var timeLeft by remember { mutableIntStateOf(time.toInt()) }
        var progress by remember { mutableFloatStateOf(0f) }
        LaunchedEffect(key1 = timeLeft) {
            if (timeLeft > 0) {
                delay(1000L)

                timeLeft--
                progress = 1 - (timeLeft.toFloat() / time.toFloat())
            }
            if (timeLeft == 0) {
                delay(1000L)
                showTimer.value = false
            }
        }

        Text(
            text = timeLeft.toString(),
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        CircularWavyProgressIndicator(gapSize = 25.dp, wavelength = 70.dp, waveSpeed = 30.dp, progress = { animatedProgress },
            modifier = Modifier.fillMaxWidth(0.5F).aspectRatio(1f/1f).align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheBigTimeTheme {
        Greeting("Android")
    }
}