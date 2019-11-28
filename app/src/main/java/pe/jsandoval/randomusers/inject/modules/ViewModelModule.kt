package pe.jsandoval.randomusers.inject.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pe.jsandoval.randomusers.inject.ViewModelFactory
import pe.jsandoval.randomusers.inject.ViewModelKey
import pe.jsandoval.randomusers.presentation.main.MainViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun provideVM(viewModel: MainViewModel): ViewModel

}