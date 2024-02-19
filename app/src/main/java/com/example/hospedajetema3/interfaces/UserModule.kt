package com.example.hospedajetema3.interfaces

import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides

@Module
class UserModule {
    @Provides
    fun provideUserManager(): UserManager {
        return UserManagerImpl()
    }
}
