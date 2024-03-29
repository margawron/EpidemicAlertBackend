package com.github.margawron.epidemicalert.location

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.converters.LocationTypeConverter
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_locations")
data class Location(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long?,

    @Column(name = "loc_expiry_date")
    val expiryDate: Instant?,

    @Column(name = "loc_latitude")
    val latitude: Double,

    @Column(name = "loc_longitude")
    val longitude: Double,

    @Column(name = "loc_type")
    @Convert(converter = LocationTypeConverter::class)
    val locationType: LocationType,

    @Column(name = "loc_description")
    val description: String
)