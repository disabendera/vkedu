package com.example.rustoretest

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rustoretest.ui.theme.RustoretestTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * Модель данных для страницы онбординга
 * @param title Заголовок страницы
 * @param description Описание страницы
 * @param imageRes Ресурс изображения для страницы
 */
data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

/**
 * Экран онбординга с перелистываемыми страницами
 * @param navController Контроллер навигации для перехода между экранами
 * @param modifier Модификатор компоновки
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Контекст приложения для доступа к SharedPreferences
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Список страниц онбординга с заголовками, описаниями и изображениями
    val pages = listOf(
        OnboardingPage(
            title = "Добро пожаловать в RuStore",
            description = "Откройте для себя мир качественных приложений от проверенных разработчиков",
            imageRes = R.drawable.rustore_logo
        ),
        OnboardingPage(
            title = "Гарантия безопасности",
            description = "Проверка приложений модерацией и современным антивирусом",
            imageRes = R.drawable.safety
        ),
        OnboardingPage(
            title = "Доступ к любым приложениям",
            description = "От мобильных банков до игровых хитов",
            imageRes = R.drawable.smartphone
        )
    )

    // Состояние пейджера для управления перелистыванием страниц
    val pagerState = rememberPagerState()
    // Coroutine scope для анимаций
    val scope = rememberCoroutineScope()

    // Эффект, который выполняется при первом показе онбординга
    LaunchedEffect(Unit) {
        // Сохраняем флаг, что онбординг был показан
        sharedPreferences.edit().putBoolean("onboarding_shown", true).apply()
    }

    // Основная поверхность экрана
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Отступ сверху для балансировки композиции
            Spacer(modifier = Modifier.height(32.dp))

            // Горизонтальный пейджер для перелистывания страниц
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier
                    .weight(1f) // Занимает все доступное пространство
                    .fillMaxWidth()
            ) { page ->
                // Колонка для контента каждой страницы
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Изображение страницы
                    Image(
                        painter = painterResource(id = pages[page].imageRes),
                        contentDescription = pages[page].title,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape) // Круглая форма изображения
                            .background(Color(0xFFF5F5F5)) // Светлый серый фон для иконок
                            .padding(32.dp), // Внутренние отступы
                        contentScale = ContentScale.Fit // Масштабирование изображения
                    )

                    // Отступ между изображением и текстом
                    Spacer(modifier = Modifier.height(48.dp))

                    // Заголовок страницы
                    Text(
                        text = pages[page].title,
                        color = Color.Black, // Черный текст
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Описание страницы
                    Text(
                        text = pages[page].description,
                        color = Color(0xFF666666), // Темно-серый текст
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp, // Межстрочный интервал
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // Индикатор прогресса (точки внизу)
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .height(16.dp),
                activeColor = Color(0xFF2F86FF), // Синий активный индикатор
                inactiveColor = Color(0xFFE0E0E0), // Светло-серый неактивный
                indicatorWidth = 12.dp,
                indicatorHeight = 12.dp,
                spacing = 8.dp // Расстояние между точками
            )

            // Основная кнопка действия
            Button(
                onClick = {
                    if (pagerState.currentPage == pages.size - 1) {
                        // Последняя страница - переход в основное приложение
                        navController.navigate("feed") {
                            // Удаляем онбординг из стека навигации
                            popUpTo("onboarding") { inclusive = true }
                        }
                    } else {
                        // Переход к следующей странице
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2F86FF), // Синяя кнопка
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp) // Закругленные углы
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Начать просмотр" else "Далее",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Кнопка пропуска (показывается только на первых двух страницах)
            if (pagerState.currentPage < pages.size - 1) {
                TextButton(
                    onClick = {
                        // Пропуск онбординга и переход в приложение
                        navController.navigate("feed") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Пропустить",
                        color = Color(0xFF666666), // Темно-серый текст
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/**
 * Обертка для онбординга, которая проверяет, нужно ли показывать онбординг
 * @param navController Контроллер навигации
 */
//@Composable
//fun OnboardingWrapper(
//    navController: NavController = rememberNavController()
//) {
//    val context = LocalContext.current
//    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//    val onboardingShown = sharedPreferences.getBoolean("onboarding_shown", false)
//
//    if (onboardingShown) {
//        // Если онбординг уже показывался, переходим к основному приложению
//        LaunchedEffect(Unit) {
//            navController.navigate("feed") {
//                popUpTo("onboarding") { inclusive = true }
//            }
//        }
//
//        // Показываем пустой контейнер, пока происходит навигация
//        Box(modifier = Modifier.fillMaxSize().background(Color.White))
//    } else {
//        // Показываем онбординг
//        OnboardingScreen(navController = navController)
//    }
//}

@Composable
fun OnboardingWrapper(
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val onboardingShown = sharedPreferences.getBoolean("onboarding_shown", false)
    OnboardingScreen(navController = navController)
}
/**
 * Preview функция для предпросмотра онбординга в Android Studio
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreenPreview() {
    RustoretestTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Для preview создаем временный navController
            OnboardingScreen(navController = rememberNavController())
        }
    }
}