package com.gionni.ethernit.ui

import androidx.lifecycle.ViewModel
import com.gionni.ethernit.data.AttackUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*


class AttackViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AttackUiState(interfaces = mockList()))
    val uiState: StateFlow<AttackUiState> = _uiState.asStateFlow()


    fun setList(list: LinkedList<String>){
        _uiState.update {currentState ->
            currentState.copy(
                interfaces = list
            )

        }
    }


    private fun mockList() : LinkedList<String>{
        var list = LinkedList<String>()
        list.add("first")
        list.add("second")
        list.add("third")
        return list
    }
}