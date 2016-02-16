package CONTROLLER;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;
import MODELS.allTransactionsM;
import MODELS.warehouseM;
import SERVICES.jsonGetWareHouses;
import SERVICES.jsonPostWareHouses;
import SERVICES.jsonPutWareHouses;
import SERVICES.wrLoadListViewService;


public class wareHouseListController {
	
	ArrayList<warehouseM> Loadmodel;
	allTransactionsM modelTr = new allTransactionsM();
	Context c;
	ListView wrList;
	String url;

	boolean status = true;
	Handler handler;
	wrLoadListViewService listService = new wrLoadListViewService();	
	BarChart mChart;
	String now = DateFormat.getDateTimeInstance().format(new Date());
	jsonGetWareHouses getJson;
	

	public wareHouseListController(Context c,ListView wrList,String dbName,BarChart mChart) {
		handler = new Handler();
		this.c=c;
		this.wrList=wrList;
		setUrl(dbName);
		this.mChart=mChart;
		
		//load Bar Chart
		handler = new Handler();
		new Thread(new Task()).start();
	}
	
	public String setUrl(String dbName){
		if(dbName.equals("silos")){
			url="http://10.0.2.2:8080/wareHouseWeb/rest/android/silos";
		}else{
			url="http://10.0.2.2:8080/wareHouseWeb/rest/android/liquid_tanks";
		}
		
		return url;
	}
	
	public void loadDataFromJson(){
		getJson = new jsonGetWareHouses(c, url);
		Loadmodel= getJson.getModel();

	}
	
	public ListView loadList(){
		return listService.loadListView(c, Loadmodel, wrList);
	}
	
	public void sendData(warehouseM model){
		new jsonPostWareHouses(c, model, url);
		refresh();
	}

	
	public void refresh(){
		Loadmodel=null;
		status=true;
		new Thread(new Task()).start();
	}
	

	public void deleteItem(int index){
		new deleteDialogProduct().showDialog(c, wareHouseListController.this, index);	
	}
	

	
	public void updateAmount(int index,String operation){
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

			new jsonPutWareHouses(c, Loadmodel.get(index), url);
			refresh();
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
							loadDataFromJson();
						} else {
							loadList();
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

	
	public ListView getWrList() {
		return wrList;
	}

	
	public ArrayList<warehouseM> getLoadmodel() {
		return Loadmodel;
	}
	
	public wareHouseListController() {
	}
	
	public String getUrl() {
		return url;
	}
	
}
