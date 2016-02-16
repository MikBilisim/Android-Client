package SERVICES;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import MODELS.allTransactionsM;
public class jsonDeleteTransaction {

	allTransactionsM model;
	Context c;
	
	public jsonDeleteTransaction(Context c, allTransactionsM model,String url) {
		this.c=c;
		this.model=model;
		new HttpAsyncTask().execute(url);
	}
	
	
	public static String DELETE(String url, allTransactionsM model){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
 
            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", model.getId());
            jsonObject.accumulate("productGroup", model.getProductGroup());
            jsonObject.accumulate("productName", model.getProductName());
            jsonObject.accumulate("transaction", model.getTransaction());
            jsonObject.accumulate("date", model.getDate());
            
            json = jsonObject.toString();
           
            StringEntity se = new StringEntity(json);
 
            delete.setEntity(se);
             
            delete.setHeader("Accept", "application/json");
            delete.setHeader("Content-type", "application/json");
 
            HttpResponse httpResponse = httpclient.execute(delete);
          
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

	 
	public static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
		    public static final String METHOD_NAME = "DELETE";
		    public String getMethod() { return METHOD_NAME; }

		    public HttpDeleteWithBody(final String uri) {
		        super();
		        setURI(URI.create(uri));
		    }
		    public HttpDeleteWithBody(final URI uri) {
		        super();
		        setURI(uri);
		    }
		    public HttpDeleteWithBody() { super(); }
		}
	 
	 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	
	            return DELETE(urls[0],model);
	        }
	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(c, "Data Sent!", Toast.LENGTH_LONG).show();
	       }
	    }
}
