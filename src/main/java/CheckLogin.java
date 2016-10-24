import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/checklogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckLogin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.getWriter().write("hello! , This is java backend for mobile cloud app. "
								+ "Post to /checklogin with user_name and user_password.");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//Parting the JSON in the request object.
		//The JSON in converted to a Map using GSON library.
		BufferedReader reader = request.getReader();
		Map httpRequestData = new Gson().fromJson(reader, Map.class);

		//Get user_name and password_password from the HTTP Request
		String user_name = (String) httpRequestData.get("user_name");
		String user_password = (String) httpRequestData.get("user_password");
		
		String responseBody = "";
		//if the request does not contain user_name
		// or user_password, the server cannot do much, can it?
		if(user_name == null || user_password == null) {
			responseBody = "{\"result\":\"error\", \"description\" : \"Credential missing\" }";
		}
		//This condition simply validates the user_name and
		//user_password against the actual credentials stored in environment variables
		else if(user_name.equals(System.getenv("APP_USER_NAME")) && 
				user_password.equals(System.getenv("APP_USER_PASSWORD"))) {
			//send a success message and also the record shows
			responseBody = "{\"result\":\"success\", \"description\" : \"You are outstanding\" }";
		}
		else{
			//well, user name and password did not match the actual credentials,
			//Send error message with the reason of failure.
			responseBody = "{\"result\":\"error\", \"description\" : \"Incorrect credentials\" }";
		}
		response.setContentType("text/html");
		response.getWriter().write(responseBody);
	}
}
