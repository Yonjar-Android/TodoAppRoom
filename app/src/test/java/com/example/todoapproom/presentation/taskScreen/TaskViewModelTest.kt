package com.example.todoapproom.presentation.taskScreen

import app.cash.turbine.test
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.motherObject.MotherObjectTask
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
class TaskViewModelTest{

    private lateinit var viewModel: TaskViewModel

    private val dispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var repositoryCRUDImp: RepositoryCRUDImp

    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        Dispatchers.setMain(dispatcher)

        // Simulamos el comportamiento de getTasks
        every { repositoryCRUDImp.getTasks() } returns flowOf(emptyList())

        viewModel = TaskViewModel(repositoryCRUDImp)

    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun `createTask should change the state of message if the function is success`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.createTask(taskModel = MotherObjectTask.task) } returns MotherObjectTask.message

        // When
        viewModel.createTask(MotherObjectTask.task)

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
    fun `createTask should change the state of message with an exception message error if an exception occurs`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.createTask(taskModel = MotherObjectTask.task) } throws Exception(MotherObjectTask.error)

        // When
        viewModel.createTask(MotherObjectTask.task)

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

    @Test
    fun `editTask should change the state of message if the function is success`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.updateTask(taskModel = MotherObjectTask.task) } returns MotherObjectTask.message

        // When
        viewModel.editTask(MotherObjectTask.task)

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
    fun `editTask should change the state of message with an exception message error if an exception occurs`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.updateTask(taskModel = MotherObjectTask.task) } throws Exception(MotherObjectTask.error)

        // When
        viewModel.editTask(MotherObjectTask.task)

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

    @Test
    fun `deleteTask should change the state of message if the function is success`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.deleteTask(taskModel = MotherObjectTask.task) } returns MotherObjectTask.message

        // When
        viewModel.deleteTask(MotherObjectTask.task)

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
    fun `delete should change the state of message with an exception message error if an exception occurs`() = runTest {

        //  Given
        coEvery { repositoryCRUDImp.deleteTask(taskModel = MotherObjectTask.task) } throws Exception(MotherObjectTask.error)

        // When
        viewModel.deleteTask(MotherObjectTask.task)

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

