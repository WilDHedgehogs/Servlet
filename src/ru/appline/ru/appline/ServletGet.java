package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/get")
public class ServletGet extends HttpServlet {

        Model model = Model.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        if (id == 0) {
//
//            pw.print("<html>" +
//                    "<h3>Доступные пользователи:</h3><br/>" +
//                    "ID пользователя: " +
//                    "<ul>");
//
//            for (Map.Entry<Integer, User> pair: model.getFromList().entrySet()) {
//
//                pw.print("<li>" + pair.getKey() + "</li>" +
//                        "<ul>" +
//                            "<li>Имя: " + pair.getValue().getName() + "</li>" +
//                            "<li>Фамилия: " + pair.getValue().getSurName() + "</li>" +
//                            "<li>Зарплата: " + pair.getValue().getSalary() + "</li>" +
//                        "</ul>");
//
//            }
//            pw.print("</ul>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//
//        } else if (id > 0) {
//
//            if (id > model.getFromList().size()) {
//
//                pw.print("<html>" +
//                        "<h3>Такого пользователя нет</h3>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//
//            } else {
//
//                pw.print("<html>" +
//                        "<h3>Запрошенный пользователь</h3>" +
//                        "<br/>" +
//                        "Имя: " + model.getFromList().get(id).getName() + "<br/>" +
//                        "Фамилия: " + model.getFromList().get(id).getSurName() + "<br/>" +
//                        "Зарплата: " + model.getFromList().get(id).getSalary() + "<br/>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//
//            }
//
//        } else {
//
//            pw.print("<html>" +
//                    "<h3>ID должен быть больше нуля</h3>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//
//        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

                pw.write(gson.toJson(model.getFromList()));

            } else if (id > 0) {

                if (id > model.getFromList().size()) {

                    JsonObject message = new JsonObject();
                    message.addProperty("message", "Такого пользователя нет");
                    pw.write(gson.toJson(message));

                } else {

                    pw.write(gson.toJson(model.getFromList().get(id)));

                }

            } else {

                JsonObject message = new JsonObject();
                message.addProperty("message", "ID должен быть больше нуля");
                pw.write(gson.toJson(message));

            }

    }
}
