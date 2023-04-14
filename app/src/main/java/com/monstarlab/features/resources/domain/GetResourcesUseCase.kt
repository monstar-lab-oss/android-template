package com.monstarlab.features.resources.domain

import com.monstarlab.core.extensions.suspendRunCatching
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetResourcesUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
) {

    suspend operator fun invoke() = suspendRunCatching {
        delay(2000)
        resourceRepository.getResources()
    }
}
