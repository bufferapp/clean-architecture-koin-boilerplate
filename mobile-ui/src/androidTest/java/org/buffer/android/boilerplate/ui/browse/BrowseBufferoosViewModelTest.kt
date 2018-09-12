package org.buffer.android.boilerplate.ui.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.browse.interactor.GetBufferoos
import org.buffer.android.boilerplate.ui.test.util.BufferooFactory
import org.buffer.android.boilerplate.ui.test.util.DataFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class BrowseBufferoosViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()
    val mockGetBufferoos = mock<GetBufferoos>()

    private val bufferoosViewModel = BrowseBufferoosViewModel(mockGetBufferoos)

    //<editor-fold desc="Success">
    @Test
    fun getBufferoosReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list))
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value is BrowseState.Success)
    }

    @Test
    fun getBufferoosReturnsDataOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list))
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == list)
    }

    @Test
    fun getBufferoosReturnsNoMessageOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list))

        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.errorMessage == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getBufferoosReturnsError() {
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Flowable.error(RuntimeException()))

        assert(bufferoosViewModel.getBufferoos().value is BrowseState.Error)
    }

    @Test
    fun getBufferoosFailsAndContainsNoData() {
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Flowable.error(RuntimeException()))

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Flowable.error(RuntimeException(errorMessage)))

        assert(bufferoosViewModel.getBufferoos().value?.errorMessage == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getBufferoosReturnsLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value is BrowseState.Loading)
    }

    @Test
    fun getBufferoosContainsNoDataWhenLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosContainsNoMessageWhenLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }
    //</editor-fold>

    private fun stubGetBufferoos(flowable: Flowable<List<Bufferoo>>) {
        whenever(mockGetBufferoos.execute(any()))
                .thenReturn(flowable)
    }
}