//指示java里io包里FileNotFoundException类和IOException的使用路径
import java.io.*;

//创建FileUtil类
public class FileUtil {
    //定义write方法，并抛出FileNotFoundException和IOException异常
    public static void write(String filename,String num) throws FileNotFoundException, IOException {
        //创建BufferedWriter类型的对象，并且他有可以写入东西的属性，定义BufferedWriter类型的变量，令创建变量指向对象
        BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
        //向bf指向的对象里写入num这一参数
        bf.write(num);
        //bf关闭
        bf.close();
    }

    //定义read方法，并抛出FileNotFoundException和IOException异常
    public static void read(String filename) throws FileNotFoundException, IOException {
        //创建BufferedWriter类型的对象，并且他有可以写入东西的属性，定义BufferedWriter类型的变量，令创建变量指向对象
        BufferedReader rd = new BufferedReader(new FileReader(filename));
        //定义String类型变量line，指定他为null
        String line = null;
        //指定int类型变量sum，指定为0
        int sum=0;
        //读取文档直到读取到空内容的一行
        while((line = rd.readLine())!=null){
            //强制类型转换，将读取的内容转换为int型的
            Integer number=new Integer(line);
            //累加读取的每一行数据
            sum+=number;
        }
        //输出sum
        System.out.println(sum);
        //rd关闭
        rd.close();
    }
}
