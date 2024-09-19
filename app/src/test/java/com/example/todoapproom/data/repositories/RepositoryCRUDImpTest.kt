package com.example.todoapproom.data.repositories

import app.cash.turbine.test
import com.example.todoapproom.data.mappers.TaskMapper
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.motherObject.MotherObjectTask
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepositoryCRUDImpTest{

    @MockK
    private lateinit var taskDao: TaskDao

    private lateinit var repositoryCRUDImp: RepositoryCRUDImp

    private val taskModel = MotherObjectTask.task
    private val roomTask = TaskMapper.toRoomModel(taskModel)

    private val errorMessage = MotherObjectTask.error

    private val finalErrorMessage = "Error: $errorMessage"

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        repositoryCRUDImp = RepositoryCRUDImp(taskDao)
    }

    @Test
    fun `getTasks should return a list of mapped TaskModels when successful`() = runTest {
        // Given: Datos de prueba y comportamiento simulado del DAO
        val taskRoomList = MotherObjectTask.taskRoomList

        val expectedTaskList = MotherObjectTask.taskList

        coEvery { taskDao.getAll() } returns flowOf(taskRoomList)

        // When: Llamada al método getTasks del repositorio
        val resultFlow = repositoryCRUDImp.getTasks()

        // Then: Verificación de que el resultado es correcto utilizando Turbine
        resultFlow.test {
            val result = awaitItem()
            assertEquals(expectedTaskList, result)
            awaitComplete()
        }
    }

    @Test
    fun `getTasks should return an empty list when an exception occurs`() = runTest {
        // Given: Simulación de un error al llamar a taskDao.getAll()
        val exception = RuntimeException("Database error")
        coEvery { taskDao.getAll() } throws exception

        // When: Llamada al método getTasks del repositorio
        val resultFlow = repositoryCRUDImp.getTasks()

        // Then: Verificación de que el flujo devuelve una lista vacía
        resultFlow.test {
            val result = awaitItem()
            assertTrue(result.isEmpty()) // Verifica que el resultado es una lista vacía
            awaitComplete()
        }
    }



    @Test
    fun `createTask - should return success message when task is inserted successfully`() = runTest {
        // Given
        coEvery { taskDao.insertTask(roomTask) } returns Unit

        val result = repositoryCRUDImp.createTask(taskModel)

        // Then
        assertEquals("Tarea creada exitosamente", result)

        // Verifica que el DAO fue llamado con el modelo correcto
        coVerify { taskDao.insertTask(roomTask) }
    }

    @Test
    fun `createTask - should return error message when exception occurs during task insertion`() = runTest {
        // Given
        coEvery { taskDao.insertTask(roomTask) } throws Exception(errorMessage)

        val result = repositoryCRUDImp.createTask(taskModel)

        // Then
        assertEquals(finalErrorMessage, result)

        // Verifica que el DAO fue llamado con el modelo correcto
        coVerify { taskDao.insertTask(roomTask) }
    }

    @Test
    fun `updateTask - should return success message when task is updated successfully`() = runTest {
        // Given
        coEvery { taskDao.updateTask(roomTask) } returns Unit

        val result = repositoryCRUDImp.updateTask(taskModel)

        // Then
        assertEquals("Tarea actualizada exitosamente", result)

        // Verifica que el DAO fue llamado con el modelo correcto
        coVerify { taskDao.updateTask(roomTask) }
    }

    @Test
    fun `updateTask - should return error message when exception occurs during task update`() = runTest {
        // Given
        coEvery { taskDao.updateTask(roomTask) } throws Exception(errorMessage)

        val result = repositoryCRUDImp.updateTask(taskModel)

        // Then
        assertEquals(finalErrorMessage, result)

        // Verifica que el DAO fue llamado con el modelo correcto
        coVerify { taskDao.updateTask(roomTask) }
    }

    @Test
    fun `deleteTask - should return success message when task is deleted successfully`() = runTest {
        // Given
        coEvery { taskDao.deleteTask(roomTask) } returns Unit

        val result = repositoryCRUDImp.deleteTask(taskModel)

        // Then
        assertEquals("Tarea eliminada exitosamente", result)

        // Verifica que el DAO fue llamado con el modelo correcto
        coVerify { taskDao.deleteTask(roomTask) }
    }

    @Test
    fun `deleteTask - should return error message when exception occurs during task deletion`() = runTest {
        // Given
        coEvery { taskDao.deleteTask(roomTask) } throws Exception(errorMessage)

        val result = repositoryCRUDImp.deleteTask(taskModel)

        // Then
        assertEquals(finalErrorMessage, result)

        // Verifica que el DAO fue llamado con el modelo correcto
        coVerify { taskDao.deleteTask(roomTask) }
    }

}