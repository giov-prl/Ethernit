package com.gionni.ethernit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    list : LinkedList<String>
){
    LazyColumn(
       modifier = modifier

    ){
       items(list){inter ->
           Row(
               verticalAlignment = Alignment.CenterVertically //TODO Modifier.selectable
           ){
               Text(inter)}
       }


    }
}

@Preview
@Composable
fun SelectOptionPreview(){
    val previewList = LinkedList<String>()
    previewList.add("first")
    previewList.add("second")
    previewList.add("third")
    ListScreen(
        list = previewList
    )
}
