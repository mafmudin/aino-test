package test.ainosi.aplikasiberita.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import test.ainosi.aplikasiberita.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher)
    )
}