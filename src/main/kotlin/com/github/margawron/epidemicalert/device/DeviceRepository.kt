package com.github.margawron.epidemicalert.device

import org.springframework.data.repository.PagingAndSortingRepository

interface DeviceRepository : PagingAndSortingRepository<Device, Long>