package com.example.nycpar.compose.ui

import android.os.Build
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.nycpar.ui.theme.PatuaOneFontFamily
import com.example.nycpar.ui.theme.White
import com.example.nycpar.R
import com.example.nycpar.ui.theme.Accent
import com.example.nycpar.ui.theme.Primary

@Preview
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
//    val activity = (LocalContext.current as AppCompatActivity)

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