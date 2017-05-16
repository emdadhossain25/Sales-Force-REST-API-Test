package salesforce_rest;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

public class Main {

	 static final String USERNAME     	= "pg2@banglafire.com";
	    static final String PASSWORD     = "emdad123123"+"8RFnjtfPuHB5lnw8DXmTuuxT";
	    static final String LOGINURL     = "https://login.salesforce.com";
	    static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
	    static final String CLIENTID     = "3MVG9d8..z.hDcPI9U2S_G.5L1AvsZ_h8awE_iF4KlyrmJZIhWjfu6w6KBp.aI3NcaSPfWAt2VBAjeqXZ0B8w";
	    static final String CLIENTSECRET = "3484998376676330718";
	 
	    public static void main(String[] args) {
	 
	        HttpClient httpclient = HttpClientBuilder.create().build();
	 
	        // Assemble the login request URL
	        String loginURL = LOGINURL +
	                          GRANTSERVICE +
	                          "&client_id=" +CLIENTID +
	                          "&client_secret=" +CLIENTSECRET +
	                          "&username=" +USERNAME +
	                          "&password=" +PASSWORD;
	 
	        // Login requests must be POSTs
	        HttpPost httpPost = new HttpPost(loginURL);
	        HttpResponse response = null;
	 
	        try {
	            // Execute the login POST request
	            response = httpclient.execute(httpPost);
	        } catch (ClientProtocolException cpException) {
	            cpException.printStackTrace();
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
	        String getResult = null;
	        // verify response is HTTP OK
	        final int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != HttpStatus.SC_OK) {
	            System.out.println("Error authenticating to Force.com: "+statusCode);
	            // Error is in EntityUtils.toString(response.getEntity())
	            try {
					getResult = EntityUtils.toString(response.getEntity());
				} catch (ParseException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println("Error authenticating to Force.com: "+getResult);
	            return;
	        }
	 
	       
	        try {
	            getResult = EntityUtils.toString(response.getEntity());
	            System.out.println("Error authenticating to Force.com: "+getResult);
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
	        JSONObject jsonObject = null;
	        String loginAccessToken = null;
	        String loginInstanceUrl = null;
	        try {
	            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
	            loginAccessToken = jsonObject.getString("access_token");
	            loginInstanceUrl = jsonObject.getString("instance_url");
	        } catch (JSONException jsonException) {
	            jsonException.printStackTrace();
	        }
	        System.out.println(response.getStatusLine());
	        System.out.println("Successful login");
	        System.out.println("instance URL: "+loginInstanceUrl);
	        System.out.println("access token/session ID: "+loginAccessToken);
	 
	        // release connection
	        httpPost.releaseConnection();
	    }
	
}
