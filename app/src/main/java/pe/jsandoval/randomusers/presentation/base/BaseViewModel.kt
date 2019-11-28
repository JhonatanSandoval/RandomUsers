package pe.jsandoval.randomusers.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope by DefaultScope() {

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        try {
            cancel()
        } catch (e: IllegalStateException) {
            Timber.e(e, "The BaseViewModel CoroutineScope does not have an associated job to cancel...")
        }
        super.onCleared()
    }

}

fun DefaultScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.Default)
internal class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}