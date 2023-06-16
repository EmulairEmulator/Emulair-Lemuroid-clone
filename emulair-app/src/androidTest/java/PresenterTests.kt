package com.bigbratan

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PresenterTest {
    @Mock
    private val mockView: MyView? = null
    private var presenter: MyPresenter? = null
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MyPresenter(mockView)
    }

    @Test
    fun fetchData_Success() {
        setup()
        `when`(mockView.isNetworkAvailable()).thenReturn(true)
        presenter.fetchData()
        verify(mockView).showLoading()
        verify(mockView).hideLoading()
        verify(mockView).showData()
    }

    @Test
    fun fetchData_NoNetwork() {
        setup()
        `when`(mockView.isNetworkAvailable()).thenReturn(false)
        presenter.fetchData()
        verify(mockView).showLoading()
        verify(mockView).hideLoading()
        verify(mockView).showNoNetworkError()
    }
}
