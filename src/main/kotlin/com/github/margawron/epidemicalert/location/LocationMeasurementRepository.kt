package com.github.margawron.epidemicalert.location

import org.springframework.data.repository.PagingAndSortingRepository

interface LocationMeasurementRepository : PagingAndSortingRepository<LocationMeasurement, Long>