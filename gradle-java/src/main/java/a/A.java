package a;

/**
 * @author Java4ye
 * @date 2021/2/3 8:25
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class A {
    private static final A a = new A();

    private A() {
    }

    public static A getA() {
        return a;
    }
}
