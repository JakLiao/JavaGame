import java.io.*;

public class FixedLengthStringIO {
    /**
     * 从输入流中读入指定长度的String 
     * @param size 长度
     * @param in 输入流
     * @return String
     * @throws IOException
     */
	public static String readFixedLengthString(int size, DataInput in) throws IOException{
    	 //定义一个用来储存字符的数组
		char[] chars = new char[size];
		//向chars中写入指定长度的字符
		for(int i = 0; i < size; i++)
			chars[i] = in.readChar();
		return new String(chars);
     }
	/**
	 * 向输出流中写入指定长度的String
	 * @param s 要写入的String
	 * @param size 长度
	 * @param out 输出流
	 * @throws IOException
	 */
	public static void writeFixedLengthString(String s, int size, DataOutput out) throws IOException{
		char[] chars = new char[size];
		//将字符从该String复制到 chars
		s.getChars(0, Math.min(size, s.length()), chars, 0);
		//补充空白
		for(int i = Math.min(size, s.length()); i < chars.length; i++)
			chars[i] = ' ';
		out.writeChars(new String(chars));
	}
	//用来初始化五个排名榜
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
