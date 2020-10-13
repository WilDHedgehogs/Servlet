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
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();

        if (id == 0) {

            Iterator<Map.Entry<Integer, User>> iterator = model.getFromList().entrySet().iterator();
            while (iterator.hasNext()) {

                iterator.next();
                iterator.remove();

            }

            JsonObject message = new JsonObject();
            if (model.getFromList().size() == 0) {

                message.addProperty("message", "Все пользователи удалены");
                pw.write(gson.toJson(message));

            } else {

                message.addProperty("message", "Произошла ошибка при удалении");
                pw.write(gson.toJson(message));

            }

        } else if (id > 0) {

            if (id > model.getFromList().size()) {

                JsonObject message = new JsonObject();
                message.addProperty("message", "Такого пользователя нет");
                pw.write(gson.toJson(message));

            } else {

                model.delete(id);
                JsonObject message = new JsonObject();
                message.addProperty("message", "Пользователь с ID: " + id + " удален");
                pw.write(gson.toJson(message));

            }

        } else {

            JsonObject message = new JsonObject();
            message.addProperty("message", "ID должен быть больше нуля");
            pw.write(gson.toJson(message));

        }

    }
}
