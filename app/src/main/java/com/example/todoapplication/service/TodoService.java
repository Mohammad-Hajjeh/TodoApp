package com.example.todoapplication.service;

import com.example.todoapplication.model.Todo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TodoService {
    @GET("Todo")
    Observable<List<Todo>> getTodos();
}
