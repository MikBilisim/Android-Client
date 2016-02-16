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

public class deleteDialogProductName {
	
	int index;
	allTransactionController cntrl;
	
	public void showDialog(Context c,allTransactionController cntrl2,int index2){
		this.index=index2;
		this.cntrl=cntrl2;
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
			alertDialogBuilder.setMessage(cntrl.getModel().get(index).toString());
		
		
			

				alertDialogBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						  allTransactionsM temp = new allTransactionsM();
						     temp.setId(cntrl.getModel().get(index).getId());
						     cntrl.deleteDataFromDb(temp);
						     cntrl.setStatus(true);
						     cntrl.setModel(null);
						     cntrl.trdRun();
						
						}
				  });
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();	 
		
	}

}
