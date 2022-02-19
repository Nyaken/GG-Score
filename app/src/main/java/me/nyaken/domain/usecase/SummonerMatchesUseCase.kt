package me.nyaken.domain.usecase

import dagger.Reusable
import me.nyaken.domain.repository.SummonerRepository
import javax.inject.Inject

@Reusable
class SummonerMatchesUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
){

    operator fun invoke(
        user: String,
        lastMatch: Long?
    ) = summonerRepository.summonerMatches(user, lastMatch)

}