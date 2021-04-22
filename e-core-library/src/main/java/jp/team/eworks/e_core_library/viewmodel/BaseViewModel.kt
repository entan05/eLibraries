package jp.team.eworks.e_core_library.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import jp.team.eworks.e_core_library.activity.BaseActivity
import jp.team.eworks.e_core_library.fragment.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope {
    companion object {
        const val NO_SET_CODE: Int = -1
    }

    sealed class State {
        data class Loading(val code: Int = NO_SET_CODE): State()
        data class Loaded(val code: Int = NO_SET_CODE): State()
        data class LoadFailure(val code: Int = NO_SET_CODE, val cause: Throwable): State()
    }

    protected val state = MutableLiveData<State>()

    fun observeState(owner: BaseActivity, callee: (data: State) -> Unit) =
        state.observe(owner, Observer { data -> callee.invoke(data) })

    fun observeState(owner: BaseFragment, callee: (data: State) -> Unit) =
        state.observe(owner, Observer { data -> callee.invoke(data) })

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}
