package com.github.margawron.epidemicalert.statistical

import com.github.margawron.epidemicalert.alerts.AlertService
import com.github.margawron.epidemicalert.suspects.SuspectService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RestController
class StatisticsController(
    private val alertService: AlertService,
    private val suspectService: SuspectService,
) {

    @GetMapping("statistics")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getStatisticsForLastMonth(): Map<String,StatisticsDto> {
        val today = LocalDate.now(ZoneOffset.UTC).atStartOfDay()
        val upperBound = today.plusDays(1)
        val lowerBound = today.minusMonths(1)
        val dayToStatistics = mutableMapOf<String, StatisticsDto>()
        var iterationLowerBound = lowerBound
        var iterationUpperBound = iterationLowerBound.plusDays(1)
        while (iterationUpperBound.plus(1, ChronoUnit.MILLIS).isBefore(upperBound)){
            val alertCount = alertService.getAlertCountBeforeTimestampAndAfterTimestamp(
                iterationLowerBound.toInstant(ZoneOffset.UTC),
                iterationUpperBound.toInstant(ZoneOffset.UTC))
            val suspectCount = suspectService.getCountOfSuspectsBetweenTimestamps(
                iterationLowerBound.toInstant(ZoneOffset.UTC),
                iterationUpperBound.toInstant(ZoneOffset.UTC))
            val dayString = iterationUpperBound.format(DateTimeFormatter.ISO_DATE)
            dayToStatistics[dayString] = StatisticsDto(suspectCount, alertCount)
            iterationLowerBound = iterationUpperBound
            iterationUpperBound = iterationLowerBound.plusDays(1)
        }
        return dayToStatistics
    }
}