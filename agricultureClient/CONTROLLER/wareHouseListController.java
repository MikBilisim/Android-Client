package CONTROLLER;

import java.text.BreakIterator;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.agriculturalwarehouse.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import DAO.allTransactions;
import MODELS.allTransactionsM;
import MODELS.warehouseM;
import SERVICES.allTrService;
import SERVICES.wareHousesService;
import SERVICES.wrLoadListViewService;

public class wareHouseListController {
	
	ArrayList<warehouseM> Loadmodel;
	allTransactionsM modelTr = new allTransactionsM();
	Context c;
	ListView wrList;
	String dbName;
	wareHousesService dbservice = new wareHousesService();
	allTrService allService = new allTrService();
	boolean status = true;
	Handler handler;
	wrLoadListViewService listService = new wrLoadListViewService();	
	BarChart mChart;
	String now = DateFormat.getDateTimeInstance().format(new Date());

	

	public wareHouseListController(Context c,ListView wrList,String dbName,BarChart mChart) {
		handler = new Handler();
		this.c=c;
		this.wrList=wrList;
		this.dbName=dbName;
		this.mChart=mChart;
		
		//load Bar Chart
		handler = new Handler();
		new Thread(new Task()).start();
	}
	
	
	public ListView loadDataFromDb(Context c,ListView wrList,String dbName){
		this.c=c;
		this.wrList=wrList;
		this.dbName=dbName;
		Loadmodel= dbservice.Loadmodel(c, dbName, Loadmodel);
		 loadBarChart();
		 allService.setC(c);
		return listService.loadListView(c, Loadmodel, wrList);
	}
	

	
	public void addDataInDb(warehouseM addModel){
		
		dbservice.addData(addModel);
		loadDataFromDb(c, wrList, dbName);	
		//add transaction
		modelTr.setProductName(addModel.getName());
		modelTr.setTransaction("Created, amount = "+addModel.getamount());
		transactionAddItem();
		
		
	}
	
	//delete item from db
	public void deleteItem(int index){
		new deleteDialogProduct().showDialog(c, wareHouseListController.this, index);	
	}
	
	public void transactionAddItem(){
		modelTr.setProductGroup(dbName);
		modelTr.setDate(now);
		allService.addData(modelTr);
		
	}
	
	public void updateDb(int index,String operation){
		int amountBefore = Loadmodel.get(index).getamount();
		int amountNew=0;
		int amount = listService.getModel()[index].getamount();		
		
		if (amount == 0) {
			Toast.makeText(c,"don't leave empty spaces", Toast.LENGTH_SHORT).show();
		} else {

			
			if (operation.equals("+")) {
				amountNew =  amountBefore+ amount;
				modelTr.setTransaction("Updated, "+amountBefore+" + "+amount+" = "+amountNew);
			} else {
				amountNew = amountBefore - amount;
				modelTr.setTransaction("Updated, "+amountBefore+" - "+amount+" = "+amountNew);			
			}

			
			if(amountNew<0){
				Toast.makeText(c,"You not enough amount", Toast.LENGTH_SHORT).show();
			}else{				
			Loadmodel.get(index).setamount(amountNew);
			dbservice.updateData(Loadmodel.get(index));
			loadDataFromDb(c, wrList, dbName);
			
			//add transaction 		
			modelTr.setProductName(Loadmodel.get(index).getName());
			transactionAddItem();		
			}
		}
	}

	class Task implements Runnable {
		@Override
		public void run() {
			while (status) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (getLoadmodel() == null) {
							loadDataFromDb(c, wrList, dbName);
						} else {
							loadBarChart();
							status = false;
						}
					}
				});
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	public void BarCharCreate() {
	
		mChart.setDescription("");
		mChart.setDrawGridBackground(false);

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setLabelCount(5, false);
		leftAxis.setSpaceTop(15f);

		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setLabelCount(5, false);
		rightAxis.setSpaceTop(15f);

		mChart.getLegend().setEnabled(false);
		mChart.animateY(700, Easing.EasingOption.EaseInCubic);

	}
	
	
	public void loadBarChart(){
		BarCharCreate();
		
		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
		ArrayList<String> nameProduct = new ArrayList<String>();
		for (int i = 0; i < Loadmodel.size(); i++) {
			entries.add(new BarEntry(Loadmodel.get(i).getamount(), i));
			nameProduct.add(Loadmodel.get(i).getName());
		}

		BarDataSet d = new BarDataSet(entries, "");
		d.setBarSpacePercent(20f);
		d.setColors(ColorTemplate.VORDIPLOM_COLORS);
		d.setBarShadowColor(Color.rgb(203, 203, 203));

		ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
		sets.add(d);

		BarData data = new BarData(nameProduct, sets);
		mChart.setData(data);
		mChart.invalidate();
	}
	
	public allTransactionsM getModelTr() {
		return modelTr;
	}
	
	public wareHousesService getDbservice() {
		return dbservice;
	}
	
	public ListView getWrList() {
		return wrList;
	}
	public String getDbName() {
		return dbName;
	}
	
	public ArrayList<warehouseM> getLoadmodel() {
		return Loadmodel;
	}
	
	public wareHouseListController() {
	}
	
}
