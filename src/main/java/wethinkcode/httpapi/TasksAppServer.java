package wethinkcode.httpapi;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.json.JsonMapper;
import org.jetbrains.annotations.NotNull;
import org.jsoup.select.Evaluator;

import java.util.Objects;

/**
 * Exercise 1
 * <p>
 * Application Server for the Tasks API
 */
public class TasksAppServer {
    private static final TasksDatabase database = new TasksDatabase();

    private final Javalin appServer;

    /**
     * Create the application server and configure it.
     */
    public TasksAppServer() {
        this.appServer = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.jsonMapper(createGsonMapper());

        });

        this.appServer.get("/tasks", this::getAllTasks);
        this.appServer.get("/task/{id}", this::getOneTask);
        this.appServer.post("/task", this::createTasks);
    }

    /**
     * Use GSON for serialisation instead of Jackson
     * because GSON allows for serialisation of objects without noargs constructors.
     *
     * @return A JsonMapper for Javalin
     */
    private static JsonMapper createGsonMapper() {
        Gson gson = new GsonBuilder().create();
        return new JsonMapper() {
            @NotNull
            @Override
            public String toJsonString(@NotNull Object obj) {
                return gson.toJson(obj);
            }

            @NotNull
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Class<T> targetClass) {
                return gson.fromJson(json, targetClass);
            }
        };
    }

    /**
     * Start the application server
     *
     * @param port the port for the app server
     */
    public void start(int port) {
        this.appServer.start(port);
    }

    /**
     * Stop the application server
     */
    public void stop() {
        this.appServer.stop();
    }

    /**
     * Get all tasks
     *
     * @param context the server context
     */
    private void createTasks(Context context) {
        Task Job = context.bodyAsClass(Task.class);
        if(this.database.add(Job)){
            context.header("Location", "/task/" + Job.getId());
            context.status(HttpCode.CREATED);
        }else{
            context.header("Location", "/task/" + Job.getId());
            context.status(HttpCode.BAD_REQUEST);
        }
        context.json(Job);
    }
    private void getOneTask(Context context) {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        context.contentType("application/json");
        Task job = database.get(id);
        if(job == null){
            throw new NotFoundResponse("404");
        }
        context.json(job);
    }
    private void getAllTasks(Context context) {
        context.contentType("application/json");
        context.json(database.all());
    }
}
