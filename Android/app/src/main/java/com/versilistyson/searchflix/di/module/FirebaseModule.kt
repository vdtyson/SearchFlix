package com.versilistyson.searchflix.di.module

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FirebaseModule {
    @JvmStatic @Singleton
    @Provides
    fun provideCrashylitics(): FirebaseCrashlytics =
        FirebaseCrashlytics.getInstance()

    @JvmStatic @Singleton
    @Provides
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)
}