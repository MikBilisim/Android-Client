package SERVICES;

import java.util.ArrayList;

import COSTUMADAPTERS.wrStatusCostumAdapter;
import MODELS.warehouseM;
import android.content.Context;
import android.widget.ListView;


public class wrLoadListViewService {
	
	Context c;
	ListView wrList;
	wrStatusCostumAdapter adapter;
	public ListView loadListView(Context c,ArrayList<warehouseM> model,ListView wrList){
		this.wrList = wrList;
		adapter = new wrStatusCostumAdapter(c, model);
		this.wrList.setAdapter(adapter);
		this.wrList.setItemsCanFocus(true);
		return this.wrList;
	}
	
	public warehouseM[] getModel(){
		
		return adapter.getModel2();
	}
	
}
