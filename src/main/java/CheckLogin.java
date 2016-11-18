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
	
	 /* Function to validate the user credentials
    This function is accessible by the /checklogin URI, relative to the server ip address
    The function accepts a "Request" object  sent by any client by POST method. The user credential
    is passed in the body of the request as JSON object.
    The function checks the user credential and prepares another JSON object with required data for 
    client-side processing.*/   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//Parting the JSON in the request object.
		//The JSON in converted to a Map using GSON library.
		BufferedReader reader = request.getReader();
		Map httpRequestData = new Gson().fromJson(reader, Map.class);

		//Get user_name and password_password from the HTTP Request and save them in the variables
		String provided_user_name = (String) httpRequestData.get("user_name");
		String provided_user_password = (String) httpRequestData.get("user_password");
		
		//Get actual user name and password from environment variables
		String actual_user_name = System.getenv("APP_USER_NAME");
		String actual_user_password = System.getenv("APP_USER_PASSWORD");	
		
		
		String responseBody = "";
		
		//if the request does not contain user_name
		// or user_password, the server cannot do much, can it?
		if(provided_user_name == null || provided_user_password == null) {
			responseBody = "{\"result\":\"error\", \"description\" : \"Credential missing\" }";
		} else {
			//If the credentials are valid
			if(provided_user_name.equals(actual_user_name) && 
					provided_user_password.equals(actual_user_password)) {
				//send a success message and also the record shows
				responseBody = "{\"result\":\"success\", \"record\" : \"You are outstanding\" }";
			}
			else{
				//well, user name and password did not match the actual credentials,
				//Send error message with the reason of failure.
				responseBody = "{\"result\":\"error\", \"description\" : \"Incorrect credentials\" }";
			}
		}
		response.setContentType("application/json");
		response.getWriter().write(responseBody);
	}
}
