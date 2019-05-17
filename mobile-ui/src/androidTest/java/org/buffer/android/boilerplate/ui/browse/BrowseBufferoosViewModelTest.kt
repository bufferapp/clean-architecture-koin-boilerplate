package org.buffer.android.boilerplate.ui.browse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.browse.interactor.GetBufferoos
import org.buffer.android.boilerplate.ui.browse.BrowseState.Loading
import org.buffer.android.boilerplate.ui.browse.BrowseState.Success
import org.buffer.android.boilerplate.ui.browse.BrowseState.Error
import org.buffer.android.boilerplate.ui.test.util.BufferooFactory
import org.buffer.android.boilerplate.ui.test.util.DataFactory
import org.junit.*
import java.util.concurrent.TimeUnit

class BrowseBufferoosViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val mockGetBufferoos = mock<GetBufferoos>()

    private val bufferoosViewModel = BrowseBufferoosViewModel(mockGetBufferoos)

    //<editor-fold desc="Success">
    @Test
    fun getBufferoosReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value is Success)
    }

    @Test
    fun getBufferoosReturnsDataOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertSame(bufferoosViewModel.getBufferoos().value?.data, list)
    }

    @Test
    fun getBufferoosReturnsNoMessageOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value?.errorMessage == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getBufferoosReturnsError() {
        stubGetBufferoos(Flowable.error(RuntimeException()))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value is Error)
    }

    @Test
    fun getBufferoosFailsAndContainsNoData() {
        stubGetBufferoos(Flowable.error(RuntimeException()))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        stubGetBufferoos(Flowable.error(RuntimeException(errorMessage)))
        bufferoosViewModel.fetchBufferoos()

        val result = bufferoosViewModel.getBufferoos().value
        Assert.assertTrue(result is Error && result.errorMessage == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getBufferoosReturnsLoading() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list).delay(60, TimeUnit.SECONDS))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value is Loading)
    }

    @Test
    fun getBufferoosContainsNoDataWhenLoading() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Flowable.just(list).delay(60, TimeUnit.SECONDS))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosContainsNoMessageWhenLoading() {
        Assert.assertTrue(bufferoosViewModel.getBufferoos().value?.data == null)
    }
    //</editor-fold>

    private fun stubGetBufferoos(single: Flowable<List<Bufferoo>>) {
        // Note: do not use mockGetBufferoos.execute(any()), it will not work. If there are no parameters, that is
        // use case params is Void, do not use any()
        whenever(mockGetBufferoos.execute())
            .thenReturn(single)
    }
}