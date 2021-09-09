package com.example.todoapplication.repository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.todoapplication.model.Todo;
import com.example.todoapplication.retrofit.ApiClient;
import com.example.todoapplication.retrofit.ApiRequest;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class TodoRepository {
    private ApiRequest apiRequest;
    private ApiClient apiClient;
    Retrofit retrofit;


    public TodoRepository(){
        apiClient=new ApiClient();
        retrofit = apiClient.getRetrofit("http://76d2-185-114-120-49.ngrok.io/");
    }
    public LiveData<Observable<List<Todo>>> getTodos(){
        final MutableLiveData<Observable<List<Todo>>> todos=new MutableLiveData<>();
        apiRequest = retrofit.create(ApiRequest.class);
        Observable<List<Todo>> todoObservable = apiRequest.getTodos();
        todos.setValue(todoObservable);
        return todos;
    }

}
