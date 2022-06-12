package com.example.todotodone.ui.core

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todotodone.Screen
import com.example.todotodone.ui.projects.ProjectListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.ProjectList.name,
        modifier = modifier
    ) {
        composable(Screen.ProjectList.name) {
            ProjectListScreen(onProjectClick = { name -> navController.navigate("${Screen.TaskList.name}/$name") })
        }
        composable(
            route = "${Screen.TaskList.name}/{projectName}",
            arguments = listOf(
                navArgument("projectName") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec )
            }
        ) {
            val projectName = it.arguments?.getString("projectName")
            Text(text = projectName!!)
        }
    }
}

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)