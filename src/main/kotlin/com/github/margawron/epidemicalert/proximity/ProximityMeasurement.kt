package com.github.margawron.epidemicalert.proximity

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.alerts.Alert
import com.github.margawron.epidemicalert.converters.ProximityTypeConverter
import com.github.margawron.epidemicalert.measurements.Measurement
import javax.persistence.*

@Entity
@Table(name = "t_proximity_measurements")
data class ProximityMeasurement(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prx_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long?,

    @JoinColumn(name = "prx_msr_id")
    @OneToOne
    val measurement: Measurement,

    @JoinColumn(name = "prx_alr_id")
    @ManyToOne(targetEntity = Alert::class)
    val alert: Alert,

    @Column(name = "prx_type")
    @Convert(converter = ProximityTypeConverter::class)
    val proximityType: ProximityType
)