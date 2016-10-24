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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("hello! , This is java backend for mobile cloud app. Post to /checklogin with username and password.");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Parting the JSON in the request object.
		//The JSON in converted to a Map using gson library.
		BufferedReader reader = request.getReader();
		Map httpRequestData = new Gson().fromJson(reader, Map.class);
		
		//Get user and password from the HTTP Request
		String user = (String) httpRequestData.get("user");
		String password = (String) httpRequestData.get("password");
		
		String responseBody = "";
		//if the request does not contain username
	    // or password, the server cannot do much, can it?
		if(user == null || password == null) {
			responseBody = "{\"result\":\"error\", \"description\" : \"Credential missing\" }";
		}
		 //This condition simply validates the user name and
	     //password against the actual credentials stored in environment variables
		else if(user.equals(System.getenv("APP_USER_NAME")) && password.equals(System.getenv("APP_USER_PASSWORD"))) {
			//send a success message and also the record shows
	        //that the student has great record
			responseBody = "{\"result\":\"success\", \"description\" : \"You are outstanding\" }";
		}
		else{
			//well, user name and password did not match the actual credentials,
	        //it seems the user is not our student
	        //Send error message with the reason of failure.
			responseBody = "{\"result\":\"error\", \"description\" : \"Incorrect credentials\" }";
		}
		
		response.setContentType("text/html");
		response.getWriter().write(responseBody);
	}

}
