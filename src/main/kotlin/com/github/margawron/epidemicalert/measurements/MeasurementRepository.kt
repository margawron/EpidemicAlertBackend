package com.github.margawron.epidemicalert.measurements

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeasurementRepository : JpaRepository<Measurement, Long>