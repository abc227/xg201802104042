//指示java里io包里FileNotFoundException类和IOException的使用路径
import java.io.FileNotFoundException;
import java.io.IOException;

//创建FileRead类
public class FileRead {
    //定义main方法
    public static void main(String[] args) {
        //使用try子句监视正常代码
        try{
            //向FileUtil发送read的消息，传入201802104042这一参数
            FileUtil.read("201802104042");
        }
        //在catch子句中定义异常类型及流程，使得程序不因异常而终止或者流程发生意外的改变
        catch(FileNotFoundException e){
            //异常处理代码，打印信息
            System.out.println("出现异常");
        }catch(IOException e){
            System.out.println("出现异常");
        }catch(Exception e){
            System.out.println("出现异常");
        }
    }
}
