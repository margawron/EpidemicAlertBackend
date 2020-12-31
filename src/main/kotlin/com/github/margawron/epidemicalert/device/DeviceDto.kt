package com.github.margawron.epidemicalert.device

data class DeviceDto(
    var id: Long?,
    var manufacturer: String,
    var firebaseToken: String,
    var deviceName: String,
    var serialNumber: String,
    var deviceOwnerId: Long
) {
    companion object {
        fun fromDevice(device: Device) = DeviceDto(
            device.id,
            device.manufacturer,
            device.firebaseToken,
            device.deviceName,
            device.serialNumber,
            device.deviceOwner.id!!
        )
    }
}