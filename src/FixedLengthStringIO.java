import java.io.*;

public class FixedLengthStringIO {
    /**
     * ���������ж���ָ�����ȵ�String 
     * @param size ����
     * @param in ������
     * @return String
     * @throws IOException
     */
	public static String readFixedLengthString(int size, DataInput in) throws IOException{
    	 //����һ�����������ַ�������
		char[] chars = new char[size];
		//��chars��д��ָ�����ȵ��ַ�
		for(int i = 0; i < size; i++)
			chars[i] = in.readChar();
		return new String(chars);
     }
	/**
	 * ���������д��ָ�����ȵ�String
	 * @param s Ҫд���String
	 * @param size ����
	 * @param out �����
	 * @throws IOException
	 */
	public static void writeFixedLengthString(String s, int size, DataOutput out) throws IOException{
		char[] chars = new char[size];
		//���ַ��Ӹ�String���Ƶ� chars
		s.getChars(0, Math.min(size, s.length()), chars, 0);
		//����հ�
		for(int i = Math.min(size, s.length()); i < chars.length; i++)
			chars[i] = ' ';
		out.writeChars(new String(chars));
	}
	//������ʼ�����������
	/*public static void main(String[] arg) throws IOException{
		for(int i = 1; i < 6; i++){
			File file = new File("rank_" + i + ".dat");
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			for(int j = 0; j < 3; j++){
				FixedLengthStringIO.writeFixedLengthString("???", 3, out);
				out.writeInt(999999);
			}
			out.close();
		} 
	}*/
}
