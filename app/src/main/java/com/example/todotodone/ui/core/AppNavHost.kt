package com.example.todotodone.ui.core

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todotodone.Screen
import com.example.todotodone.ui.projects.ProjectListScreen
import com.example.todotodone.ui.tasks.TaskListScreen
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
        composable(
            route = Screen.ProjectList.name
        ) {
            ProjectListScreen(onProjectClick = { projectId -> navController.navigate("${Screen.TaskList.name}/$projectId") })
        }
        composable(
            route = "${Screen.TaskList.name}/{projectId}",
            arguments = listOf(
                navArgument("projectId") {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1500 }, animationSpec = springSpec )
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1500 }, animationSpec = springSpec )
            }
        ) {
            val projectId = it.arguments?.getInt("projectId")
            TaskListScreen(
                projectId = projectId!!,
                onBackClick = { navController.popBackStack() },
                onDeleteProject = { navController.popBackStack() }
            )
        }
    }
}

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioLowBouncy)