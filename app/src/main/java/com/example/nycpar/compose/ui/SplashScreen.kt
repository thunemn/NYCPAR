package com.example.nycpar.compose.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.nycpar.R
import com.example.nycpar.compose.ui.components.StatusBar
import com.example.nycpar.models.Screens
import com.example.nycpar.ui.theme.*
import com.example.nycpar.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val activity = (LocalContext.current as? Activity)
    viewModel.updateCurrentScreen(Screens.SPLASH)

    StatusBar()

    val context = LocalContext.current

    LaunchedEffect(context) {
        delay(1000L)
        navigateToHome()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context).data(data = R.drawable.tree_animation).build(), imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.splash_tree_size)),
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.splash_text_margin)))

        Text(
            text = stringResource(id = R.string.splash_app_name),
            color = Accent,
            fontSize = dimensionResource(id = R.dimen.splash_name_textSize).value.sp,
            fontFamily = PatuaOneFontFamily,
            textAlign = TextAlign.Center,
        )
    }
}