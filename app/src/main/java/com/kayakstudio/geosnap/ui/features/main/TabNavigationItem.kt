package com.kayakstudio.geosnap.ui.features.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NotListedLocation
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardNavigator
import com.kayakstudio.geosnap.ui.features.geoguesser.GeoGuesserNavigator
import com.kayakstudio.geosnap.ui.features.profile.ProfileNavigator

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title,
                )
            }
        },
        label = { Text(tab.options.title) },
    )
}

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_home)
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        DashboardNavigator()
    }
}

object GeoGuesserTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_geo_guess)
            val icon = rememberVectorPainter(Icons.AutoMirrored.Filled.NotListedLocation)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        GeoGuesserNavigator()
    }
}

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_profile)
            val icon = rememberVectorPainter(Icons.Default.Person)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        ProfileNavigator()
    }
}
