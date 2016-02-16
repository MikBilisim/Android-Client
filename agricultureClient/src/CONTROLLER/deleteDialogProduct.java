package CONTROLLER;


import com.client.agricultureclient.R;
import MODELS.warehouseM;
import SERVICES.jsonDeleteWareHouses;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class deleteDialogProduct {
	
	int index;
	wareHouseListController cntrl;
	Context c;
	
	public void showDialog(Context c1,wareHouseListController cntrl2,int index2){
		this.index=index2;
		this.cntrl=cntrl2;
		this.c=c1;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);

		alertDialogBuilder
			.setIcon(R.drawable.infoico)
			.setCancelable(false)
			.setNegativeButton("Close",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {					
					dialog.cancel();
					}
			  });

		
			
			alertDialogBuilder.setTitle("Are You Sure Want To Delete ?");
			alertDialogBuilder.setMessage(cntrl.getLoadmodel().get(index).toString());
		
			// create alert dialog
			

				alertDialogBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
				
						
						
						warehouseM model = new warehouseM();
						model.setId(cntrl.getLoadmodel().get(index).getId());
						model.setName(cntrl.getLoadmodel().get(index).getName());
						model.setamount(cntrl.getLoadmodel().get(index).getamount());
						
						new jsonDeleteWareHouses(c, model, cntrl.getUrl());
					      
						cntrl.refresh();
						
						
						}
				  });
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();	 
		
	}

}
