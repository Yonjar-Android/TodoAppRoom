package com.example.todoapproom.presentation.taskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.domain.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repositoryCRUDImp: RepositoryCRUDImp
) :
    ViewModel() {

    val taskList: StateFlow<List<TaskModel>> = repositoryCRUDImp.getTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList() // Valor inicial vacío
        )

    private val _state = MutableStateFlow(TaskScreenState())
    val state: StateFlow<TaskScreenState> = _state

    fun createTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            repositoryCRUDImp.createTask(taskModel)
            _state.update {
                _state.value.copy(
                    successMessage = "Tarea creada correctamente"
                )
            }
        }
    }

    fun editTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            repositoryCRUDImp.updateTask(taskModel)

            _state.update {
                _state.value.copy(successMessage = "Se ha actualizado la tarea correctamente")
            }
        }
    }

    fun deleteTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            repositoryCRUDImp.deleteTask(taskModel)

            _state.update {
                _state.value.copy(
                    error = "Se ha eliminado la tarea correctamente"
                )
            }
        }
    }

    fun resetMessages() {
        _state.update {
            _state.value.copy(error = null, successMessage = null, isLoading = false)
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