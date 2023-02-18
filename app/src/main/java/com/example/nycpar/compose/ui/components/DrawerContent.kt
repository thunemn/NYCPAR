package com.example.nycpar.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycpar.R
import com.example.nycpar.ui.theme.Accent
import com.example.nycpar.ui.theme.PatuaOneFontFamily

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    itemClick: (String) -> Unit
) {
    with(prepareDrawerItems()) list@ {
        Column(modifier = Modifier
            .padding(vertical = 24.dp)) box@ {
            for(item in this@list) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            itemClick(item.label)
                        }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(28.dp),
                        painter = item.image,
                        contentDescription = item.label
                    )

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = item.label,
                        fontSize = dimensionResource(id = R.dimen.drawer_textSize).value.sp,
                        fontFamily = PatuaOneFontFamily,
                    )
                }
            }
        }
    }
}

@Composable
private fun prepareDrawerItems(): List<DrawerItem> {
    return arrayListOf<DrawerItem>().apply { 
        add(
            DrawerItem(
                image = painterResource(id = R.drawable.ic_hiking),
                label = stringResource(id = R.string.trails)
            )
        )
        add(
            DrawerItem(
                image = painterResource(id = R.drawable.ic_favorite),
                label = stringResource(id = R.string.favorite)
            )
        )
    }
}

data class DrawerItem(
    val image: Painter,
    val label: String 
)