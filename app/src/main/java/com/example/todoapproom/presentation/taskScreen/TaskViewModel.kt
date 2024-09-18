package com.example.todoapproom.presentation.taskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.domain.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repositoryCRUDImp: RepositoryCRUDImp
) :
    ViewModel() {

    private val _state = MutableStateFlow(TaskScreenState())
    val state: StateFlow<TaskScreenState> = _state

    init {
        getTask()
    }

    private fun getTask() {
        loadingState()
        viewModelScope.launch {
            val list = repositoryCRUDImp.getTasks()

            _state.update {
                if (list.isNotEmpty()) {
                    _state.value.copy(tasks = list)
                } else {
                    _state.value.copy(tasks = list, error = "There is no tasks")
                }
            }
        }
    }

    fun createTask(taskModel: TaskModel) {
        viewModelScope.launch {
            repositoryCRUDImp.createTask(taskModel)
            getTask()
        }
    }

    fun editTask(taskModel: TaskModel) {
        viewModelScope.launch {
            repositoryCRUDImp.updateTask(taskModel)
            getTask()

            _state.update {
                _state.value.copy(successMessage = "Se ha actualizado la tarea correctamente")
            }
        }
    }

    fun deleteTask(taskModel: TaskModel) {
        viewModelScope.launch {
            repositoryCRUDImp.deleteTask(taskModel)
            getTask()

            _state.update {
                _state.value.copy(
                    error = "Se ha eliminado la tarea correctamente"
                )
            }
        }
    }

    fun resetMessages() {
        _state.update {
            _state.value.copy(error = null, successMessage = null)
        }
    }

    private fun loadingState() {
        _state.update {
            _state.value.copy(
                isLoading = true
            )
        }
    }
}