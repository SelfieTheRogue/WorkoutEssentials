package no.hiof.workoutessentials.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.hiof.workoutessentials.service.AccountService
import no.hiof.workoutessentials.service.impl.AccountServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule{
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl) : AccountService
}