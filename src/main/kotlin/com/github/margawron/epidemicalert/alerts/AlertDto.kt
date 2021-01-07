package com.github.margawron.epidemicalert.alerts

import com.github.margawron.epidemicalert.proximity.ProximityType

data class AlertDto(
    var id: Long,
    var proximityType: ProximityType
)