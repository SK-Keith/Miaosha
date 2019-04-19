package method;

/**
 * @author YMX
 * @date 2019/4/18 16:11
 */
public class test {
    public static void main(String[] args){
        Object o=new Object(){
            //重写了equals(),不管参数是什么，都是返回true
            public boolean equals(Object obj){
                return true;
            }
        };
        System.out.println(o.equals("Fred"));
    }
}
