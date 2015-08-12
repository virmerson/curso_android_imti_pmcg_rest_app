package com.htcursos.webservicesapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htcursos.webservicesapp.models.Course;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public class ApiClient {

    public static final String BASE_URL = "http://192.168.25.117:8082/webandroidapi";

    public static Routes sharedClient;

    public static Routes getClient() {
        if (sharedClient == null) {

            //Serializador Client  Json
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            //Inicializa o Rest Client
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(gson))
                    .setEndpoint(String.format("%s", BASE_URL))
                    .build();

            //Objeto rest
            sharedClient = restAdapter.create(Routes.class);
        }

        return sharedClient;
    }
    //Mapeamento das Requisições
    public interface Routes{

        @GET("/courses/list")
        public void getCourses(Callback<List<Course>> callback);

        @POST("/courses/insert")
        public void saveCourse(@Body Course course, Callback<Response> callback);

        @PUT("/courses/update")
        public void editCourse(@Body Course course, Callback<Response> callback);

        @DELETE("/courses/{id}")
        public void removeCourse(@Path("id") long remote_id, Callback<Response> callback);

    }

}
