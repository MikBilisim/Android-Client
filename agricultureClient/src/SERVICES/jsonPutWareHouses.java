package SERVICES;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import MODELS.warehouseM;
public class jsonPutWareHouses {

	warehouseM model;
	Context c;
	
	public jsonPutWareHouses(Context c, warehouseM model,String url) {
		this.c=c;
		this.model=model;
		new HttpAsyncTask().execute(url);
	}
	
	
	public static String PUT(String url, warehouseM model){
        InputStream inputStream = null;
        String result = "";
        try {
           
            HttpClient httpclient = new DefaultHttpClient();

            HttpPut httpPut = new HttpPut(url);
            
            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", model.getId());
            jsonObject.accumulate("name", model.getName());
            jsonObject.accumulate("amount", model.getamount());
 
            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);
 
            httpPut.setEntity(se);
   
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
 
            HttpResponse httpResponse = httpclient.execute(httpPut);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
           Log.w("InputStream", e.toString());
        }

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
	 
	
	            return PUT(urls[0],model);
	        }

	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(c, "Data Sent!", Toast.LENGTH_LONG).show();
	       }
	    }
}
