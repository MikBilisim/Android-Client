package CONTROLLER;

import com.example.agriculturalwarehouse.R;

import CONTROLLER.allTransactionController.fromUser;
import MODELS.allTransactionsM;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

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
				
				
						cntrl.getModelTr().setProductName(cntrl.getLoadmodel().get(index).getName());
						cntrl.getModelTr().setTransaction("Delete item");
						cntrl.getDbservice().deleteData(cntrl.getLoadmodel().get(index));			
						cntrl.loadDataFromDb(c, cntrl.getWrList(), cntrl.getDbName());
						
						//transaction 
						cntrl.transactionAddItem();
						
						
						}
				  });
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();	 
		
	}

}
