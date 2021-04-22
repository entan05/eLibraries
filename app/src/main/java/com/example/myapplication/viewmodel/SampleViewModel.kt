package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import jp.team.eworks.e_core_library.activity.BaseActivity
import jp.team.eworks.e_core_library.viewmodel.BaseViewModel
import com.example.myapplication.api.GetSnacksApi
import kotlinx.coroutines.launch

class SampleViewModel: BaseViewModel() {

    private val snackApiResponse = MutableLiveData<String>()

    fun observeSnackApiResponse(owner: BaseActivity, callee: (data: String) -> Unit) =
        snackApiResponse.observe(owner, Observer { data -> callee.invoke(data) })

    fun getSnacks() = launch {
        val api = GetSnacksApi()
        state.postValue(State.Loading(api.apiCode))
        runCatching { api.get() }.fold(
            onSuccess = {
                state.postValue(State.Loaded(api.apiCode))
                snackApiResponse.postValue(it)
            },
            onFailure = {
                state.postValue(State.LoadFailure(api.apiCode, it))
            }
        )
    }
}
