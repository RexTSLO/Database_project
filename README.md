# Database_project

目標: 利用程式語言(EX: C, C++,  JAVA… )自行開發一個簡易的 DBMS<br/>
需自行撰寫介面(不需GUI)。該介面要可輸入SQL語法做查詢並顯示查詢結果。 

程式語言: JAVA<br/>
編譯環境: Eclipse<br/>
作業系統: Win8.1

**介面截圖與使用說明**<br/>

- 編譯並執行後會跳出訊息來提示你輸入SQL指令。<br/>
![db1](/DBProject_F74996308/pic/db1.png)
<br/><br/>
**1.SELECT-FROM(1)-WHERE(>,AND,<)**<br/>
- 照著提示輸入指令，其中需注意attribute list之間須以','分隔，condition之間須空1格分隔。<br/>
![db2](/DBProject_F74996308/pic/db2.png)

- 輸入';'並ENTER之後會跳出提示是否完成，若有錯誤也會告知，之後繼續等待下一次輸入。<br/>
![db3](/DBProject_F74996308/pic/db3.png)

- 執行結果會輸出至Output.txt檔，上頁範例結果如右圖。<br/>
![db4](/DBProject_F74996308/pic/db4.png)
<br/><br/>
**2.SELECT *-FROM(1)-WHERE(= ' xx ')**<br/>
![db5](/DBProject_F74996308/pic/db5.png)
- 上圖範例執行結果如下圖。<br/>
![db6](/DBProject_F74996308/pic/db6.png)
<br/><br/>
**3.SELECT-FROM(2)-WHERE(JOIN)**<br/>
![db7](/DBProject_F74996308/pic/db7.png)
- 上圖範例執行結果如下圖。<br/>
![db8](/DBProject_F74996308/pic/db8.png)
<br/><br/>
**4.SELECT(COUNT,SUM)-FROM(1)**<br/>
![db9](/DBProject_F74996308/pic/db9.png)
- 不輸入WHERE也可以。COUNT、SUM等函數功能中，括號間須空1格，上圖範例執行結果如下圖。
![db10](/DBProject_F74996308/pic/db10.png)
<br/><br/>
**5.SELECT(MAX,MIN,AVG)-FROM(2)-WHERE(JOIN)**<br/>
![db11](/DBProject_F74996308/pic/db11.png)
- 上圖範例執行結果如下圖。<br/>
![db12](/DBProject_F74996308/pic/db12.png)
<br/><br/>
**6.SELECT *-FROM(1)-WHERE(AND,AND...)**<br/>
![db13](/DBProject_F74996308/pic/db13.png)
- 上圖範例執行結果如下圖。<br/>
![db14](/DBProject_F74996308/pic/db14.png)
<br/><br/>


以上為此次專題執行結果範例，程式碼部分尚有許多不足之處可以改進，像是物件導向概念的應用、Bad smell的出現，都還需要加強，往後要朝這方面努力，寫出更乾淨的程式碼。
