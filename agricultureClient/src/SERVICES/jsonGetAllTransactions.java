package SERVICES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import MODELS.allTransactionsM;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class jsonGetAllTransactions {
	
	Context c;
	ArrayList<allTransactionsM> model = new ArrayList<allTransactionsM>();
	
	public jsonGetAllTransactions(Context c,String url) {
		this.c = c;
		if(isConnected()){
			new HttpAsyncTask().execute(url);
        }
		else{
			Toast.makeText(c, "internet not connecting", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public static String GET(String url){
		InputStream inputStream = null;
		String result = "";
		try {
			
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			
			// convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
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
	 
	 public boolean isConnected(){
	    	ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Activity.CONNECTIVITY_SERVICE);
	    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    	    if (networkInfo != null && networkInfo.isConnected()) 
	    	    	return true;
	    	    else
	    	    	return false;	
	    }
	 
	 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	              
	            return GET(urls[0]);
	        }
	        
	        @Override
	        protected void onPostExecute(String result) {
	        	Toast.makeText(c, "Received!", Toast.LENGTH_LONG).show();
	        	
	        	try {
					JSONArray json = new JSONArray(result);
					
					for(int i=0;i<json.length();i++){
						allTransactionsM temp = new allTransactionsM();
						temp.setId(json.getJSONObject(i).getInt("id"));
						temp.setProductGroup(json.getJSONObject(i).getString("productGroup"));
						temp.setProductName(json.getJSONObject(i).getString("productName"));
						temp.setTransaction(json.getJSONObject(i).getString("transaction"));
						temp.setDate(json.getJSONObject(i).getString("date"));

						model.add(temp);
					}
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       }
	    }
	 
	 public ArrayList<allTransactionsM> getModel() {
		return model;
	}
}
