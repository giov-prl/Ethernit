package com.gionni.ethernit.data

import java.util.*

data class AttackUiState (
    val interfaces : LinkedList<String> = LinkedList(),
    var isGpsEnabled : Boolean = false,
    var isBluetoothEnabled : Boolean = false
        )