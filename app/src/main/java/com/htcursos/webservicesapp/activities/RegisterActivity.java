package com.htcursos.webservicesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.htcursos.webservicesapp.R;
import com.htcursos.webservicesapp.api.ApiClient;
import com.htcursos.webservicesapp.models.Course;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RegisterActivity extends BaseActivity implements Callback<Response> {

    public static final String EDIT_KEY_COURSE = "edit_key_course";
    @Bind(R.id.course_name)
    EditText courseName;
    @Bind(R.id.btn_back)
    Button btn_back;

    Course courseToEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if(getIntent().getExtras() != null) {
             courseToEdit = (Course) getIntent().getExtras().getSerializable(EDIT_KEY_COURSE);
        }
        if(courseToEdit != null){
            courseName.setText(courseToEdit.getTitle());
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_save)
    public void saveCourse() {
        startLoading();
        Course course = new Course();
        course.setTitle(courseName.getText().toString());
        if(courseToEdit != null){
            course.setId(courseToEdit.getId());
            ApiClient.getClient().editCourse(course, this);

        }else{
            ApiClient.getClient().saveCourse(course, this);
        }
    }

    @Override
    public void success(Response response, Response response2) {
        finishLoading();
        Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failure(RetrofitError error) {
        finishLoading();
        Toast.makeText(this, "Erro ao salvar, tente novamente", Toast.LENGTH_SHORT).show();
    }
}
