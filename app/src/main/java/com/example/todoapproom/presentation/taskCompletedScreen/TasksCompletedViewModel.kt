package com.example.todoapproom.presentation.taskCompletedScreen

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
class TasksCompletedViewModel @Inject constructor
    (private val repositoryCRUDImp: RepositoryCRUDImp) :
    ViewModel() {

    /* Estado de tareas que recibe un flow directo desde la base de datos
  por lo que si hay un cambio en la base de datos se actualizará automáticamente*/
    val taskList: StateFlow<List<TaskModel>> = repositoryCRUDImp.getTasksCompleted()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList() // Valor inicial vacío
        )

    private val _state = MutableStateFlow(TaskCompletedState())
    val state: StateFlow<TaskCompletedState> = _state

    fun updateTask(taskModel: TaskModel){

        _state.update { _state.value.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val response = repositoryCRUDImp.updateTask(taskModel)

                if (response.isNotBlank()){
                    _state.update { _state.value.copy(message = response) }
                }
            } catch (e:Exception){
                _state.update { _state.value.copy(message = e.message) }
            }
        }
    }

    fun resetMessages() {
        _state.update {
            _state.value.copy(message = null, isLoading = false)
        }
    }
}