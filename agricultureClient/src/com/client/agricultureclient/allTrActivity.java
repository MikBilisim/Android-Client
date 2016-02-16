package com.client.agricultureclient;


import CONTROLLER.allTransactionController;
import MODELS.allTransactionsM;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;

public class allTrActivity extends Activity {
	String productName;
	allTransactionsM tr = new allTransactionsM();
	allTransactionController cntrl;
	TableLayout table_layout;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.table_all_transaction);
	       
	        table_layout = (TableLayout) findViewById(R.id.table_allTransaction);
	        cntrl = new allTransactionController(this,table_layout);

	        try {
	        	 Bundle receiveData=getIntent().getExtras();
	        	 productName = receiveData.getString("productName");
	 	        tr.setProductName(productName);
	 	        cntrl.loadDataFromUser(tr);
			} catch (NullPointerException e) {
				// TODO: handle exception
				cntrl.loadDataFromWeb();
			}

	    }

	  @Override
	  public void onBackPressed() {
	  	super.onBackPressed();
	  	finish();
	  }

}
