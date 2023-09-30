package com.chus.clua.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chus.clua.presentation.detail.NewsDetailScreenRoute
import com.chus.clua.presentation.news.NewsListScreenRoute
import com.chus.clua.presentation.webview.NewsWebViewScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LIST
    ) {
        composable(route = Routes.LIST) {
            NewsListScreenRoute(navController::navigateToDetail)
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument(Arguments.URL) {
                    type = NavType.StringType
                }
            )
        ) {
            NewsDetailScreenRoute(
                navController::popBackStack,
                navController::navigateToWebView
            )
        }
        composable(
            route = Routes.WEBVIEW,
            arguments = listOf(
                navArgument(Arguments.URL) {
                    type = NavType.StringType
                }
            )) { navBackStackEntry ->
            NewsWebViewScreen(
                url = navBackStackEntry.arguments?.getString(Arguments.URL),
                onCloseClick = navController::popBackStack,
            )
        }
    }
}

private fun NavController.navigateToDetail(url: String) {
    val encodedUrl = url.encode()
    navigate("detail/$encodedUrl")
}

private fun NavController.navigateToWebView(url: String) {
    val encodedUrl = url.encode()
    navigate("webview/$encodedUrl") {
        popUpTo(Routes.LIST)
    }
}

private fun String.encode() = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())