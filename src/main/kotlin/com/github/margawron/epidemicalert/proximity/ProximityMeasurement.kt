package com.github.margawron.epidemicalert.proximity

import com.github.margawron.epidemicalert.alerts.Alert
import com.github.margawron.epidemicalert.converters.ProximityTypeConverter
import com.github.margawron.epidemicalert.measurements.Measurement
import javax.persistence.*

@Entity
@Table(name = "t_proximity_measurements")
class ProximityMeasurement(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prx_id")
    val id: Long?,

    @JoinColumn(name = "prx_msr_id")
    @ManyToOne(targetEntity = Measurement::class)
    val measurement: Measurement,

    @JoinColumn(name = "prx_alr_id")
    @ManyToOne(targetEntity = Alert::class)
    val alert: Alert,

    @Column(name = "prx_type")
    @Convert(converter = ProximityTypeConverter::class)
    val proximityType: ProximityType
)