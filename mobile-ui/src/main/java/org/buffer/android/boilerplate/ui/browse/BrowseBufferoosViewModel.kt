package org.buffer.android.boilerplate.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import org.buffer.android.boilerplate.data.browse.interactor.GetBufferoos

class BrowseBufferoosViewModel(val getBufferoos: GetBufferoos) : ViewModel() {

    private val bufferoosLiveData: MutableLiveData<BrowseState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getBufferoos(): LiveData<BrowseState> {
        return bufferoosLiveData
    }

    fun fetchBufferoos() {
        bufferoosLiveData.postValue(BrowseState.Loading)
        disposable = getBufferoos.execute()
                .subscribe({
                    bufferoosLiveData.postValue(BrowseState.Success(it))
                }, {
                    bufferoosLiveData.postValue(BrowseState.Error (it.message))
                })
    }
}