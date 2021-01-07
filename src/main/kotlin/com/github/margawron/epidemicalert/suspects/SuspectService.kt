package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.pathogens.PathogenService
import com.github.margawron.epidemicalert.users.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuspectService(
    private val userService: UserService,
    private val pathogenService: PathogenService,
    private val suspectRepository: SuspectRepository,
    @Qualifier("alertingSystemTaskExecutor")
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor,
    private val applicationContext: ApplicationContext
) {

    fun createAndRunAlertingTask(dto: SuspectDto){
        val suspect = createSuspectFromDto(dto)
        val task = applicationContext.getBean(SuspectProximityAnalyzingTask::class.java)
        task.suspect = suspect
        enqueueTask(task)
    }

    @Transactional
    fun createSuspectFromDto(dto: SuspectDto): Suspect{
        val suspectedUser = userService.getUserById(dto.suspectId)
        val pathogen = pathogenService.getPathogenById(dto.pathogenId)
        val suspect = Suspect(
            null,
            dto.startTime,
            dto.suspicionLevel,
            suspectedUser,
            pathogen,
            mutableListOf()
        )
        return suspectRepository.save(suspect)
    }

    @Transactional
    fun saveSuspect(suspect: Suspect): Suspect {
        return suspectRepository.save(suspect)
    }

    private fun enqueueTask(task: SuspectProximityAnalyzingTask){
        threadPoolTaskExecutor.execute(task)
    }
}