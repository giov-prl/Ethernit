package com.gionni.ethernit.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.util.*
import com.gionni.ethernit.data.AttackUiState



@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: AttackViewModel,
    myUiState: AttackUiState
){


    val uiState by viewModel.uiState.collectAsState()


    Column(
        modifier = modifier


    ){
       myUiState.interfaces.forEach{inter ->
           Row(

               verticalAlignment = Alignment.CenterVertically //TODO Modifier.selectable
           ){

               Text(inter)
           }

       }


    }
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()

    ) {
        Button(onClick = {
            viewModel.stopScan()
            viewModel.setList(LinkedList())
            viewModel.startScan()
        }) {
            Text("Scan")
        }
    }
}






