package com.michael.appverse.features.programmingJokes.usecase.contracts

import com.michael.appverse.commons.utils.Resource
import com.michael.appverse.features.programmingJokes.model.JokesModelSafe
import kotlinx.coroutines.flow.Flow

interface ProgrammingJokesUseCase {
    suspend operator fun invoke() : Flow<Resource<JokesModelSafe>>
}