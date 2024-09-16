package com.example.todoapproom.presentation.taskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.domain.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repositoryCRUDImp: RepositoryCRUDImp
) :
    ViewModel() {

    private val _state = MutableStateFlow<TaskScreenState>(TaskScreenState.Initial)
    val state: StateFlow<TaskScreenState> = _state

    private val _taskList = MutableStateFlow<List<TaskModel>>(listOf())
    val taskList: StateFlow<List<TaskModel>> = _taskList


    fun getTask() {
        loadingState()
        viewModelScope.launch {
            val list = repositoryCRUDImp.getTasks()
            if (list.isNotEmpty()) {
                _state.value = TaskScreenState.Success("Tasks found")
                _taskList.value = list
            } else {
                _state.value = TaskScreenState.Error("There is no tasks")
                _taskList.value = list
            }
        }
    }

    fun createTask(taskModel: TaskModel){
        viewModelScope.launch {
            repositoryCRUDImp.createTask(taskModel)
            getTask()
        }
    }

    fun editTask(taskModel: TaskModel){
        viewModelScope.launch {
            repositoryCRUDImp.updateTask(taskModel)
            getTask()
            _state.value = TaskScreenState.Success("Se ha actualizado la tarea correctamente")
        }
    }

    fun deleteTask(taskModel: TaskModel){
        viewModelScope.launch {
            repositoryCRUDImp.deleteTask(taskModel)
            getTask()
            _state.value = TaskScreenState.Error("Se ha eliminado la tarea correctamente")
        }
    }

    fun resetState() {
        _state.value = TaskScreenState.Initial
    }

    private fun loadingState() {
        _state.value = TaskScreenState.Loading
    }
}