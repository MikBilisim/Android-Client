package COSTUMADAPTERS;

import java.util.ArrayList;
import com.client.agricultureclient.R;
import MODELS.warehouseM;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class wrStatusCostumAdapter extends BaseAdapter {

	ArrayList<warehouseM> model = new ArrayList<warehouseM>();
	//for edittext
	warehouseM[] model2;
	String[] myStringArray = new String[3];
	int count = 0;
	private LayoutInflater mLayoutInflater;
	int valueFromEditText=0;
	public wrStatusCostumAdapter(Context context, ArrayList<warehouseM> model) {
		
		this.model=model;
		
		
		model2= new warehouseM[this.model.size()];
		
		for(int i=0;i<this.model.size();i++){
			model2[i]= new warehouseM(i, 0);			 
		}
		
	
		
		count=model.size();
		// get the layout inflater
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// getCount() represents how many items are in the list
		return count;
	}

	 public void addCount(int num) {
	        count += num;
	        
	    }
	 
	@Override
	// get the data of an item from a specific position
	// i represents the position of the item in the list
	public Object getItem(int i) {
		return null;
	}

	@Override
	// get the position id of the item from the list
	public long getItemId(int i) {
		return 0;
	}

	 public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
         if (convertView == null) {
        	
             holder = new ViewHolder();
             convertView = mLayoutInflater.inflate(R.layout.wrstatus_item, null);
             holder.caption = (EditText) convertView.findViewById(R.id.etAmountTrans);
             holder.nameTx = (TextView) convertView.findViewById(R.id.txt_wrstatus);
             convertView.setTag(holder);
             
             if ( position % 2 == 0)
            	 convertView.setBackgroundResource(R.drawable.listview_selector_even);
               else
            	   convertView.setBackgroundResource(R.drawable.listview_selector_odd);
      
         } else {
        
             holder = (ViewHolder) convertView.getTag();
         }
      
        
         
         holder.nameTx.setText(model.get(position).toString());
         holder.caption.setId(position);
     
      
         holder.caption.setOnFocusChangeListener(new OnFocusChangeListener() {
             public void onFocusChange(View v, boolean hasFocus) {
                 if (!hasFocus){
                     final int position = v.getId();
                     final EditText Caption = (EditText) v;
                     
                     //edittexte yazilan degeri alma
                     if(!Caption.getText().toString().isEmpty()){
                     model2[position].setamount(Integer.parseInt(Caption.getText().toString()));
                     }
                 }
             }
         });

         return convertView;
     }
	 
	public warehouseM[] getModel2() {
		return model2;
	}
 }

 class ViewHolder {
     EditText caption;
     TextView nameTx;
 }

 class ListItem {
     String caption;
 }

