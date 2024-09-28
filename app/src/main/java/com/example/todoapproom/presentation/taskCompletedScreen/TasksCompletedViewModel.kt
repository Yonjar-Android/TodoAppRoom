package com.example.todoapproom.presentation.taskCompletedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.domain.repositories.IRepositoryCRUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksCompletedViewModel @Inject constructor
    (private val repositoryCRUDImp: IRepositoryCRUD) :
    ViewModel() {

    /* Estado de tareas que recibe un flow directo desde la base de datos
  por lo que si hay un cambio en la base de datos se actualizará automáticamente*/

    val taskList: Flow<PagingData<TaskModel>> = repositoryCRUDImp.getTasksCompleted()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(TaskCompletedState())
    val state: StateFlow<TaskCompletedState> = _state

    fun updateTask(taskModel: TaskModel){

        _state.update { _state.value.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                repositoryCRUDImp.updateTask(taskModel)

                resetMessages()

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