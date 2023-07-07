package com.gionni.ethernit.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.gionni.App
import com.gionni.ethernit.data.AttackUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

private val TAG = "OUTPUT"
class AttackViewModel : ViewModel() {

    private val _uiState =  MutableStateFlow(AttackUiState())
    val uiState: StateFlow<AttackUiState> = _uiState.asStateFlow()


    private val bluetoothManager: BluetoothManager = App.getContext()
        .getSystemService(ComponentActivity.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private val locationManager =
        App.getContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val applicationContext = App.getContext()

    val receiver = object : BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {

                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address

                    val list = LinkedList<String>()

                    if(!uiState.value.interfaces.contains(deviceHardwareAddress)) {
                        _uiState.update { currentState ->
                            if (deviceHardwareAddress != null && deviceName != null) {

                                list.addAll(currentState.interfaces)
                                list.add(deviceName)
                                list.add(deviceHardwareAddress)


                            }
                            currentState.copy(interfaces = list)
                        }
                    }

                    if (device != null) {
                        Log.i(TAG, device.address)
                    }


                }
            }
        }
    }

    fun setList(list: LinkedList<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                interfaces = list
            )

        }
    }

    fun addInterface(inter: String?) {
        val list = LinkedList<String>()
        _uiState.update { currentState ->

            list.addAll(currentState.interfaces)
            if (inter != null) {
                list.add(inter)
            }

            currentState.copy(interfaces = list)
        }

    }

    fun setGps(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.isGpsEnabled = true
            currentState
        }

    }



    private fun mockList(): LinkedList<String> {
        var list = LinkedList<String>()
        list.add("first")
        list.add("second")
        list.add("third")
        return list
    }

    private fun interfacesList() {

    }
    @SuppressLint("MissingPermission")
    fun stopScan() {

        if(bluetoothAdapter.isDiscovering){
            bluetoothAdapter.cancelDiscovery()
        }



    }


    @SuppressLint("MissingPermission")
    fun startScan() {


            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                setGps(false)
            else
                setGps(true)

            if (App.getContext() is Activity)
                ActivityCompat.requestPermissions(
                    App.getContext() as Activity,
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ),
                    0
                )

            if (bluetoothAdapter.startDiscovery()) {
                Log.i(TAG, "Wow it started")
            }
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            App.getContext().registerReceiver(receiver, filter)
            //registerReceiver(receiver1, IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED))


    }
}
