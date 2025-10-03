@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rustoretest

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onMicClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {}
) {
    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .weight(1f)
                .height(54.dp),
            placeholder = { Text("бем бем бем") },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
            trailingIcon = {
                IconButton(onClick = onMicClick) {
                    Icon(Icons.Outlined.MoreVert, contentDescription = "Voice search")
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2B2B2B),
                unfocusedContainerColor = Color(0xFF2B2B2B),
                disabledContainerColor = Color(0xFF2B2B2B),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedPlaceholderColor = Color(0xFF9BA0A6),
                unfocusedPlaceholderColor = Color(0xFF9BA0A6),
                focusedLeadingIconColor = Color(0xFF9BA0A6),
                unfocusedLeadingIconColor = Color(0xFF9BA0A6),
                focusedTrailingIconColor = Color(0xFF9BA0A6),
                unfocusedTrailingIconColor = Color(0xFF9BA0A6),
            )
        )

        Spacer(Modifier.width(10.dp))

        BadgedBox(
            badge = {
                Badge(
                    containerColor = Color(0xFFFF3B30),
                    modifier = Modifier.size(8.dp)
                )
            }
        ) {
            FilledIconButton(
                onClick = onAvatarClick,
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFF464646),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Account",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun AdBox(
//    imageUri: Uri?,
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {}
) {
    Box(modifier = modifier
        .padding(horizontal = 13.dp, vertical = 10.dp)
        .clip(RoundedCornerShape(18.dp))
        .fillMaxWidth()
        .background(color=Color.Gray)
    ) {
        Box(modifier=Modifier.height(300.dp))
    }
}

@Composable
fun DownloadPill(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.height(32.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color(0xFF2A2A2A),
            contentColor   = Color(0xFF2F86FF)
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text("Скачать")
    }
}

@Composable
fun PromoBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF6C49FF),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = Color.White
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun GameSet(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text("Игровой набор", color = Color.White, fontSize = 20.sp)
            Spacer(Modifier.height(2.dp))
            Text("Собрали для вас лучшее", color = Color.Gray, fontSize = 14.sp)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }

    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(30) { index ->
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(360.dp)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(start=16.dp, end=0.dp, top=0.dp, bottom=0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Игра $index",
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Танчики $index",
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    PromoBadge(
                        text = "МЕСЯЦ ИГР",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("★ 5,0", color = Color(0xFFBDBDBD), fontSize = 13.sp)
                }

                Spacer(Modifier.width(8.dp))
                DownloadPill(onClick = { /* … */ })
            }
        }
    }
}

data class MenuTab(
    val title: String,
    val icon: ImageVector
)

@Composable
fun MenuLine(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val tabs = listOf(
        MenuTab("Интересное", Icons.Filled.Star),
        MenuTab("Приложения", Icons.Filled.AddCircle),
        MenuTab("Игры", Icons.Filled.Face),
        MenuTab("Киоск", Icons.Filled.ShoppingCart),
        MenuTab("Моё", Icons.Filled.AccountCircle),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
            .navigationBarsPadding()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { i, tab ->
            val color = if (i == selectedIndex) activeColor else inactiveColor

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onSelect(i) }
                    .padding(vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.title,
                    tint = color
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = tab.title,
                    color = color,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}
