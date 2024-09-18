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

    /* Estado de tareas que recibe un flow directo desde la base de datos
      por lo que si hay un cambio en la base de datos se actualizará automáticamente*/

    val taskList: StateFlow<List<TaskModel>> = repositoryCRUDImp.getTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList() // Valor inicial vacío
        )

    private val _state = MutableStateFlow(TaskScreenState())
    val state: StateFlow<TaskScreenState> = _state

    // Función para la creación de tareas

    fun createTask(taskModel: TaskModel) {
        loadingState()
        viewModelScope.launch {

            try {
                val response = repositoryCRUDImp.createTask(taskModel)
                _state.update {
                    if (response.contains("Error")){
                        _state.value.copy(
                            error = response
                        )
                    } else{
                        _state.value.copy(
                            successMessage = response
                        )
                    }

                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(
                        error = e.message
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

                _state.update {
                    if (response.contains("Error")){
                        _state.value.copy(error = response)
                    } else{
                        _state.value.copy(successMessage = response)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(error = e.message)
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
                    if (response.contains("Error")){
                        _state.value.copy(
                            successMessage = response
                        )
                    } else{
                        _state.value.copy(
                            error = response
                        )
                    }

                }
            } catch (e: Exception) {
                _state.update {
                    _state.value.copy(error = e.message)
                }
            }
        }
    }

    // Función para el reseteo del estado, mensajes y carga

    fun resetMessages() {
        _state.update {
            _state.value.copy(error = null, successMessage = null, isLoading = false)
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