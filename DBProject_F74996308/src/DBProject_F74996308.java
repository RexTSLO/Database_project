import java.io.*;
import java.util.Scanner;

public class DBProject_F74996308 {
	private int colSize,rowSize;
	private String line;
	private String[] temparray;
	private String[] att_array;//�sattribute list ���
	private String[] tab_array;//�stable list ���
	private String[] con_array;//�scondition list ���
	private int con_count = 0;//con_count �s�`�@�X�ӱ���
	private boolean ifcondition = false;//�����O�_��condition list
	private boolean ifjoin = false;//�����O�_�ݭnjoin
	private int newFormSize = 0;//����JOIN�ɡA�s��檺�j�p
	private int[] att_function;//����attribute�n�ϥΪ����
	private boolean iffunction = false;//�����O�_�n�Ψ��
	private boolean newline = false;//������X�ɬO�_�n����
	public static Scanner keyin = new Scanner(System.in);
	public Data BAT,FIELD,PITCH,PLAYER,TEAM ;
	public Data[] data_array = new Data[5];
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		String S_str,F_str,W_str;
		do{
			/******************
			 *Ū��&�إ߸��table
			 ******************/
			DBProject_F74996308 DBMS = new DBProject_F74996308();
			
			DBMS.fileSizeCheck("BAT.txt"); DBMS.BAT = new Data("BAT",DBMS.colSize,DBMS.rowSize); DBMS.fileRead("BAT.txt",DBMS.BAT);
			DBMS.fileSizeCheck("FIELD.txt"); DBMS.FIELD = new Data("FIELD",DBMS.colSize,DBMS.rowSize); DBMS.fileRead("FIELD.txt",DBMS.FIELD);
			DBMS.fileSizeCheck("PITCH.txt"); DBMS.PITCH = new Data("PITCH",DBMS.colSize,DBMS.rowSize); DBMS.fileRead("PITCH.txt",DBMS.PITCH);
			DBMS.fileSizeCheck("PLAYER.txt"); DBMS.PLAYER = new Data("PLAYER",DBMS.colSize,DBMS.rowSize); DBMS.fileRead("PLAYER.txt",DBMS.PLAYER);
			DBMS.fileSizeCheck("TEAM.txt"); DBMS.TEAM = new Data("TEAM",DBMS.colSize,DBMS.rowSize); DBMS.fileRead("TEAM.txt",DBMS.TEAM);
			
			DBMS.data_array[0] = DBMS.BAT; DBMS.data_array[1] = DBMS.FIELD; DBMS.data_array[2] = DBMS.PITCH; DBMS.data_array[3] = DBMS.PLAYER; DBMS.data_array[4] = DBMS.TEAM;
			
			/******************
			 * ���ݿ�JSQL�y�k
			 ******************/
			System.out.println("~~���ݿ�JSQL�y�k~~");
			System.out.println("�п�JSELECT:");
			S_str = keyin.nextLine();
			if(S_str.equals("SELECT")){
				DBMS.getAttList();
				do{
					System.out.println("�п�JFROM:");
					F_str = keyin.nextLine();
					if(F_str.equals("FROM")){
						DBMS.getTabList();
						if(DBMS.tab_array.length>1)//��ӥH�W���
							DBMS.ifjoin = true;
						break;
					}
					else
						System.out.println("�y�k���~");
				}while(true);
				
				do{
					System.out.println("�п�JWHERE�~���';'��ܵ���:");
					W_str = keyin.nextLine();
					if(W_str.equals("WHERE")){
						DBMS.getConList();
						DBMS.ifcondition = true;
						do{
							System.out.println("�п�J';'��ܵ���:");
							W_str = keyin.nextLine();
							if(W_str.equals(";")){
								System.out.println("SQL�y�k��J����");
								break;
							}
							else
								System.out.println("�y�k���~");
						}while(true);
						break;
					}
					else if(DBMS.ifjoin && W_str.equals(";")){//�ݭn��JWHERE��JOIN���O
						System.out.println("�y�k���~�A�ݭnjoin");
					}
					else if(W_str.equals(";")){
						DBMS.ifcondition = false;
						System.out.println("SQL�y�k��J����");
						break;
					}
					else
						System.out.println("�y�k���~");
				}while(true);
				//�i�JSQL�ѪR
				DBMS.SQL(DBMS);
				
				if(DBMS.ifcondition){//��condition list
					DBMS.compile_con_list(DBMS);
				}
				
				//��X
				DBMS.fileWrite(DBMS);
				System.out.println("Output.txt��X����");
			}
			else
				System.out.println("�y�k���~");
		}while(true);
	}
	
	public DBProject_F74996308(){
		
	}
	
	//�ˬd�������&�C��
	public void fileSizeCheck(String fileName) throws IOException{
		colSize = 0;rowSize = 0 ;
		FileReader fr = new FileReader(fileName); 
	    BufferedReader br = new BufferedReader(fr);
	    while((line = br.readLine())!=null){
	    	this.temparray = line.split("\t");
	    	colSize = temparray.length;
	    	rowSize++;
	    }
	    br.close();
	    fr.close();
	}
	
	//�إ߸��table
	public void fileRead(String fileName,Data data) throws IOException{
		int col=0,row=0;
		FileReader fr = new FileReader(fileName); 
	    BufferedReader br = new BufferedReader(fr);
	    //System.out.println(data.colSize+" "+data.rowSize);
	    while((line = br.readLine())!=null){
	    	this.temparray = line.split("\t");
	    	for(col=0;col<data.colSize;col++){
	    		data.table[col][row] = temparray[col];
	    		//System.out.print(data.table[col][row]+" ");
	    	}
	    	//System.out.println("");
	    	row++;
	    }
	    br.close();
	    fr.close();
	}
	
	//���oattribute list���e
	public void getAttList(){
		String att_str;
		String[] temp_array1;
		System.out.println("�п�J<attribute list>�A�H','���j�AAggregate function���e�ݥΪťչj�}:");
		att_str = keyin.nextLine();
		temp_array1 = att_str.split(",");
		this.att_array = new String[temp_array1.length];//attribute�j�p�i�T�w
		this.att_function = new int[temp_array1.length];
		
		for(int i=0;i<temp_array1.length;i++){
			String[] temp_array2;
			temp_array2 = temp_array1[i].split(" ");
			if(temp_array2.length==1){//�����
				iffunction = false;//
				att_array[i] = temp_array2[0];
				att_function[i] = 0;//�L��ƥ\��
			}
			else if(temp_array2.length==3){//�����
				iffunction = true;
				att_array[i] = temp_array2[1];
				if(temp_array2[0].equals("COUNT(")){
					att_function[i] = 1;
				}
				else if(temp_array2[0].equals("SUM(")){
					att_function[i] = 2;
				}
				else if(temp_array2[0].equals("MAX(")){
					att_function[i] = 3;
				}
				else if(temp_array2[0].equals("MIN(")){
					att_function[i] = 4;
				}
				else if(temp_array2[0].equals("AVG(")){
					att_function[i] = 5;
				}
				else{
					System.out.println("��ƻy�k���~");
				}
			}
				
		}
	}
	
	//���otable list���e
	public void getTabList(){
		String tab_str;
		System.out.println("�п�J<table list>�A�H','���j:");
		tab_str = keyin.nextLine();
		this.tab_array = tab_str.split(",");
	}
	
	//���ocondition list���e
	public void getConList(){
		String con_str;
		System.out.println("�п�J<condition>�A�r�P�Ÿ����Ϊťչj�}:");
		con_str = keyin.nextLine();
		this.con_array = con_str.split(" ");
	}
	
	//�}�lSQL�A�䨺��table�O�n�Ϊ�
	public void SQL(DBProject_F74996308 DBMS){
		for(int i=0;i<tab_array.length;i++){
			for(int j=0;j<DBMS.data_array.length;j++){
				if(DBMS.data_array[j].name.equals(tab_array[i])){
					//System.out.println(DBMS.data_array[j].name+" same");
					DBMS.data_array[j].iffrom = true;
					DBMS.search_att_list(DBMS.data_array[j]);
				}
			}
		}
	}
	
	//��ݬݨ���attribute�O�n�L��
	public void search_att_list(Data data){
		if(att_array.length==1 && att_array[0].equals("*")){//select all
			for(int col=0;col<data.colSize;col++){
				data.ifselect[col] = true;
			}
		}
		else{//select attribute
			for(int i=0;i<att_array.length;i++){
				for(int col=0;col<data.colSize;col++){
					if(data.table[col][0].equals(att_array[i])){
						data.ifselect[col] = true;
						data.selectfunction[col] = att_function[i];//���w��ƥ\��
					}
				}
			}
		}
	}
	
	//��Ūcondition���e
	public void compile_con_list(DBProject_F74996308 DBMS){
		/*********************************
		 * operator
		 * 0: = , 3: >=
		 * 1: > , 4: <=
		 * 2: < , 5: !=
		 * str �s�¦r��
		 * att_count��attribute�O�@���٨��
		 * num �s�Ʀr
		 * num_or_str ����O�Ʀr�Φr��
		 * 0: �Ʀr , 1: �r��
		 *********************************/
		String attribute1 = null, attribute2 = null;
		String str = null;
		int att_count = 0, operator = 6, num_or_str = 2;
		float num = 0;
		for(int i=0;i<DBMS.con_array.length;i++){
			try{
				num = Float.parseFloat(DBMS.con_array[i]);//�Ʀr
				num_or_str = 0;
				//System.out.println("num "+num);
			}
			catch(Exception e){
				//System.out.println(e.toString());
				if(DBMS.con_array[i].equals("=")){//�Ÿ�
					//System.out.println("is =");
					operator = 0;
				}
				else if(DBMS.con_array[i].equals(">")){
					//System.out.println("is >");
					operator = 1;
				}
				else if(DBMS.con_array[i].equals("<")){
					//System.out.println("is <");
					operator = 2;
				}
				else if(DBMS.con_array[i].equals(">=")){
					//System.out.println("is >=");
					operator = 3;
				}
				else if(DBMS.con_array[i].equals("<=")){
					//System.out.println("is <=");
					operator = 4;
				}
				else if(DBMS.con_array[i].equals("!=")){
					//System.out.println("is !=");
					operator = 5;
				}
				else if(DBMS.con_array[i].equals("'")){//�r��
					//System.out.println("is '");
					if(DBMS.con_array[i+2].equals("'")){
						str = DBMS.con_array[i+1];
						num_or_str = 1;
						i = i+2;
					}
					else
						System.out.println(" '�r��P�_���~ ");
				}
				else if(DBMS.con_array[i].equals("AND")){//�S��r AND�A�}�l�z��
					//System.out.println("is AND");
					DBMS.con_count++;
					condition_setting(DBMS,attribute1,attribute2,str,att_count,operator,num,num_or_str);
					//�n�٭�
					attribute1 = null; attribute2 = null; str = null;
					att_count = 0; num = 0; operator = 6; num_or_str = 2;
				}
				else{//���W
					//System.out.println("is attribute");
					if(att_count==0){
						attribute1 = DBMS.con_array[i];
						att_count++;
					}
					else if(att_count==1){
						attribute2 = DBMS.con_array[i];
						att_count++;
					}
					else
						System.out.println("attribute�ƶq���~");
				}
			}
		}
		
		//�}�l�z��
		DBMS.con_count++;
		condition_setting(DBMS,attribute1,attribute2,str,att_count,operator,num,num_or_str);
		
	}
	
	//WHERE����z��
	public void condition_setting(DBProject_F74996308 DBMS, String attribute1, String attribute2, String str, int att_count, int op, float num, int num_or_str){
		for(int format=0;format<DBMS.data_array.length;format++){//����
			if(DBMS.data_array[format].iffrom==true){
				for(int col=0;col<DBMS.data_array[format].colSize;col++){//�����1
					if(DBMS.data_array[format].table[col][0].equals(attribute1)){
						switch(op){
						case 0:// =
							if(att_count==2){//���2�Ajoin
								DBMS.data_array[format].ifgroup = true;
								for(int format2=0;format2<DBMS.data_array.length;format2++){//����2
									if(DBMS.data_array[format2].iffrom==true){
										for(int col2=0;col2<DBMS.data_array[format2].colSize;col2++){//�����2
											if(DBMS.data_array[format2].table[col2][0].equals(attribute2)){
												//�z��1
												for(int row=1;row<DBMS.data_array[format].rowSize;row++){
													for(int row2=1;row2<DBMS.data_array[format2].rowSize;row2++){
														if(DBMS.data_array[format].table[col][row].equals( DBMS.data_array[format2].table[col2][row2] )){
															DBMS.data_array[format].ifwhere[row]++;
															newFormSize++;//�q1�}�l�s��
															DBMS.data_array[format].newform[row] = newFormSize;
															DBMS.data_array[format2].newform[row2] = newFormSize;
															//System.out.println("0-2-1��"+row);
														}
													}
												}
												//�z��2
												for(int row2=1;row2<DBMS.data_array[format2].rowSize;row2++){
													for(int row=1;row<DBMS.data_array[format].rowSize;row++){
														if(DBMS.data_array[format2].table[col2][row2].equals( DBMS.data_array[format].table[col][row] )){
															DBMS.data_array[format2].ifwhere[row2]++;
															//System.out.println("0-2-2��"+row2);
														}
													}
												}
											}
										}
									}
								}
							}
							else if(num_or_str==0){//�Ʀr
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(Float.parseFloat(DBMS.data_array[format].table[col][row]) == num){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("0-0��"+row);
									}
								}
							}
							else if(num_or_str==1){//�r��
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(DBMS.data_array[format].table[col][row].equals(str)){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("0-1��"+row);
									}
								}
							}
							break;
						case 1:// >
							if(num_or_str==0){//�Ʀr
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(Float.parseFloat(DBMS.data_array[format].table[col][row]) > num){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("1-0��"+row);
									}
								}
							}
							break;
						case 2:// <
							if(num_or_str==0){//�Ʀr
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(Float.parseFloat(DBMS.data_array[format].table[col][row]) < num){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("2-0��"+row);
									}
								}
							}
							break;
						case 3:// >=
							if(num_or_str==0){//�Ʀr
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(Float.parseFloat(DBMS.data_array[format].table[col][row]) >= num){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("3-0��"+row);
									}
								}
							}
							break;
						case 4:// <=
							if(num_or_str==0){//�Ʀr
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(Float.parseFloat(DBMS.data_array[format].table[col][row]) <= num){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("4-0��"+row);
									}
								}
							}
							break;
						case 5:// !=
							if(num_or_str==0){//�Ʀr
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(Float.parseFloat(DBMS.data_array[format].table[col][row]) != num){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("5-0��"+row);
									}
								}
							}
							else if(num_or_str==1){//�r��
								for(int row=1;row<DBMS.data_array[format].rowSize;row++){
									if(!DBMS.data_array[format].table[col][row].equals(str)){
										DBMS.data_array[format].ifwhere[row]++;
										//System.out.println("5-1��"+row);
									}
								}
							}
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}
	
	//��XOutput.txt
	public void fileWrite(DBProject_F74996308 DBMS) throws IOException{
		FileWriter fw = new FileWriter("Output.txt"); 
	    BufferedWriter bw = new BufferedWriter(fw);
	    //
	    if(ifjoin){
	    	for(int format=0;format<DBMS.data_array.length;format++){//���u����X�����1
	    		if(DBMS.data_array[format].ifgroup){
	    			fileWriteColName(DBMS,format,bw);
	    			for(int format2=0;format2<DBMS.data_array.length;format2++){//����2
	    				if(!DBMS.data_array[format2].ifgroup && DBMS.data_array[format2].iffrom ){
	    					fileWriteColName(DBMS,format2,bw);
	    					if(newline){
	    		    			bw.newLine();
	    		    			newline = false;
	    		    		}
	    					if(iffunction){//����ƪ��L�k
	    						fileWriteTable(DBMS,format,bw);
	    						fileWriteTable(DBMS,format2,bw);
	    					}
	    					else{//�S��ƪ��L�k
	    						for(int newrow=1;newrow<=DBMS.newFormSize;newrow++){
	    							for(int row=1;row<DBMS.data_array[format].rowSize;row++){
	    								for(int col=0;col<DBMS.data_array[format].colSize;col++){
	    									if(DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count && DBMS.data_array[format].newform[row]==newrow ){
	    										bw.write(DBMS.data_array[format].table[col][row]+"\t");
	    										newline = true;
	    									}
	    								}
	    							}
	    							for(int row2=1;row2<DBMS.data_array[format2].rowSize;row2++){
	    								for(int col2=0;col2<DBMS.data_array[format2].colSize;col2++){
	    									if(DBMS.data_array[format2].ifselect[col2]==true && DBMS.data_array[format2].ifwhere[row2]==DBMS.con_count && DBMS.data_array[format2].newform[row2]==newrow ){
	    										bw.write(DBMS.data_array[format2].table[col2][row2]+"\t");
	    										newline = true;
	    									}
	    								}
	    							}
	    							if(newline){
	    								bw.newLine();
	    								newline = false;
	    							}
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
	    else{//�S��JOIN
	    	for(int format=0;format<DBMS.data_array.length;format++){//����
	    		fileWriteColName(DBMS,format,bw);
	    		if(newline){
	    			bw.newLine();
	    			newline = false;
	    		}
	    		if(iffunction){//����ƪ��L�k
	    			fileWriteTable(DBMS,format,bw);
	    		}
	    		else{//�S��ƪ��L�k
	    			for(int row=1;row<DBMS.data_array[format].rowSize;row++){//�L���e
	    				for(int col=0;col<DBMS.data_array[format].colSize;col++){
	    					if(DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count){
	   							bw.write(DBMS.data_array[format].table[col][row]+"\t");
	   							newline = true;
	    					}
	    				}
	    				if(newline){
	    					bw.newLine();
	    					newline = false;
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    bw.flush();
	    bw.close();
	    fw.close();
	}
	
	//��X���
	public void fileWriteColName(DBProject_F74996308 DBMS,int format,BufferedWriter bw) throws IOException{
		for(int col=0;col<DBMS.data_array[format].colSize;col++){
			if(DBMS.data_array[format].ifselect[col]==true){
				if(DBMS.data_array[format].selectfunction[col]==0){
					bw.write(DBMS.data_array[format].table[col][0]+"\t");
					newline = true;
				}
				else{
					if(DBMS.data_array[format].selectfunction[col]==1){
						bw.write("COUNT( ");
					}
					else if(DBMS.data_array[format].selectfunction[col]==2){
						bw.write("SUM( ");
					}
					else if(DBMS.data_array[format].selectfunction[col]==3){
						bw.write("MAX( ");
					}
					else if(DBMS.data_array[format].selectfunction[col]==4){
						bw.write("MIN( ");
					}
					else if(DBMS.data_array[format].selectfunction[col]==5){
						bw.write("AVG( ");
					}
					else{
						System.out.println("��ƿ�X���~");
						System.exit(1);
					}
					bw.write(DBMS.data_array[format].table[col][0]+" )\t");
    				newline = true;
    				iffunction = true;
				}
			}
		}
	}
	
	//��X��Ƶ��G���
	public void fileWriteTable(DBProject_F74996308 DBMS,int format,BufferedWriter bw) throws IOException{
		boolean first_time = true,dfirst_time = true;
	    boolean int_or_double = false;//false��int�Atrue��double
	    int count = 0,sum = 0,max = 0,min = 100000;
	    double dsum = 0,dmax = 0,dmin = 100000,avg = 0;
	    
		for(int col=0;col<DBMS.data_array[format].colSize;col++){
			for(int row=1;row<DBMS.data_array[format].rowSize;row++){
				if(DBMS.data_array[format].selectfunction[col]==1 && DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count){//count()
					count++;
				}
				else if(DBMS.data_array[format].selectfunction[col]==2 && DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count){//sum()
					try{
						sum += Integer.parseInt(DBMS.data_array[format].table[col][row]);
					}
					catch(Exception e){
						try{
							dsum += Double.parseDouble(DBMS.data_array[format].table[col][row]);
							int_or_double = true;
						}
						catch(Exception ex){
							System.out.println(ex.toString());
						}
					}
				}
				else if(DBMS.data_array[format].selectfunction[col]==3 && DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count){//max()
					try{
						int temp = Integer.parseInt(DBMS.data_array[format].table[col][row]);
						if(temp > max) max = temp;
					}
					catch(Exception e){
						try{
							double dtemp = Double.parseDouble(DBMS.data_array[format].table[col][row]);
							if(dtemp > dmax) dmax = dtemp;
						}
						catch(Exception ex){
							System.out.println(ex.toString());
						}
					}
				}
				else if(DBMS.data_array[format].selectfunction[col]==4 && DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count){//min()
					try{
						int temp = Integer.parseInt(DBMS.data_array[format].table[col][row]);
						if(first_time){
							min = temp;
							first_time = false;
						}
						else if(temp < min) 
							min = temp;
					}
					catch(Exception e){
						try{
							double dtemp = Double.parseDouble(DBMS.data_array[format].table[col][row]);
							if(dfirst_time){
								dmin = dtemp;
								dfirst_time = false;
							}
							else if(dtemp < dmin) 
								dmin = dtemp;
						}
						catch(Exception ex){
							System.out.println(ex.toString());
						}
					}
				}
				else if(DBMS.data_array[format].selectfunction[col]==5 && DBMS.data_array[format].ifselect[col]==true && DBMS.data_array[format].ifwhere[row]==DBMS.con_count){//avg()
					dsum += Double.parseDouble(DBMS.data_array[format].table[col][row]);
					count++;
				}
			}
			if(DBMS.data_array[format].selectfunction[col]==1){
				bw.write(count+"\t");
				count = 0;//�٭�
			}
			else if(DBMS.data_array[format].selectfunction[col]==2){
				dsum += sum;//�Y��ƲV����Ƹ�p��
				if(int_or_double) bw.write(dsum+"\t");
				else bw.write(sum+"\t");
				sum = 0; dsum = 0;
				int_or_double = false;
			}
			else if(DBMS.data_array[format].selectfunction[col]==3){
				if(dmax > max) bw.write(dmax+"\t");
				else bw.write(max+"\t");
				max = 0; dmax = 0;
			}
			else if(DBMS.data_array[format].selectfunction[col]==4){
				if(dmin < min) bw.write(dmin+"\t");
				else bw.write(min+"\t");
				min = 100000;
				dmin = 100000;
				first_time = true; dfirst_time = true;
			}
			else if(DBMS.data_array[format].selectfunction[col]==5){
				avg = dsum/count;
				//System.out.println(avg);
				bw.write(avg+"\t");
				avg = 0; dsum = 0; count = 0;
			}
		}
	}

}
