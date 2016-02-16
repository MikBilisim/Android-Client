package MODELS;

public class allTransactionsM {
	
	int id;
	String productGroup;
	String productName;
	String transaction;
	String date;
	
	
	
	public allTransactionsM(int id, String productGroup, String productName,
			String transaction, String date) {
		this.id = id;
		this.productGroup = productGroup;
		this.productName = productName;
		this.transaction = transaction;
		this.date = date;
	}
	
	public allTransactionsM() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "id=" + id + ", productGroup=" + productGroup
				+ ", productName=" + productName + ", transaction="
				+ transaction + ", date=" + date;
	}

	
	

}
