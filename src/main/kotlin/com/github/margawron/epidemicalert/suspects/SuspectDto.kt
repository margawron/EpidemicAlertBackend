package com.github.margawron.epidemicalert.suspects

import java.time.Instant

data class SuspectDto(
    var id: Long?,
    var startTime: Instant,
    var suspicionLevel: SuspicionLevel,
    var suspectId: Long,
    var pathogenId: Long
)