/*
 * Copyright (c) 2017. Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */

package pe.jsandoval.randomusers.inject

import pe.jsandoval.randomusers.inject.modules.AppModule

object Injector {

    private lateinit var appComponent: AppComponent

    @Synchronized
    fun init(appModule: AppModule) {
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build()
    }

    @Synchronized
    fun get(): AppComponent {
        return appComponent
    }
}