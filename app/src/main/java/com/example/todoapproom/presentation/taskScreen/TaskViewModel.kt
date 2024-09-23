package com.example.todoapproom.presentation.taskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.domain.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    /* Estado de tareas que recibe un flow directo desde la base de datos
      por lo que si hay un cambio en la base de datos se actualizará automáticamente*/

    val taskList: Flow<PagingData<TaskModel>> = repositoryCRUDImp.getTasks()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(TaskScreenState())
    val state: StateFlow<TaskScreenState> = _state

    // Función para la creación de tareas

    fun createTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {

            try {
                val response = repositoryCRUDImp.createTask(taskModel)
                _state.update {
                    _state.value.copy(
                        message = response
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(
                        message = e.message
                    )
                }
            }
        }
    }

    // Función para la actualizacion de tareas

    fun editTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            try {
               val response = repositoryCRUDImp.updateTask(taskModel)

                if (!taskModel.isCompleted){
                    _state.update {
                        _state.value.copy(message = response)
                    }
                } else{
                    resetMessages()
                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(message = e.message)
                }
            }
        }
    }

    // Función para la eliminación de tareas

    fun deleteTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {
            try {
                val response = repositoryCRUDImp.deleteTask(taskModel)

                _state.update {
                    _state.value.copy(
                        message = response
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(message = e.message)
                }
            }
        }
    }

    // Función para el reseteo del estado, mensajes y carga

    fun resetMessages() {
        _state.update {
            _state.value.copy(message = null, isLoading = false)
        }
    }

    // Función para activar la pantalla de carga

    private fun loadingState() {
        _state.update {
            _state.value.copy(
                isLoading = true
            )
        }
    }
}