package com.example.todoapproom.presentation.taskCompletedScreen

import app.cash.turbine.test
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.motherObject.MotherObjectTask
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksCompletedViewModelTest{

    private lateinit var viewModel: TasksCompletedViewModel

    @MockK
    private lateinit var repositoryCRUDImp: RepositoryCRUDImp

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        Dispatchers.setMain(dispatcher)

        // Simulamos el comportamiento de getTasks
        every { repositoryCRUDImp.getTasksCompleted() } returns flowOf(emptyList())

        viewModel = TasksCompletedViewModel(repositoryCRUDImp)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun `updateTask should update state with success message when task is updated successfully`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.updateTask(taskModel = MotherObjectTask.task) } returns MotherObjectTask.message

        // When
        viewModel.updateTask(MotherObjectTask.task)

        //Then
        viewModel.state.test {
            // Verificamos que el primer estado es de carga
            assertTrue(awaitItem().isLoading)

            // Avanzamos hasta que todas las coroutines se completen
            advanceUntilIdle()

            // Verificamos el estado final
            assertTrue(awaitItem().message == MotherObjectTask.message)

            // Simulamos que el Toast fue mostrado y llamamos a resetMessages
            viewModel.resetMessages()

            val resetState = awaitItem()
            assertNull(resetState.message) // Verificamos que el mensaje fue reseteado
            assertFalse(resetState.isLoading) // Verificamos que isLoading fue reseteado
        }
    }

    @Test
    fun `updateTask should update state with error message when an exception occurs during task update`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.updateTask(taskModel = MotherObjectTask.task) } throws Exception(MotherObjectTask.error)

        // When
        viewModel.updateTask(MotherObjectTask.task)

        //Then
        viewModel.state.test {
            // Verificamos que el primer estado es de carga
            assertTrue(awaitItem().isLoading)

            // Avanzamos hasta que todas las coroutines se completen
            advanceUntilIdle()

            // Verificamos el estado final
            assertTrue(awaitItem().message == MotherObjectTask.error)

            // Simulamos que el Toast fue mostrado y llamamos a resetMessages
            viewModel.resetMessages()

            val resetState = awaitItem()
            assertNull(resetState.message) // Verificamos que el mensaje fue reseteado
            assertFalse(resetState.isLoading) // Verificamos que isLoading fue reseteado
        }
    }


}