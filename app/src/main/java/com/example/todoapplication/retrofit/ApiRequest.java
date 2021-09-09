package com.example.todoapplication.retrofit;

import com.example.todoapplication.model.Todo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("Todo")
    Observable<List<Todo>> getTodos();
}
