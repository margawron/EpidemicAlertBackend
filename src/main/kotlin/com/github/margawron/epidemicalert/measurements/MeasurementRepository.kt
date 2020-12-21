package com.github.margawron.epidemicalert.measurements

import org.springframework.data.repository.PagingAndSortingRepository

interface MeasurementRepository : PagingAndSortingRepository<Measurement, Long>