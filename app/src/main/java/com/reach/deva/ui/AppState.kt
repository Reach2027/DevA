/*
 * Copyright 2023 Reach2027
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reach.deva.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.reach.deva.feature.task.navigation.ROUTE_TASK
import com.reach.deva.feature.task.navigation.navigateToTask
import com.reach.deva.feature.translate.navigation.navigateToTranslate
import com.reach.deva.navigation.TopLevelDestination

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        windowSizeClass,
        navController,
    ) {
        AppState(
            windowSizeClass,
            navController,
        )
    }
}

@Stable
class AppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            ROUTE_TASK -> TopLevelDestination.TASK
            else -> null
        }

    val topLevelDestinations = TopLevelDestination.values().asList()

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation:  ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                TopLevelDestination.TASK -> navController.navigateToTask(topLevelNavOptions)
                TopLevelDestination.TRANSLATE -> navController.navigateToTranslate(topLevelNavOptions)
            }
        }
    }
}
