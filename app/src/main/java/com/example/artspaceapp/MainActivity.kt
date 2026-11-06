package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentStep by remember { mutableIntStateOf(1) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val useWideLayout = maxWidth > 600.dp

        if (useWideLayout) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageDisplay(currentStep, modifier = Modifier.weight(1f))
                ControlPanel(
                    currentStep = currentStep,
                    onPrevClick = { if (currentStep > 1) currentStep-- },
                    onNextClick = { if (currentStep < 3) currentStep++ },
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ImageDisplay(currentStep)
                Spacer(modifier = Modifier.height(32.dp))
                ControlPanel(
                    currentStep = currentStep,
                    onPrevClick = { if (currentStep > 1) currentStep-- },
                    onNextClick = { if (currentStep < 3) currentStep++ }
                )
            }
        }
    }
}

@Composable
fun ImageDisplay(step: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp) // Увеличили высоту для новых картин
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
    ) {
        when (step) {
            1 -> Image(
                painter = painterResource(id = R.drawable.image1),
                contentDescription = stringResource(id = R.string.artwork_title_1),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            2 -> Image(
                painter = painterResource(id = R.drawable.image2),
                contentDescription = stringResource(id = R.string.artwork_title_2),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            3 -> Image(
                painter = painterResource(id = R.drawable.image3),
                contentDescription = stringResource(id = R.string.artwork_title_3),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ControlPanel(
    currentStep: Int,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.widthIn(max = 400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val title = when (currentStep) {
                    1 -> R.string.artwork_title_1
                    2 -> R.string.artwork_title_2
                    else -> R.string.artwork_title_3
                }
                val artist = when (currentStep) {
                    1 -> R.string.artwork_artist_1
                    2 -> R.string.artwork_artist_2
                    else -> R.string.artwork_artist_3
                }

                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = artist),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onPrevClick,
                enabled = currentStep > 1,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.previous))
            }
            Button(
                onClick = onNextClick,
                enabled = currentStep < 3,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    MaterialTheme {
        ArtSpaceApp()
    }
}