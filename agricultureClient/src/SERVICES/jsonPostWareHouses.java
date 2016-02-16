package SERVICES;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import MODELS.warehouseM;
public class jsonPostWareHouses {

	warehouseM model;
	Context c;
	
	public jsonPostWareHouses(Context c, warehouseM model,String url) {
		this.c=c;
		this.model=model;
		new HttpAsyncTask().execute(url);
	}
	
	
	public static String POST(String url, warehouseM model){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
          
            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", model.getId());
            jsonObject.accumulate("name", model.getName());
            jsonObject.accumulate("amount", model.getamount());
 
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person); 
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
           Log.w("InputStream", e.toString());
        }
 
        // 11. return result
        return result;
    }
	
	
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }   
	 
	 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	 
	
	            return POST(urls[0],model);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(c, "Data Sent!", Toast.LENGTH_LONG).show();
	       }
	    }
}
