package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/update")
public class ServletUpdate extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuffer jb = new StringBuffer();
        String line;
        try {

            BufferedReader reader = request.getReader();

            while ((line = reader.readLine()) != null) {

                jb.append(line);

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        JsonObject jObj = gson.fromJson(String.valueOf(jb), JsonObject.class);
        int id = jObj.get("id").getAsInt();
        String name = jObj.get("name").getAsString();
        String surName = jObj.get("surName").getAsString();
        double salary = jObj.get("salary").getAsDouble();
        User user = new User(name, surName, salary);

        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();

        if (id == 0) {

            JsonObject message = new JsonObject();
            message.addProperty("message", "Обновление всех пользователей не предусмотрено");
            pw.write(gson.toJson(message));

        } else if (id > 0) {

            if (id > model.getFromList().size()) {

                JsonObject message = new JsonObject();
                message.addProperty("message", "Такого пользователя нет");
                pw.write(gson.toJson(message));

            } else {

                model.update(user, id);
                pw.write(gson.toJson(model.getFromList()));

            }

        } else {

            JsonObject message = new JsonObject();
            message.addProperty("message", "ID должен быть больше нуля");
            pw.write(gson.toJson(message));

        }

    }
}
