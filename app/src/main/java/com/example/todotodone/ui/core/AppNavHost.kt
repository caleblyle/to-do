package com.example.todotodone.ui.core

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todotodone.Screen
import com.example.todotodone.ui.projects.ProjectListScreen
import com.example.todotodone.ui.projectsandtasks.ProjectsAndTasksScreen
import com.example.todotodone.ui.tasks.TaskListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    //Should we use expanded or medium?
    val isExpanded by remember { mutableStateOf(windowSize == WindowSizeClass.Expanded) }
    val startDestination = if(isExpanded) Screen.ProjectsAndTasks.name else Screen.ProjectList.name

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Screen.ProjectList.name,
            enterTransition = { fadeIn() },
            popEnterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popExitTransition = { fadeOut() }
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
                slideInHorizontally(initialOffsetX = { 1500 }, animationSpec = springSpec)
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1500 }, animationSpec = springSpec)
            },
            popEnterTransition = null,
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1500 }, animationSpec = springSpec)
            }
        ) {
            val projectId = it.arguments?.getInt("projectId")
            TaskListScreen(
                projectId = projectId!!,
                onBackClick = { navController.popBackStack() },
                onDeleteProject = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ProjectsAndTasks.name
        ) {
            ProjectsAndTasksScreen()
        }
    }
}

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioLowBouncy)