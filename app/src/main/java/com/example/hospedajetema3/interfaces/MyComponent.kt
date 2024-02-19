package com.example.hospedajetema3.interfaces

import com.example.hospedajetema3.LoginActivity
import com.google.android.datatransport.runtime.dagger.Component

@Component(modules = [UserModule::class])
interface MyComponent {
    fun inject(loginActivity: LoginActivity)
}
