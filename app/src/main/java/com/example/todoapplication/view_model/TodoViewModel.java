package com.example.todoapplication.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapplication.model.Todo;
import com.example.todoapplication.repository.TodoRepository;

import java.util.List;

import io.reactivex.Observable;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository todoRepository;
    private LiveData<Observable<List<Todo>>> todoLiveData;
    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository=new TodoRepository();
        this.todoLiveData=todoRepository.getTodos();
    }
    public LiveData<Observable<List<Todo>>> getTodoLiveData(){
        return todoLiveData;
    }
}
