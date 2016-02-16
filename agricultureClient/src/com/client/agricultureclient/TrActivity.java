package com.client.agricultureclient;

import com.github.mikephil.charting.charts.BarChart;
import CONTROLLER.wareHouseListController;
import MODELS.warehouseM;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TrActivity extends Activity {
	
	 String dbName;
	 ListView showList;
	 wareHouseListController cntrl;
	
	 BarChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wr_status);
        Bundle receiveData=getIntent().getExtras();
        dbName = receiveData.getString("dbName");
       
        mChart = (BarChart) findViewById(R.id.chart1);
        showList = (ListView)findViewById(R.id.statusList);

        loadList();    
       }


    public void addItem(View v){

	 EditText name = (EditText)findViewById(R.id.etProductName);
	 EditText amount = (EditText)findViewById(R.id.etAmountkg);
	 
	 if(!name.getText().toString().isEmpty() && ! amount.getText().toString().isEmpty()){
	 
	 int amnt = Integer.parseInt(amount.getText().toString());
	 String nameStr=name.getText().toString();
	 
	 cntrl.sendData(new warehouseM(nameStr, amnt));
	 
	 name.setText(null);
	 amount.setText(null);
	 
	 }else{
		 Toast.makeText(this,"don't leave empty spaces", Toast.LENGTH_SHORT).show();
	 }
	 
 }
 
	public void loadList() {
		cntrl = new wareHouseListController(this, showList, dbName, mChart);
	}

	public void plusItem(View v) {
		final int index = showList.getPositionForView((View) v.getParent());
		cntrl.updateAmount(index, "+");

	}

	public void minusItem(View v) {
		final int index = showList.getPositionForView((View) v.getParent());
		cntrl.updateAmount(index, "-");
	}

	public void deleteItem(View v) {
		final int index = showList.getPositionForView((View) v.getParent());
		cntrl.deleteItem(index);
	}

	public void showDetails(View v) {
		final int index = showList.getPositionForView((View) v.getParent());

		final Intent intent = new Intent(
				"com.client.agricultureclient.TABLE");
		final Bundle bundle = new Bundle();
		bundle.putString("productName", cntrl.getLoadmodel().get(index)
				.getName());
		intent.putExtras(bundle);
		startActivity(intent);
	}
 
	
	 @Override
	 public void onBackPressed() {
	 	super.onBackPressed();
	 	finish();
	 }
 
    
}
