package com.gionni.ethernit

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gionni.ethernit.ui.AttackScreen
import com.gionni.ethernit.ui.AttackViewModel
import com.gionni.ethernit.ui.ListScreen

enum class EthernitScreen() {
    List,
    Attack;
}

@Composable
fun EthernitAppBar(
    currentScreen: EthernitScreen,
    canNavigateBack: Boolean,
    navigateList: () -> Unit,
    navigateAttack: () -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        title = { "Ethernit" },
        modifier = modifier,
        navigationIcon = {
                IconButton(onClick = navigateList) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Back"
                    )
                }
                IconButton(onClick = navigateAttack) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Back"
                    )
                }

        }

    )
}



@Composable
fun EthernitApp(
    modifier: Modifier = Modifier,
    viewModel: AttackViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = EthernitScreen.valueOf(
        backStackEntry?.destination?.route ?: EthernitScreen.List.name
    )

    Scaffold(
        topBar = {
            EthernitAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateList = { navController.navigate(EthernitScreen.List.name)},
                navigateAttack = { navController.navigate(EthernitScreen.Attack.name)}
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = EthernitScreen.List.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = EthernitScreen.List.name) {
                ListScreen(list = uiState.interfaces)
            }
            composable(route = EthernitScreen.Attack.name) {
                val context = LocalContext.current
                AttackScreen(

                )
            }


        }
    }
}