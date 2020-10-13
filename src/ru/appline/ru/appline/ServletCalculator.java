package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calculate")
public class ServletCalculator extends HttpServlet {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
        double a = jObj.get("a").getAsDouble();
        double b = jObj.get("b").getAsDouble();
        String math = jObj.get("math").getAsString();

        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();

        String result;

        switch (math) {

            case "+":

                result = String.valueOf(a + b);
                break;

            case "-":

                result = String.valueOf(a - b);
                break;

            case "*":

                result = String.valueOf(a * b);
                break;

            case "/":

                result = String.valueOf(a / b);
                break;

            case "%":

                result = String.valueOf(a % b);
                break;

            default:

                result = "Неизвестная операция";
                break;

        }

        JsonObject message = new JsonObject();
        message.addProperty("result", result);
        pw.write(gson.toJson(message));

    }
}
