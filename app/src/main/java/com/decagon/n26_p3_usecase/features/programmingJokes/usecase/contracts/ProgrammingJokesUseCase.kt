package com.decagon.n26_p3_usecase.features.programmingJokes.usecase.contracts

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokesModelSafe
import kotlinx.coroutines.flow.Flow

interface ProgrammingJokesUseCase {
    suspend operator fun invoke() : Flow<Resource<JokesModelSafe>>
}