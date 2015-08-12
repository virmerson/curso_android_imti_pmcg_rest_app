package com.htcursos.webservicesapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.htcursos.webservicesapp.R;
import com.htcursos.webservicesapp.adapters.CoursesListAdapter;
import com.htcursos.webservicesapp.api.ApiClient;
import com.htcursos.webservicesapp.models.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity  {

    @Bind(R.id.list)
    ListView listView;

    @Bind(R.id.btn_new)
    Button btnNew;

    List<Course> coursesList;

    CoursesListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final Course course = adapter.getItem(position);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Excluir curso")
                        .setMessage("Deseja realmente excluir este curso?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int i) {
                                startLoading();
                               //Chamando API para remover
                                ApiClient.getClient().removeCourse(course.getId(), new Callback<Response>() {
                                    @Override
                                    public void success(Response response, Response response2) {
                                        if (coursesList.contains(course)) {
                                            //Removendo da ListView
                                            coursesList.remove(course);
                                        }
                                        finishLoading();
                                        //Atualizar o layout
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor", Toast.LENGTH_SHORT).show();
                                        finishLoading();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Chamando nova activity passando um Objeto no Bundle para ser editado no form
                startActivity(new Intent(MainActivity.this, RegisterActivity.class).putExtra(RegisterActivity.EDIT_KEY_COURSE, adapter.getItem(position)));
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();

        //Limpando a lista
        if (coursesList != null) {
            coursesList.clear();
        }
        //Carregando os cursos
        ApiClient.getClient().getCourses(new Callback<List<Course>>() {
            @Override
            public void success(List<Course> courses, Response response) {
                if (coursesList == null) {
                    coursesList = new ArrayList<>();
                }
                coursesList =  courses;
                adapter = new CoursesListAdapter(MainActivity.this, coursesList);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
