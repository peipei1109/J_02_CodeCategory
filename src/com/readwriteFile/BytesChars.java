package com.readwriteFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

//区分字节流和字符流
public class BytesChars {

	public BytesChars() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args){
		//todo
		
	}

}

/*
 * 先来看一下流的概念：

在程序中所有的数据都是以流的方式进行传输或保存的，程序需要数据的时候要使用输入流读取数据，而当程序需要将一些数据保存起来的时候，就要使用输出流完成。

程序中的输入输出都是以流的形式保存的，流中保存的实际上全都是字节文件。

字节流与字符流
在java.io包中操作文件内容的主要有两大类：字节流、字符流，两类都分为输入和输出操作。在字节流中输出数据主要是使用OutputStream完成，输入使的是InputStream，在字符流中输出主要是使用Writer类完成，输入流主要使用Reader类完成。（这四个都是抽象类）

操作流程
在Java中IO操作也是有相应步骤的，以文件操作为例，主要的操作流程如下：

使用File类打开一个文件
通过字节流或字符流的子类，指定输出的位置
进行读/写操作
关闭输入/输出
IO操作属于资源操作，一定要记得关闭



字节流
字节流主要是操作byte类型数据，以byte数组为准，主要操作类就是OutputStream、InputStream

 

字节输出流：OutputStream

OutputStream是整个IO包中字节输出流的最大父类，此类的定义如下：

public abstract class OutputStream extends Object implements Closeable,Flushable

从以上的定义可以发现，此类是一个抽象类，如果想要使用此类的话，则首先必须通过子类实例化对象，那么如果现在要操作的是一个文件，则可以使用：FileOutputStream类。通过向上转型之后，可以为OutputStream实例化

Closeable表示可以关闭的操作，因为程序运行到最后肯定要关闭

Flushable：表示刷新，清空内存中的数据

FileOutputStream类的构造方法如下：

public FileOutputStream(File file)throws FileNotFoundException

写数据：
 */



class Test11 {
	
	
    public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        OutputStream out=new FileOutputStream(f);//如果文件不存在会自动创建
        String str="Hello World";
        byte[] b=str.getBytes();
        out.write(b);//因为是字节流，所以要转化成字节数组进行输出
        out.close();
    }
}

/*
 * 也可以一个字节一个字节进行输出，如下：
 */

class Test12 {
    public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        OutputStream out=new FileOutputStream(f);//如果文件不存在会自动创建
        String str="Hello World";
        byte[] b=str.getBytes();
        for(int i=0;i<b.length;i++){
            out.write(b[i]);
        }
        out.close();
    }
    
    
    
}

/*
 * 以上输出只会进行覆盖，如果要追加的话，请看FileOutputStream类的另一个构造方法：

public FileOutputStream(File file,boolean append)throws FileNotFoundException

在构造方法中，如果将append的值设置为true，则表示在文件的末尾追加内容。
 */

class Test13 {
    public void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        OutputStream out=new FileOutputStream(f,true);//追加内容
        String str="\r\nHello World";
        byte[] b=str.getBytes();
        for(int i=0;i<b.length;i++){
            out.write(b[i]);
        }
        out.close();
    }
}


/*
文件中换行为：\r\n

字节输入流：InputStream
既然程序可以向文件中写入内容，则就可以通过InputStream从文件中把内容读取进来，首先来看InputStream类的定义：

public abstract class InputStream extends Object implements Closeable

与OutputStream类一样，InputStream本身也是一个抽象类，必须依靠其子类，如果现在是从文件中读取，就用FileInputStream来实现。

观察FileInputStream类的构造方法：

public FileInputStream(File file)throws FileNotFoundException

读文件：
*/

class Test14 {
    public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        InputStream in=new FileInputStream(f);
        byte[] b=new byte[1024];
        int len=in.read(b);
        in.close();
        System.out.println(new String(b,0,len));
    }
}



/*
 * 但以上方法是有问题的，用不用开辟这么大的一个字节数组，明显是浪费嘛，我们可以根据文件的大小来定义字节数组的大小，File类中的方法：public long length()
 * 
 * 
 */

class Test15 {
	 public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        InputStream in=new FileInputStream(f);
        byte[] b=new byte[(int) f.length()];
        in.read(b);
        in.close();
        System.out.println(new String(b));
    }
}



