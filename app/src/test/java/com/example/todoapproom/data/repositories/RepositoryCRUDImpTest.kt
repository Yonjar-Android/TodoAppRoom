package com.example.todoapproom.data.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.todoapproom.data.mappers.TaskMapper
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.motherObject.MotherObjectTask
import com.example.todoapproom.utils.ResourceProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryCRUDImpTest{

    @MockK
    private lateinit var taskDao: TaskDao

    @MockK
    private lateinit var resourceProvider: ResourceProvider

    private lateinit var repositoryCRUDImp: RepositoryCRUDImp

    private val taskModel = MotherObjectTask.task
    private val roomTask = TaskMapper.toRoomModel(taskModel)

    private val errorMessage = MotherObjectTask.error

    private val finalErrorMessage = "Error: $errorMessage"

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        repositoryCRUDImp = RepositoryCRUDImp(taskDao,resourceProvider)

        every { resourceProvider.getString(2131492959) } returns "Tarea creada exitosamente"
        every { resourceProvider.getString(2131492961) } returns "Tarea actualizada exitosamente"
        every { resourceProvider.getString(2131492960) } returns "Tarea eliminada exitosamente"
    }

    @Test
    fun `getTasks should return a list of mapped TaskModels when successful`() = runTest {
        // Given: Datos de prueba y comportamiento simulado del DAO
        val expectedTaskList = MotherObjectTask.taskList

        // Simular la respuesta del DAO con la FakePagingSource
        coEvery { taskDao.getAll() } returns MotherObjectTask.FakePagingSource()

        // Crear el pager para manejar la paginación
        val pagingSource = MotherObjectTask.FakePagingSource()
        val pager = MotherObjectTask.TestPager(PagingConfig(pageSize = 25), pagingSource)

        // When: Llamada al método getTasks del repositorio
        val result = pager.refresh() as PagingSource.LoadResult.Page

        // Then: Verificación de que el resultado es correcto
        val mappedTasks = result.data.map { TaskMapper.fromRoomModel(it) }
        assertEquals(expectedTaskList, mappedTasks)
    }

    @Test
    fun `getTasksCompleted should return a list of mapped TaskModels when successful`() = runTest {
        // Given: Datos de prueba y comportamiento simulado del DAO
        val expectedTaskList = MotherObjectTask.taskList

        // Simular la respuesta del DAO con la FakePagingSource
        coEvery { taskDao.getTaskCompleted() } returns MotherObjectTask.FakePagingSource()

        // Crear el pager para manejar la paginación
        val pagingSource = MotherObjectTask.FakePagingSource()
        val pager = MotherObjectTask.TestPager(PagingConfig(pageSize = 25), pagingSource)

        // When: Llamada al método getTasks del repositorio
        val result = pager.refresh() as PagingSource.LoadResult.Page

        // Then: Verificación de que el resultado es correcto
        val mappedTasks = result.data.map { TaskMapper.fromRoomModel(it) }
        assertEquals(expectedTaskList, mappedTasks)
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