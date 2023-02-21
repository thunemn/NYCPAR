package com.example.nycpar.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nycpar.R
import com.example.nycpar.ui.theme.PatuaOneFontFamily
import com.example.nycpar.utils.TextUtils
import kotlinx.coroutines.launch

@Composable
fun NYCTopAppBar(
    currentScreen: String,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(
            text = TextUtils.capitalizeFirstLetter(currentScreen),
            fontFamily = PatuaOneFontFamily
        ) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(id = R.string.menu),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.let { drawer ->
                                if (drawer.isClosed) drawer.open() else drawer.close()
                            }
                        }
                    })
            )
        }
    )
}