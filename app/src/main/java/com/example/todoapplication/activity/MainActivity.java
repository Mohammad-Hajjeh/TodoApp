package com.example.todoapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todoapplication.R;
import com.example.todoapplication.adapter.TodoAdapter;
import com.example.todoapplication.model.ApiClient;
import com.example.todoapplication.model.Todo;
import com.example.todoapplication.service.TodoService;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
    List<Todo> todosNullParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        progressBar=findViewById(R.id.ProgressBar);
        ApiClient apiClient = new ApiClient();
        retrofit = apiClient.getRetrofit("http://a4ed-185-114-120-49.ngrok.io/");
        Realm.init(MainActivity.this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("todo.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
//        Realm.deleteRealm(realmConfig);
        realm = Realm.getDefaultInstance();
        todos = realm.where(Todo.class).findAll();
        todosNullParent=new ArrayList<>();
        if (todos != null && !todos.isEmpty()) {
            Log.d("REalm", "REalmCall");
            progressBar.setVisibility(View.INVISIBLE);
            for(int i=0;i<todos.size();i++){
                if(todos.get(i).getParent_id()==-1){
                    todosNullParent.add(todos.get(i));
                }
            }
            TodoAdapter todoAdapter = new TodoAdapter(getBaseContext(), todosNullParent);
            listView.setAdapter(todoAdapter);
        } else {
            Realm.deleteRealm(realmConfig);
            loadJson();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                List<Todo> chosenTodos = new ArrayList<>();
                Todo todo = (Todo) adapterView.getItemAtPosition(position);
                for (int i = 0; i < todos.size(); i++) {
                    if(todos.get(i).getParent_id()==todo.getId()){
                        chosenTodos.add(todos.get(i));
                    }
                }
                TodoAdapter todoAdapter = new TodoAdapter(getBaseContext(), chosenTodos);
                listView.setAdapter(todoAdapter);
                Toast.makeText(MainActivity.this, String.valueOf(chosenTodos.size()), Toast.LENGTH_SHORT).show();
//                Intent intent = getIntent();
//                Uri deeplink = intent.getData();
//                imageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });


//                // Parse the deeplink and take the adequate action
//                if (deeplink != null) {
//                    parseDeepLink(deeplink);
//                }
            }
        });


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
            TodoAdapter todoAdapter = new TodoAdapter(this, parentNullList);
            realm.beginTransaction();
            realm.copyToRealm(todoList);
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
    private void parseDeepLink(Uri deeplink) {
        // The path of the deep link, e.g. '/products/123?coupon=save90'
        String path = deeplink.getPath();

        if (path.startsWith("/Todo")) {
            // Handles a product deep link
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("id", deeplink.getLastPathSegment()); // 123
            intent.putExtra("coupon", deeplink.getQueryParameter("coupon")); // save90
            startActivity(intent);
        }
    }
}