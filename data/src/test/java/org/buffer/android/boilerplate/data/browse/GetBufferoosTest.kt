package org.buffer.android.boilerplate.data.browse

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.browse.interactor.GetBufferoos
import org.buffer.android.boilerplate.data.executor.PostExecutionThread
import org.buffer.android.boilerplate.data.executor.ThreadExecutor
import org.buffer.android.boilerplate.data.repository.BufferooRepository
import org.buffer.android.boilerplate.data.test.factory.BufferooFactory
import org.junit.Test

class GetBufferoosTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val mockBufferooRepository = mock<BufferooRepository>()

    private val getBufferoos = GetBufferoos(mockBufferooRepository, mockThreadExecutor,
            mockPostExecutionThread)

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getBufferoos.buildUseCaseObservable(null)
        verify(mockBufferooRepository).getBufferoos()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubBufferooRepositoryGetBufferoos(Flowable.just(BufferooFactory.makeBufferooList(2)))
        val testObserver = getBufferoos.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val bufferoos = BufferooFactory.makeBufferooList(2)
        stubBufferooRepositoryGetBufferoos(Flowable.just(bufferoos))
        val testObserver = getBufferoos.buildUseCaseObservable(null).test()
        testObserver.assertValue(bufferoos)
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Flowable<List<Bufferoo>>) {
        whenever(mockBufferooRepository.getBufferoos())
                .thenReturn(single)
    }

}