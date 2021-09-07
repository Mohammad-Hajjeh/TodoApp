package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.adapter.TodoAdapter;
import com.example.todoapplication.model.ApiClient;
import com.example.todoapplication.model.Todo;
import com.example.todoapplication.service.TodoService;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    ListView listView;
    ProgressBar progressBar;
    private Realm realm;
    RealmResults<Todo> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        progressBar=findViewById(R.id.ProgressBar);
        ApiClient apiClient = new ApiClient();
        retrofit = apiClient.getRetrofit("http://61cf-185-114-120-49.ngrok.io/");
        Realm.init(MainActivity.this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("todos.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm.deleteRealm(realmConfig);
        realm = Realm.getDefaultInstance();
        todos = realm.where(Todo.class).findAll();
        if (todos != null && !todos.isEmpty()) {
            Log.d("REalm", "REalmCall");
            progressBar.setVisibility(View.INVISIBLE);
            TodoAdapter todoAdapter = new TodoAdapter(getBaseContext(), todos);
            listView.setAdapter(todoAdapter);
        } else {
            //Realm.deleteRealm(realmConfig);
            loadJson();
        }
    }

    void loadJson() {
        progressBar.setVisibility(View.VISIBLE);
        TodoService todoService = retrofit.create(TodoService.class);
        Observable<List<Todo>> todoObservable = todoService.getTodos();
        todoObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(List<Todo> todoList) {
        if (todoList != null && todoList.size() != 0) {
            List<Todo> parentNullList = new ArrayList<>();
            for(int i=0;i<todoList.size();i++){
                if(todoList.get(i).getParent_id()==-1){
                    parentNullList.add(todoList.get(i));
                }
            }
            listView = (ListView) findViewById(R.id.listView);
            TodoAdapter todoAdapter = new TodoAdapter(this, parentNullList);
            realm.beginTransaction();
            realm.copyToRealm(parentNullList);
            realm.commitTransaction();
            listView.setAdapter(todoAdapter);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, String.valueOf(todoList.size()), Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
        }
    }




    private void handleError(Throwable t) {

        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}