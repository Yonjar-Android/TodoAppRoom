package com.example.todoapproom.motherObject

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todoapproom.data.mappers.TaskMapper
import com.example.todoapproom.data.models.TaskRoomModel
import com.example.todoapproom.domain.model.TaskModel

object MotherObjectTask {

    const val message = "A success message"
    const val error = "An error Message"

    val task = TaskModel(
        taskId = 1,
        creationDate = 1000L,
        taskName = "Programar Python",
        isCompleted = false,
        completedDate = 500000L
    )

    val task2 = TaskModel(
        taskId = 2,
        creationDate = 3000L,
        taskName = "Programar SQL",
        isCompleted = true,
        completedDate = 100000L
    )

    val taskList = listOf(task, task2)

    val taskRoomList = listOf(TaskMapper.toRoomModel(task), TaskMapper.toRoomModel(task2))

    class FakePagingSource(private val shouldThrowException: Boolean = false) : PagingSource<Int, TaskRoomModel>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskRoomModel> {
            return if (shouldThrowException) {
                LoadResult.Error(RuntimeException("Database error"))
            } else {
                LoadResult.Page(
                    data = taskRoomList, // Datos de prueba
                    prevKey = null,
                    nextKey = null
                )
            }
        }

        override fun getRefreshKey(state: PagingState<Int, TaskRoomModel>): Int? {
            return state.anchorPosition
        }
    }

    class TestPager<T : Any>(
        private val config: PagingConfig,
        private val pagingSource: PagingSource<Int, T>
    ) {
        suspend fun refresh(): PagingSource.LoadResult<Int, T> {
            return pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = config.initialLoadSize,
                    placeholdersEnabled = false
                )
            )
        }
    }
}