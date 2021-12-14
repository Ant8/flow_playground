package com.ant8.flowplayground.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Test
    fun test() = runBlockingTest {
        val testObject = MainViewModel()

        val toList = testObject.stateFlow
            .take(3)
            .toList()

        with(testObject) {
            sendIntent(MainViewModel.Intent.MINUS)
            sendIntent(MainViewModel.Intent.PLUS)
            sendIntent(MainViewModel.Intent.MINUS)
        }

        assert(toList.last() == -1)
    }
}