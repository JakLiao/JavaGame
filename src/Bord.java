import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
/**
 * ���������
 * @author Ϧ��
 * 
 */
public class Bord extends JPanel{
    /**
     * �����趨��ʾ�ķ��(����)
     */
	public final int STYLE_1 = 1, STYLE_2 = 2, STYLE_3 = 3;
	/**
	 * ҳ����(Ĭ��Ϊ���1)
	 */
	private int style = STYLE_1;
	/**
     * ��Ϸ�Ѷ�,ȱʡֵΪ3,��5������
     */
	private int lvl = 3;
	/**
     * ������ʾ�û����������
     */
	private JButton[][] bords = new JButton[9][9];
    /**
     * ����������Ӧ������
     */
	private int[][] form1 = new int[9][9];
	/**
	 * ��������form1��������ж�
	 */
	private int[][] form2 = new int[9][9];
	/**
	 * �ٶ���һ��form3����������Ƿ�Ψһ
	 */
	private int[][] form3 = new int[9][9];
	/**
	 * ��������form1 �����ݣ��������¿�ʼ�˵�
	 */
	private int[][] form4 = new int[9][9];
	/**
	 * Ϊ�˷�����ƣ���С���ڵ�Button ���� Bord �� data fields����
	 */
	JButton[] numbers = new JButton[9]; 
    /**
     * ����ѡ��ĸ�����
     */
	JFrame subFrame = new JFrame();
	/**
     * �����������������z
     */
	private int z;
	/**
	 * ��������λ��
	 */
	private int i = 0, j = 0;
	/**
     * �޲ι����������а���һЩ��ʼ������
     */
	public Bord(){
    	/**
    	 * ��ʼ��subFrame
    	 */
		subFrame.setTitle("��ѡ��Ҫ���������");
		for(int i = 0; i < 9; i++){
			numbers[i] = new JButton(new ImageIcon("image/" + (i + 1) + "g" + style + ".gif"));
			numbers[i].addActionListener(new NumActionListener());
			subFrame.add(numbers[i]);
		}
		subFrame.pack();
		subFrame.setLayout(new GridLayout(3,3));
		/**
    	 *  ���ò���GridLayout��9��9��
    	 */
		this.setLayout(new GridLayout(9,9));
		for(int x = 0; x < 9; x++){
    		for(int y = 0; y < 9; y++){
    			/**
    			 * ʵ����ÿһ��JButton
    			 */
    			bords[x][y] = new JButton(new ImageIcon("image/0.gif"));	
    			bords[x][y].setSize(34, 38);
    			/**
    			 * ��ӵ������
    			 */
    			bords[x][y].addActionListener(new LabelActionListener());
    			this.add(bords[x][y]);
    		}
    	}
		setBackground(3, 0, 5, 2);
		setBackground(0, 3, 2, 5);
		setBackground(6, 3, 8, 5);
		setBackground(3, 6, 5, 8);
		//�ø��Ӳ�����
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++)
				bords[i][j].setEnabled(false);
		}
    }
	/**
	 * ��form1�����ݻ�ԭ�ɳ�ֵ
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
	 * �����������ķ��
	 * @param n ���
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
	 * @return ���ؽ�����
	 */
	public int getStyle(){
		return this.style;
	}
	/**
	 * �ı����ķ��
	 * @param n �������
	 */
	public void setStyle(int n){
		this.style = n;
	}
	/**
	 * �������һ�������Ƿ�Ϸ�
	 * @param x ���ݵ�x����
	 * @param y ���ݵ�y����
	 * @param z ����������
	 * @param form Ҫ�������ݱ��
	 * @return ���û�г�ͻ�򷵻�TRUE�����򷵻�FALSE
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
	 * �������һ�������Ƿ�Ϸ������Ϸ�����ʾ�û�
	  * @param x ���ݵ�x����
	 * @param y ���ݵ�y����
	 * @param z ����������
	 * @param form Ҫ�������ݱ��
	 */
	private void checkAndPrint(int x, int y, int z, int[][] form){
		for(int i = 0; i < 9; i++){
			if(form[i][y] == z){
				JOptionPane.showMessageDialog(
						null,"�����������"+(i+1)+"�е�"+(y+1)+"�����ֳ�ͻ����������룡");
			    return;
			}
		}
		for(int i = 0; i < 9; i++){
			if(form[x][i] == z){
				JOptionPane.showMessageDialog(
						null,"�����������"+(x+1)+"�е�"+(i+1)+"�����ֳ�ͻ����������룡");
				return;
			}
		}
		for(int i = (x / 3) * 3; i <(x / 3) * 3 + 3; i++){
			for(int j = ( y / 3) * 3; j < (y / 3) * 3 + 3; j++){
				if(form[i][j] == z){
					JOptionPane.showMessageDialog(
							null,"�����������"+(i+1)+"�е�"+(j+1)+"�����ֳ�ͻ����������룡" );
					return;
				}
			}
		}
	}
	/**
	 * ��upcheck()�����໥���ã������ж��Ƿ������֡���ȻҲ���������ܽ��⣬�������������һ��������
	 * @param x ֻ��һ������һλ���飬���� ��ʾx����
	 * @param y ֻ��һ������һλ���飬���� ��ʾy����
	 * @param a ����������
	 * @param b ����������
	 * @return ��x > 8�򷵻�TRUE�� x < 0 �򷵻�FALSE
	 */
	private boolean downCheck(int[] x, int[] y, int[][] a,int[][] b){
		if(y[0] > 8){                                              //check y[0]
    		y[0] = 0;                                              //����
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
	 * ��downCheck�����໥���ã������ж��Ƿ������֡���ȻҲ���������ܽ��⣬�������������һ��������
	 * @param x ֻ��һ������һλ���飬���� ��ʾx����
	 * @param y ֻ��һ������һλ���飬���� ��ʾy����
	 * @param a ����������
	 * @param b ����������
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
	 * ��downChcek����һ����ֻ�����Ǵ�9~1������
	 * @param x ֻ��һ������һλ���飬���� ��ʾx����
	 * @param y ֻ��һ������һλ���飬���� ��ʾy����
	 * @param a ����������
	 * @param b ����������
	 * @return ��x > 8�򷵻�TRUE�� x < 0 �򷵻�FALSE
	 */
	private boolean downCheckTopDown(int[] x, int[] y, int[][] a,int[][] b){
		if(y[0] > 8){                                              //check y[0]
    		y[0] = 0;                                              //����
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
	 * ��upCheck����һ��ֻ�����Ǵ�9~1������ 
	 * @param x ֻ��һ������һλ���飬���� ��ʾx����
	 * @param y ֻ��һ������һλ���飬���� ��ʾy����
	 * @param a ����������
	 * @param b ����������
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
	 * �ڲ���������� frame �е�ÿ��Button�ļ�����
	 * @author xw
	 *
	 */
	private class LabelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(i = 0; i < 9; i++){
				for(j = 0; j < 9; j++){
					if(e.getSource().equals(bords[i][j])){
						/**
						 * �������Ŀ���������֣���ʾ�û����ܸ��ġ�
						 */
						if(bords[i][j].getIcon().getIconHeight() == 38){
							JOptionPane.showMessageDialog(null, "��Ŀ�������ֲ��ܸ���!");
							/**
							 * ��Ϊ�ô����벻�Ϸ���ȡ���ϴε��޸�
							 */
							return;
						}
						subFrame.setSize(50,50);
						/**
						 * ʹsubFrame  ����ڵ�ǰ��
						 */
						subFrame.setLocationRelativeTo(bords[i][j]);
						/**
						 * ��subFrame
						 */
						subFrame.setVisible(true);
						/**
						 * ����Ч��"�� "
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
	 * �ڲ��� implements ActionListener,�������subFrame�� Button �ļ�����
	 * @author xw
	 *
	 */
	public class NumActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int a = 0; a < 9; a++){
				if(e.getSource().equals(numbers[a])){
					/**
					 * ����û���������� 
					 */
					z = a + 1;
					/**
					 * �����λ�õ���ֵ
					 */
					int temp = form1[i][j];
					/**
					 * ʹ��λ�õ�����Ϊ0��ʹ�û��ܸ���
					 */
					form1[i][j] = form2[i][j] = 0;
				    if(check(i, j, z, form1)){
						/**
						 * ��ʾ�û����������
						 */
						bords[i][j].setIcon(new ImageIcon("image/" + z + "t" + style + ".gif"));
						/**
						 * �����û����������
						 */
						form1[i][j] = form2[i][j] = z;
						/**
						 * ʹz��λ
						 */
						i = j = z = 0;
						/**
						 * �����ر��Ӵ���,����Ч��"�ر�"
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
						 * ��ʾ�û����벻�Ϸ�
						 */
						checkAndPrint(i, j, z, form1);
						/**
						 * ��ԭform1��form2��ֵ
						 */
						form1[i][j] = form2[i][j] = temp;
						/**
						 * �ر� subFrame
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
	 * �������һ������ı��
	 */
	public void getRandomForm(){
		/**
		 * ��������downCheck  �� upCheck����
		 */
		int[] x = new int[1], y = new int[1];
		x[0] = y[0] = 0;
		/**
		 * ��ʼ����һ����
		 */
		form1[0][0]	= form2[0][0] = (int)(Math.random() * 9 + 1);
		/**
		 * ���������������,a,b ����������꣬c�������ֵ��i��������ѭ��
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
		 * ����downCheck()���һ����õı��form2
		 */
		downCheck(x,y,form1,form2);
		/**
		 * ��ɢform2��˳��ʹ�ñ���޹��ɻ�,��������form1
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
	 * �����ı���Ϸ���Ѷ�
	 * @param n �������ı���Ѷ�
	 */
	public void setLvl(int n){
		if(n < 1)
			JOptionPane.showMessageDialog(null, "��Ϊ��򵥵ļ���!");
		else if(n > 5)
			JOptionPane.showMessageDialog(null,"��Ϊ���ѵļ���");
		else
			this.lvl = n;
	}
	/**
	 * �����Ϸ���Ѷ�
	 * @return ��Ϸ���Ѷ�ֵ
	 */
	public int getLvl(){
		return this.lvl;
	}
	/**
	 * ��ʼһ���µ���Ϸ
	 * @param n ��Ϸ���Ѷ�
	 */
	public void newGame(int n){
		//�����һ����Ŀ
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				form1[i][j] = form2[i][j] = form3[i][j] = 0;
				bords[i][j].setIcon(new ImageIcon("image/0.gif"));
			}
		}
		/**
		 * ����н�Ҫ��ʾ���û��ĸ��ӵĸ���
		 */
		int number = 31 + (3 - n);
		/**
		 * һ�����Ѿ���ʾ�˵����ֵĸ���
		 */
		int already[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		/**
		 *  ��ĿǰΪֹ�ܹ��Ѷ���ʾ�� �����֣����ÿ��3�� ����� 27 ��������Ϊ  ��29 - 27�� �� ��33 - 27��
		 *  �����ܱ�9 ����������ÿ�в����ܶ���һ��ֻ����Щ�ж�һ��������������Щ�в����ӣ�
		 */
		int total = 0;
		//����ÿ��Ҫ��ʾ���ӵĸ���
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(already[j] != 0)
				    total += (already[j] - 3);
			}
			//���ʣ�µ�ÿ�����ֻ����ʾ3������涨ÿ����ʾ3��
			if((number - total) % 9 == 0)
				already[i] = 3;
			//���ʣ�µ�ֻ������һ�ж�һ���������Ϊ4��
			else if((number - total) % 9 == 1)
				already[i] = 4;
			//��������´�3��4 ��5��ѡ��һ��
			else
				already[i] = (int)(Math.random() * 3) + 3;
			//��total ��λ 
			total = 0;
		}
		//����һ������ı��
		getRandomForm();
		int[][] displayed = new int[9][5]; //��Ҫ��ʾ���û�������
		boolean isRight;//�����ѭ���н��õ�
		do{
			//��λform1
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
		//����form1��form4
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
	 * ����
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
			JOptionPane.showMessageDialog(null, "�����޽⣡");
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					form2[i][j] = form1[i][j];//��ԭform2
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
	 * �����õ�n ��0~8 �в�ͬ�����֣��������� ����arr�� 
	 * @param n Ҫ�õ���n ������
	 * @param arr �������������
	 */
	public void difNumbers(int n, int[] arr){
		int already = 0; // �Ѿ������˵����ֵĸ��� 
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
		}while(already != n);//ֱ��������n������
		java.util.Arrays.sort(arr);// ��arr��������
	}
	/**
	 * ������ݺͽ���
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
	 * ����û��Ƿ�����
	 * @return �������ͷ���TRUE������FALSE
	 */
	public boolean isOver(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(form1[i][j] == 0)
					return false;
			}
		}
		JOptionPane.showMessageDialog(null, "��Ϸ������\nCONGRATULATIONS��");
		return true;
	}
	/**
	 * ʹ�û�������
	 */
	public void enableInput(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++)
				bords[i][j].setEnabled(true);
		}
	}
	/**
	 * ����form1 ��ֵ
	 * @param x x����
	 * @param y y����
	 * @return form[x][y]
	 */
	public int getForm_1(int x, int y){
		return this.form1[x][y];
	}
	/**
	 * �ı�ָ������Button �ı���
	 * @param x0 ��ʼx����
	 * @param y0 ��ʼy����
	 * @param x1 ����x����
	 * @param y1 ����y����
	 */
	public void setBackground(int x0, int y0, int x1, int y1){
		for(int i = x0; i <= x1; i++){
			for(int j = y0; j <= y1; j++)
				bords[i][j].setBackground(Color.red);
		}
	}
	/**
	 *  �ı�form1 ��ֵ��ֻҪ���ڽ��������뷽��
	 * @param x x����
	 * @param y y����
	 * @param val Ҫ�ı��ֵ
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
	 * �������Ե�main����
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
		System.out.println("�ϵ�����");
	}*/
}
