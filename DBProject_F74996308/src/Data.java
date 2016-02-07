
public class Data {
	public String name;
	public String[][] table;
	public int colSize,rowSize;
	public boolean[] ifselect;//�����O�_SELECT
	public int[] selectfunction;//�����n�ϥΪ���ƥ\��
	public boolean iffrom;//�����O�_FROM
	public int[] ifwhere;//�����O�_�ŦXWHERE�n�D
	public boolean ifgroup;//�����O�_�u����X
	public int[] newform;//JOIN���ɭԥΨӽs����X����
	
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
