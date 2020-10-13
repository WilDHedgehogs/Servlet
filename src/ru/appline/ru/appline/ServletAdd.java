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
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private AtomicInteger counter = new AtomicInteger(Model.getInstance().getFromList().size() + 1);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        String name = request.getParameter("name");
//        String surName = request.getParameter("surName");
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        User user = new User(name, surName, salary);
//
//        model.add(user, counter.getAndIncrement());
//
//        pw.print("<html>" +
//                "<h3>Пользователь " + name + " " + surName + " с зарплатой " + salary + " успешно создан! </h3>" +
//                "<a href=\"addUser.html\">Создать нового пользователя</a><br/>" +
//                "<a href=\"index.jsp\">Домой</a><br/>" +
//                "</html");
//
//
//
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
        String name = jObj.get("name").getAsString();
        String surName = jObj.get("surName").getAsString();
        double salary = jObj.get("salary").getAsDouble();
        User user = new User(name, surName, salary);
        model.add(user, counter.getAndIncrement());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(gson.toJson(model.getFromList()));

    }
}
