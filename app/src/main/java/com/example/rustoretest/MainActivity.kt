package com.example.rustoretest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rustoretest.ui.theme.RustoretestTheme

private data class Tab(val title: String, val route: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RustoretestTheme {
                val navController = rememberNavController()

                // Проверяем, нужно ли показывать онбординг
                OnboardingWrapper(navController = navController)

                val tabs = remember {
                    listOf(
                        Tab("Интересное", "feed"),
                        Tab("Приложения", "apps"),
                        Tab("Игры", "games"),
                        Tab("Киоск", "kiosk"),
                        Tab("Моё", "profile")
                    )
                }

                var selected by rememberSaveable { mutableIntStateOf(0) }
                val backStack by navController.currentBackStackEntryAsState()

                LaunchedEffect(backStack) {
                    val route = backStack?.destination?.route
                    val idx = tabs.indexOfFirst { it.route == route }
                    if (idx >= 0 && idx != selected) selected = idx
                }
                Scaffold(
                    containerColor = Color(0xFF121212),
                    contentWindowInsets = WindowInsets.safeDrawing,
                    bottomBar = {
                        // Показываем нижнюю панель только если это не онбординг
                        if (backStack?.destination?.route != "onboarding") {
                            Surface(
                                color = Color(0xFF121212),
                                contentColor = contentColorFor(Color(0xFF121212)),
                                tonalElevation = 0.dp,
                                shadowElevation = 0.dp
                            ) {
                                MenuLine(
                                    selectedIndex = selected,
                                    onSelect = { i ->
                                        selected = i
                                        navController.navigate(tabs[i].route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .navigationBarsPadding()
                                        .height(56.dp),
                                    containerColor = Color(0xFF121212)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "onboarding", // Стартуем с онбординга
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(navController = navController)
                        }
                        composable("feed") {
                            InterestingScreen(Modifier.fillMaxSize())
                        }
                        composable("apps") {
                            AppsScreen()
                        }
                        composable("games") {
                            GamesScreen()
                        }
                        composable("kiosk") {
                            KioskScreen()
                        }
                        composable("profile") {
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InterestingScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        SearchBar()
        AdBox()
        GameSet()
    }
}
@Composable fun AppsScreen()   { Text("Приложения", color = Color.White)
}
@Composable fun GamesScreen()  { Text("Игры", color = Color.White) }
@Composable fun KioskScreen()  { Text("Киоск", color = Color.White) }
@Composable fun ProfileScreen(){ Text("Моё", color = Color.White) }
