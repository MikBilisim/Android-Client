package MODELS;

public class warehouseM {
	
	int id,amount;
	String name;
	
	public warehouseM() {
		
	}
	
	public warehouseM(int id,String name,int amount) {
		this.id=id;
		this.name=name;
		this.amount=amount;
	}
	
	public warehouseM(int id,int amount) {
		this.id=id;
		this.amount=amount;
	}
	
	public warehouseM(String name,int amount) {
		this.name=name;
		this.amount=amount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getamount() {
		return amount;
	}
	public void setamount(int amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return id + " , " + name  + " , " + amount ;
	}
	

}
