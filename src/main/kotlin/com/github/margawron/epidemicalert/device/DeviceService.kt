package com.github.margawron.epidemicalert.device

import com.github.margawron.epidemicalert.users.LoginDto
import com.github.margawron.epidemicalert.users.User
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
) {

    @Transactional
    fun registerDeviceOrUpdateFirebaseToken(user: User, loginRequest:LoginDto): Device {
        val device = user.userDevices.find { device -> device.serialNumber == loginRequest.serialNumber && device.manufacturer == loginRequest.manufacturer}
        return if(device == null){
            val newDevice = Device(
                firebaseToken = loginRequest.fcmToken,
                manufacturer = loginRequest.manufacturer,
                deviceName = loginRequest.deviceName,
                deviceOwner = user,
                serialNumber = loginRequest.serialNumber,
            )
            deviceRepository.save(newDevice)
        }else {
            device.firebaseToken = loginRequest.fcmToken
            deviceRepository.save(device)
        }
    }

    @Transactional
    fun saveDevice(device:Device): Device {
        return deviceRepository.save(device)
    }
    
}