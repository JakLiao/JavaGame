import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
/**
 * 数独的面板
 * @author 夕阳
 * 
 */
public class Bord extends JPanel{
    /**
     * 用来设定显示的风格(常量)
     */
	public final int STYLE_1 = 1, STYLE_2 = 2, STYLE_3 = 3;
	/**
	 * 页面风格(默认为风格1)
	 */
	private int style = STYLE_1;
	/**
     * 游戏难度,缺省值为3,共5个级别
     */
	private int lvl = 3;
	/**
     * 用来显示用户的输入界面
     */
	private JButton[][] bords = new JButton[9][9];
    /**
     * 用来处理相应的数据
     */
	private int[][] form1 = new int[9][9];
	/**
	 * 用来辅助form1进行相关判断
	 */
	private int[][] form2 = new int[9][9];
	/**
	 * 再定义一个form3用来检验答案是否唯一
	 */
	private int[][] form3 = new int[9][9];
	/**
	 * 用来储存form1 的数据，用于重新开始菜单
	 */
	private int[][] form4 = new int[9][9];
	/**
	 * 为了方便控制，将小窗口的Button 放在 Bord 的 data fields里面
	 */
	JButton[] numbers = new JButton[9]; 
    /**
     * 用来选择的副窗口
     */
	JFrame subFrame = new JFrame();
	/**
     * 用来储存待检查的数字z
     */
	private int z;
	/**
	 * 用来储存位置
	 */
	private int i = 0, j = 0;
	/**
     * 无参构造器，其中包含一些初始化处理
     */
	public Bord(){
    	/**
    	 * 初始化subFrame
    	 */
		subFrame.setTitle("请选择要填入的数字");
		for(int i = 0; i < 9; i++){
			numbers[i] = new JButton(new ImageIcon("image/" + (i + 1) + "g" + style + ".gif"));
			numbers[i].addActionListener(new NumActionListener());
			subFrame.add(numbers[i]);
		}
		subFrame.pack();
		subFrame.setLayout(new GridLayout(3,3));
		/**
    	 *  设置布局GridLayout（9，9）
    	 */
		this.setLayout(new GridLayout(9,9));
		for(int x = 0; x < 9; x++){
    		for(int y = 0; y < 9; y++){
    			/**
    			 * 实例化每一个JButton
    			 */
    			bords[x][y] = new JButton(new ImageIcon("image/0.gif"));	
    			bords[x][y].setSize(34, 38);
    			/**
    			 * 添加到面板中
    			 */
    			bords[x][y].addActionListener(new LabelActionListener());
    			this.add(bords[x][y]);
    		}
    	}
		setBackground(3, 0, 5, 2);
		setBackground(0, 3, 2, 5);
		setBackground(6, 3, 8, 5);
		setBackground(3, 6, 5, 8);
		//让格子不能填
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++)
				bords[i][j].setEnabled(false);
		}
    }
	/**
	 * 将form1的数据还原成初值
	 */
	public void restart(){
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++){
				form1[i][j] = form4[i][j];
				if(form1[i][j] != 0)
					bords[i][j].setIcon(new ImageIcon("image/" + form1[i][j] + "g" + getStyle() + ".gif"));
				else
					bords[i][j].setIcon(new ImageIcon("image/0.gif"));
			}
	}
	/**
	 * 用来更新面板的风格
	 * @param n 风格
	 */
	public void update(int n){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(form1[i][j] != 0){
					if(bords[i][j].getIcon().getIconHeight() == 37)
						bords[i][j].setIcon(new ImageIcon("image/" + form1[i][j] + "t" + this.style + ".gif"));
					else
						bords[i][j].setIcon(new ImageIcon("image/" + form1[i][j] + "g" + this.style + ".gif"));
				}
				else
					bords[i][j].setIcon(new ImageIcon("image/0.gif"));
			}
		}
	}
	/**
	 * @return 返回界面风格
	 */
	public int getStyle(){
		return this.style;
	}
	/**
	 * 改变面板的风格
	 * @param n 风格类型
	 */
	public void setStyle(int n){
		this.style = n;
	}
	/**
	 * 用来检查一个输入是否合法
	 * @param x 数据的x坐标
	 * @param y 数据的y坐标
	 * @param z 待检查的数据
	 * @param form 要检查的数据表格
	 * @return 如果没有冲突则返回TRUE，否则返回FALSE
	 */
	private boolean check(int x, int y, int z, int[][] form){
		boolean isOk = true;
		if(form[x][y] != 0)
			isOk = false;
		for(int i = 0; i < 9; i++){
			if(form[i][y] == z || form[x][i] == z)
				isOk = false;
		}
		for(int i = (x / 3) * 3; i <(x / 3) * 3 + 3; i++){
			for(int j = ( y / 3) * 3; j < (y / 3) * 3 + 3; j++){
				if(form[i][j] == z)
					isOk = false;
			}
		}
		return isOk;
	}
	/**
	 * 用来检查一个输入是否合法，不合法则提示用户
	  * @param x 数据的x坐标
	 * @param y 数据的y坐标
	 * @param z 待检查的数据
	 * @param form 要检查的数据表格
	 */
	private void checkAndPrint(int x, int y, int z, int[][] form){
		for(int i = 0; i < 9; i++){
			if(form[i][y] == z){
				JOptionPane.showMessageDialog(
						null,"填入数字与第"+(i+1)+"行第"+(y+1)+"列数字冲突！请从新填入！");
			    return;
			}
		}
		for(int i = 0; i < 9; i++){
			if(form[x][i] == z){
				JOptionPane.showMessageDialog(
						null,"填入数字与第"+(x+1)+"行第"+(i+1)+"列数字冲突！请从新填入！");
				return;
			}
		}
		for(int i = (x / 3) * 3; i <(x / 3) * 3 + 3; i++){
			for(int j = ( y / 3) * 3; j < (y / 3) * 3 + 3; j++){
				if(form[i][j] == z){
					JOptionPane.showMessageDialog(
							null,"填入数字与第"+(i+1)+"行第"+(j+1)+"列数字冲突！请从新填入！" );
					return;
				}
			}
		}
	}
	/**
	 * 和upcheck()方法相互调用，用来判断是否是死局。当然也可用来智能解题，和用来随机产生一个数独题
	 * @param x 只含一个数的一位数组，用来 表示x坐标
	 * @param y 只含一个数的一位数组，用来 表示y坐标
	 * @param a 待检查的数组
	 * @param b 辅助的数组
	 * @return 若x > 8则返回TRUE； x < 0 则返回FALSE
	 */
	private boolean downCheck(int[] x, int[] y, int[][] a,int[][] b){
		if(y[0] > 8){                                              //check y[0]
    		y[0] = 0;                                              //换行
    		x[0] += 1;                                              
    		if(x[0] > 8)
    			return true;
    	}
		if(x[0] < 0)
			return false;
		else if(x[0] > 8)
			return true;
		else{
	        while(a[x[0]][y[0]] != 0){
	        	y[0] += 1;
	        	if(y[0] > 8){
	        		y[0] = 0;
	        		x[0] += 1;
	        		if(x[0] > 8)
	        			return true;
	        	}
	        }                                                            //to examine whether this unit has a number	
	       body:{ for(int i = 1; i < 10; i++){
	        	     if(check(x[0],y[0],i,b)){                          //to check whether a particular number can be put there
	        		   b[x[0]][y[0]] = i;                             //to store number i
	        		   y[0] += 1;
	        		   downCheck(x,y,a,b);
	        		   break body;                                   //if finding one then break
	        	     }
	              }
	        	  b[x[0]][y[0]] = 0;                                      //clear b[x][y[0]] 
	        	  y[0] -= 1;
	        	  upCheck(x,y,a,b);                            //the last unit
	        }
		}
		if(x[0] > 8){                                               //check again
			return true;
		}
		else 
			return false;
	}
	/**
	 * 和downCheck方法相互调用，用来判断是否是死局。当然也可用来智能解题，和用来随机产生一个数独题
	 * @param x 只含一个数的一位数组，用来 表示x坐标
	 * @param y 只含一个数的一位数组，用来 表示y坐标
	 * @param a 待检查的数组
	 * @param b 辅助的数组
	 */
	private void upCheck(int[] x, int[] y, int[][] a, int[][] b){
		if(y[0] < 0){                                               //check y[0] 
			y[0] = 8;
			x[0] -= 1;
			if(x[0] < 0)
				return;
		}
		while(a[x[0]][y[0]] != 0){                                     //move back
			y[0] -= 1;
			if(y[0] < 0){
				y[0] = 8;
				x[0] -= 1;
				if(x[0] < 0)
					return;
			}
		}
	    body: {for(int i = b[x[0]][y[0]] + 1; i < 10; i++){
	    	      b[x[0]][y[0]] = 0;
	    	      if(check(x[0],y[0],i,b)){
	    	    	  b[x[0]][y[0]] = i;
	    	    	  y[0] += 1;
	    	    	  downCheck(x,y,a,b);
	    	    	  break body;
	    	      }
	           }
	    	   b[x[0]][y[0]] = 0;
	    	   y[0] -= 1;
	    	   upCheck(x,y,a,b);
	         }
		if(x[0] < 0)
			return;
	}
	/**
	 * 和downChcek方法一样，只不过是从9~1检索。
	 * @param x 只含一个数的一位数组，用来 表示x坐标
	 * @param y 只含一个数的一位数组，用来 表示y坐标
	 * @param a 待检查的数组
	 * @param b 辅助的数组
	 * @return 若x > 8则返回TRUE； x < 0 则返回FALSE
	 */
	private boolean downCheckTopDown(int[] x, int[] y, int[][] a,int[][] b){
		if(y[0] > 8){                                              //check y[0]
    		y[0] = 0;                                              //换行
    		x[0] += 1;                                              
    		if(x[0] > 8)
    			return true;
    	}
		if(x[0] < 0)
			return false;
		else if(x[0] > 8)
			return true;
		else{
	        while(a[x[0]][y[0]] != 0){
	        	y[0] += 1;
	        	if(y[0] > 8){
	        		y[0] = 0;
	        		x[0] += 1;
	        		if(x[0] > 8)
	        			return true;
	        	}
	        }                                                            //to examine whether this unit has a number	
	       body:{ for(int i = 9; i > 0; i--){
	        	     if(check(x[0],y[0],i,b)){                          //to check whether a particular number can be put there
	        		   b[x[0]][y[0]] = i;                             //to store number i
	        		   y[0] += 1;
	        		   downCheckTopDown(x,y,a,b);
	        		   break body;                                   //if finding one then break
	        	     }
	              }
	        	  b[x[0]][y[0]] = 0;                                      //clear b[x][y[0]] 
	        	  y[0] -= 1;
	        	  upCheckTopDown(x,y,a,b);                            //the last unit
	        }
		}
		if(x[0] > 8){                                               //check again
			return true;
		}
		else 
			return false;
	}
	/**
	 * 和upCheck方法一样只不过是从9~1检索。 
	 * @param x 只含一个数的一位数组，用来 表示x坐标
	 * @param y 只含一个数的一位数组，用来 表示y坐标
	 * @param a 待检查的数组
	 * @param b 辅助的数组
	 */
	private void upCheckTopDown(int[] x, int[] y, int[][] a, int[][] b){
		if(y[0] < 0){                                               //check y[0] 
			y[0] = 8;
			x[0] -= 1;
			if(x[0] < 0)
				return;
		}
		while(a[x[0]][y[0]] != 0){                                     //move back
			y[0] -= 1;
			if(y[0] < 0){
				y[0] = 8;
				x[0] -= 1;
				if(x[0] < 0)
					return;
			}
		}
	    body: {for(int i = b[x[0]][y[0]] - 1; i > 0; i--){
	    	      b[x[0]][y[0]] = 0;
	    	      if(check(x[0],y[0],i,b)){
	    	    	  b[x[0]][y[0]] = i;
	    	    	  y[0] += 1;
	    	    	  downCheckTopDown(x,y,a,b);
	    	    	  break body;
	    	      }
	           }
	    	   b[x[0]][y[0]] = 0;
	    	   y[0] -= 1;
	    	   upCheckTopDown(x,y,a,b);
	         }
		if(x[0] < 0)
			return;
	}
	/**
	 * 内部类用来添加 frame 中的每个Button的监听器
	 * @author xw
	 *
	 */
	private class LabelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(i = 0; i < 9; i++){
				for(j = 0; j < 9; j++){
					if(e.getSource().equals(bords[i][j])){
						/**
						 * 如果是题目给出的数字，提示用户不能更改。
						 */
						if(bords[i][j].getIcon().getIconHeight() == 38){
							JOptionPane.showMessageDialog(null, "题目给出数字不能更改!");
							/**
							 * 因为该次输入不合法，取消上次的修改
							 */
							return;
						}
						subFrame.setSize(50,50);
						/**
						 * 使subFrame  相对于当前打开
						 */
						subFrame.setLocationRelativeTo(bords[i][j]);
						/**
						 * 打开subFrame
						 */
						subFrame.setVisible(true);
						/**
						 * 动画效果"打开 "
						 */
						try {
							Thread.sleep(60);
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						int kongzhi = 1;
						while(kongzhi < 5){
							try {
								Thread.sleep(60);
								subFrame.setSize(50 + 50 * kongzhi,100 + 50 * kongzhi);
								kongzhi++;
							}
							catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						return;
					}
				}
			}
		}	
	}
	/**
	 * 内部类 implements ActionListener,用来添加subFrame的 Button 的监听器
	 * @author xw
	 *
	 */
	public class NumActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int a = 0; a < 9; a++){
				if(e.getSource().equals(numbers[a])){
					/**
					 * 获得用户输入的数字 
					 */
					z = a + 1;
					/**
					 * 储存该位置的数值
					 */
					int temp = form1[i][j];
					/**
					 * 使该位置的数成为0，使用户能更改
					 */
					form1[i][j] = form2[i][j] = 0;
				    if(check(i, j, z, form1)){
						/**
						 * 显示用户填入的数字
						 */
						bords[i][j].setIcon(new ImageIcon("image/" + z + "t" + style + ".gif"));
						/**
						 * 储存用户输入的数字
						 */
						form1[i][j] = form2[i][j] = z;
						/**
						 * 使z复位
						 */
						i = j = z = 0;
						/**
						 * 填完后关闭子窗口,动画效果"关闭"
						 */
					    int kongzhi = 1;
					    while(kongzhi < 5){
					    	subFrame.setSize(250 - 50 * kongzhi, 250 - 50 * kongzhi);
					    	try {
								Thread.sleep(60);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    	kongzhi++;
					    }
						subFrame.setVisible(false);
						return;
					}
					else{
						/**
						 * 提示用户输入不合法
						 */
						checkAndPrint(i, j, z, form1);
						/**
						 * 还原form1，form2的值
						 */
						form1[i][j] = form2[i][j] = temp;
						/**
						 * 关闭 subFrame
						 */
						subFrame.setVisible(false);
						return;
					}
				}
			}
		}
	}
	public boolean isEqual(int[][] a, int[][] b){
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[0].length; j++){
				if(a[i][j] != b[i][j])
					return false;
			}
		}
		return true;
	}
	/**
	 * 用来获得一个随机的表格
	 */
	public void getRandomForm(){
		/**
		 * 用来调用downCheck  和 upCheck方法
		 */
		int[] x = new int[1], y = new int[1];
		x[0] = y[0] = 0;
		/**
		 * 初始化第一个空
		 */
		form1[0][0]	= form2[0][0] = (int)(Math.random() * 9 + 1);
		/**
		 * 再随机化几个格子,a,b 代表随机坐标，c代表随机值，i用来孔子循环
		 */
		int a,b,c, i = 0;
		while(i < 4){
			a = (int)(Math.random() * 9);
			b = (int)(Math.random() * 9);
			c = (int)(Math.random() * 9 + 1);
			if(check(a,b,c,form1)){
				form1[a][b] = form2[a][b] = c;
				i++;
			}
		}
		/**
		 * 调用downCheck()获得一个填好的表格form2
		 */
		downCheck(x,y,form1,form2);
		/**
		 * 打散form2的顺序，使得表的无规律化,并拷贝到form1
		 */
		for(int k = 0; k < 9;k++){
			for(int j = 0; j < 9; j++){
				switch(form2[k][j]){
				case 1:form1[k][j] = form2[k][j] = 3;
				break;
				case 2:form1[k][j] = form2[k][j] = 2;
				break;
				case 3:form1[k][j] = form2[k][j] = 9;
				break;
				case 4:form1[k][j] = form2[k][j] = 6;
				break;
				case 5:form1[k][j] = form2[k][j] = 5;
				break;
				case 6:form1[k][j] = form2[k][j] = 8;
				break;
				case 7:form1[k][j] = form2[k][j] = 4;
				break;
				case 8:form1[k][j] = form2[k][j] = 1;
				break;
				case 9:form1[k][j] = form2[k][j] = 7;
				break;
				}
			}
		}
	}
	/**
	 * 用来改变游戏的难度
	 * @param n ：期望改变的难度
	 */
	public void setLvl(int n){
		if(n < 1)
			JOptionPane.showMessageDialog(null, "已为最简单的级别!");
		else if(n > 5)
			JOptionPane.showMessageDialog(null,"已为最难的级别！");
		else
			this.lvl = n;
	}
	/**
	 * 获得游戏的难度
	 * @return 游戏的难度值
	 */
	public int getLvl(){
		return this.lvl;
	}
	/**
	 * 开始一场新的游戏
	 * @param n 游戏的难度
	 */
	public void newGame(int n){
		//清除上一道题目
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				form1[i][j] = form2[i][j] = form3[i][j] = 0;
				bords[i][j].setIcon(new ImageIcon("image/0.gif"));
			}
		}
		/**
		 * 表格中将要显示给用户的个子的个数
		 */
		int number = 31 + (3 - n);
		/**
		 * 一行中已经显示了的数字的个数
		 */
		int already[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		/**
		 *  到目前为止总共已多显示了 的数字（如果每行3个 则九行 27 个。又因为  （29 - 27） 与 （33 - 27）
		 *  都不能被9 整除。所以每行不可能都多一个只能有些行多一个（或两个）有些行不增加）
		 */
		int total = 0;
		//决定每行要显示格子的个数
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(already[j] != 0)
				    total += (already[j] - 3);
			}
			//如果剩下的每行最多只能显示3个，则规定每行显示3个
			if((number - total) % 9 == 0)
				already[i] = 3;
			//如果剩下的只允许有一行多一个，则该行为4个
			else if((number - total) % 9 == 1)
				already[i] = 4;
			//其他情况下从3，4 ，5中选择一个
			else
				already[i] = (int)(Math.random() * 3) + 3;
			//令total 复位 
			total = 0;
		}
		//产生一个随机的表格
		getRandomForm();
		int[][] displayed = new int[9][5]; //将要显示给用户的数字
		boolean isRight;//下面的循环中将用到
		do{
			//复位form1
			for(int i = 0; i < 9; i++){
				for(int j = 0; j< 9; j++)
					form1[i][j] = form2[i][j];
			}
			for(int i = 0; i < 9; i++)
				difNumbers(already[i], displayed[i]);
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					body:{
					for(int k = 0; k < displayed[i].length; k++)
						if(j == displayed[i][k])
							break body;
						form1[i][j] = 0;
				}
				}
			}
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					form3[i][j] = form1[i][j];
				}
			}
			int[] x = {0};
			int[] y = {0};
			downCheckTopDown(x, y, form1, form3);
			isRight = isEqual(form2, form3);
		}while(!isRight);
		//拷贝form1到form4
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
				form4[i][j] = form1[i][j];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(form1[i][j] != 0)
					bords[i][j].setIcon(new ImageIcon("image/" + form1[i][j] +"g" + style + ".gif"));
			bords[i][j].setEnabled(false);
			//bords[i][j].setContentAreaFilled(false);
			}
		}
	}
	/**
	 * 解题
	 */
	public void solving(){
		int[] x = {0};
		int[] y = {0};
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				form2[i][j]	 = form1[i][j];
			}
		}
		if(!downCheck(x, y, form1, form2)){
			JOptionPane.showMessageDialog(null, "该题无解！");
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					form2[i][j] = form1[i][j];//还原form2
				}
			}
		}
		else{
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					bords[i][j].setIcon(new ImageIcon("image/" + form2[i][j] + "g" + style + ".gif"));
					bords[i][j].setEnabled(false);
				}
			}
		}
	}
	/**
	 * 用来得到n 个0~8 中不同的数字，并储存在 数组arr中 
	 * @param n 要得到的n 个数字
	 * @param arr 用来储存的数组
	 */
	public void difNumbers(int n, int[] arr){
		int already = 0; // 已经产生了的数字的个数 
		for(int i = 0; i < arr.length; i++)
			arr[i] = -1;
		do{
			int i = (int)(Math.random() * 9);
			if(already == 0){
				arr[already] = i;
				already++;
			}
			else{
				body:{
				    for(int j = 0; j < already; j++)
					    if(i == arr[j])
						    break body;
					arr[already] = i;
					already++;
				}
			}
		}while(already != n);//直到产生了n个数字
		java.util.Arrays.sort(arr);// 对arr进行排序
	}
	/**
	 * 清空数据和界面
	 */
	public void clear(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				form1[i][j] = form2[i][j] = form3[i][j] = 0;
				bords[i][j].setIcon(new ImageIcon("image/0.gif"));
			}
		}
	}
	/**
	 * 检查用户是否填完
	 * @return 如果填完就返回TRUE；否则FALSE
	 */
	public boolean isOver(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(form1[i][j] == 0)
					return false;
			}
		}
		JOptionPane.showMessageDialog(null, "游戏结束！\nCONGRATULATIONS！");
		return true;
	}
	/**
	 * 使用户能输入
	 */
	public void enableInput(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++)
				bords[i][j].setEnabled(true);
		}
	}
	/**
	 * 返回form1 的值
	 * @param x x坐标
	 * @param y y坐标
	 * @return form[x][y]
	 */
	public int getForm_1(int x, int y){
		return this.form1[x][y];
	}
	/**
	 * 改变指定区域Button 的背景
	 * @param x0 起始x坐标
	 * @param y0 起始y坐标
	 * @param x1 结束x坐标
	 * @param y1 结束y坐标
	 */
	public void setBackground(int x0, int y0, int x1, int y1){
		for(int i = x0; i <= x1; i++){
			for(int j = y0; j <= y1; j++)
				bords[i][j].setBackground(Color.red);
		}
	}
	/**
	 *  改变form1 的值，只要用于将来的输入方法
	 * @param x x坐标
	 * @param y y坐标
	 * @param val 要改变的值
	 */
	public void changeForm_1(int x, int y, int val, boolean isG){
		this.form1[x][y] = val;
		if(isG)
			bords[x][y].setIcon(new ImageIcon("image/" + form1[x][y] + "g" + this.getStyle() + ".gif"));
	}
	public boolean isG(int x, int y){
		if(bords[x][y].getIcon().getIconHeight() == 38)
			return true;
		else 
			return false;
	}
	/**
	 * 用来测试的main方法
	 * @param args
	 */
	/*public static void main(String[] args){
		Bord panel = new Bord();
		panel.newGame(panel.getLvl());
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		System.out.println("断点设置");
	}*/
}
