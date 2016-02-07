
public class Data {
	public String name;
	public String[][] table;
	public int colSize,rowSize;
	public boolean[] ifselect;//紀錄是否SELECT
	public int[] selectfunction;//紀錄要使用的函數功能
	public boolean iffrom;//紀錄是否FROM
	public int[] ifwhere;//紀錄是否符合WHERE要求
	public boolean ifgroup;//紀錄是否優先輸出
	public int[] newform;//JOIN的時候用來編號輸出順序
	
	public Data(String name,int col,int row){
		this.name = name;
		this.colSize = col;
		this.rowSize = row;
		this.table = new String[col][row];
		this.ifselect = new boolean[col];
		for(int i=0;i<col;i++){
			this.ifselect[i] = false;
		}
		this.selectfunction = new int[col];
		for(int i=0;i<col;i++){
			this.selectfunction[i] = 0;
		}
		this.ifwhere = new int[row];
		for(int j=0;j<row;j++){
			this.ifwhere[j] = 0;
		}
		this.iffrom = false;
		this.ifgroup = false;
		this.newform = new int[row];
		for(int j=0;j<row;j++){
			this.newform[j] = 0;
		}
	}
	
}