/*
 * 我们换种方式，一个字节一个字节读入~
 */

 class Test16 {
	 public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        InputStream in=new FileInputStream(f);
        byte[] b=new byte[(int) f.length()];
        for(int i=0;i<b.length;i++){
            b[i]=(byte) in.read();
        }
        in.close();
        System.out.println(new String(b));
    }
}
 
 
 /*
  * 但以上情况只适合知道输入文件的大小，不知道的话用如下方法：
  */
 
class Test17 {
	public  void test() throws IOException {
         File f = new File("d:" + File.separator+"test.txt");
         InputStream in=new FileInputStream(f);
         byte[] b=new byte[1024];
         int temp=0;
         int len=0;
         while((temp=in.read())!=-1){//-1为文件读完的标志
             b[len]=(byte) temp;
             len++;
         }
         in.close();
         System.out.println(new String(b,0,len));
     }
 }//感觉这个类，如果文件多于1024个字节，就会造成数组越界问题


//******************************以下是字符流

/*
 * 字符流
在程序中一个字符等于两个字节，那么java提供了Reader、Writer两个专门操作字符流的类。


字符输出流：Writer
Writer本身是一个字符流的输出类，此类的定义如下：

public abstract class Writer extends Object implements Appendable，Closeable，Flushable

此类本身也是一个抽象类，如果要使用此类，则肯定要使用其子类，此时如果是向文件中写入内容，所以应该使用FileWriter的子类。

FileWriter类的构造方法定义如下：

public FileWriter(File file)throws IOException

字符流的操作比字节流操作好在一点，就是可以直接输出字符串了，不用再像之前那样进行转换操作了。

写文件：
 * 
 */
class Test18 {
	public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        Writer out=new FileWriter(f);
        String str="Hello World";
        out.write(str);
        out.close();
    }
}

/*
 * 
 * 在默认情况下再次输出会覆盖，追加的方法也是在构造函数上加上追加标记
 */

class Test19 {
	public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        Writer out=new FileWriter(f,true);//追加
        String str="\r\nHello World";
        out.write(str);
        out.close();
    }
}


/*
 * 
 * 字符输入流：Reader
Reader是使用字符的方式从文件中取出数据，Reader类的定义如下：

public abstract class Reader extends Objects implements Readable，Closeable

Reader本身也是抽象类，如果现在要从文件中读取内容，则可以直接使用FileReader子类。

FileReader的构造方法定义如下：

public FileReader(File file)throws FileNotFoundException

以字符数组的形式读取出数据：
 */

class Test20 {
	public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        Reader input=new FileReader(f);
        char[] c=new char[1024];
        int len=input.read(c);
        input.close();
        System.out.println(new String(c,0,len));
    }
}


/*
 * 也可以用循环方式，判断是否读到底：

*/
class Test21 {
	public  void test() throws IOException {
        File f = new File("d:" + File.separator+"test.txt");
        Reader input=new FileReader(f);
        char[] c=new char[1024];
        int temp=0;
        int len=0;
        while((temp=input.read())!=-1){
            c[len]=(char) temp;
            len++;
        }
        input.close();
        System.out.println(new String(c,0,len));
    }
}

/*
 * 字节流与字符流的区别
字节流和字符流使用是非常相似的，那么除了操作代码的不同之外，还有哪些不同呢？

字节流在操作的时候本身是不会用到缓冲区（内存）的，是与文件本身直接操作的，而字符流在操作的时候是使用到缓冲区的

字节流在操作文件时，即使不关闭资源（close方法），文件也能输出，但是如果字符流不使用close方法的话，则不会输出任何内容，说明字符流用的是缓冲区，并且可以使用flush方法强制进行刷新缓冲区，这时才能在不close的情况下输出内容



那开发中究竟用字节流好还是用字符流好呢？

在所有的硬盘上保存文件或进行传输的时候都是以字节的方法进行的，包括图片也是按字节完成，而字符是只有在内存中才会形成的，所以使用字节的操作是最多的。



如果要java程序实现一个拷贝功能，应该选用字节流进行操作（可能拷贝的是图片），并且采用边读边写的方式（节省内存）。
 */
