import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Test {

    static int count;
    static Product proSelected[] = new Product[5];//购物车
    static Product product;
    static Product[] products;
    static String pId;
    static InputStream proIn;
    static ReadProductsExcel readProductsExcel;
    static Scanner scanner = new Scanner(System.in);
    static HashMap<Product, Integer> amount = new HashMap<Product, Integer>();;

    public static void main(String[] args) throws ClassNotFoundException {
        boolean flag = true;
        while (flag) {
            System.out.println("请输入用户名：");
            String testName = scanner.next().trim();
            System.out.println("请输入密码：");
            String testPassword = scanner.next().trim();
/*
        读取文件
        */
//        File file = new File("C:\\Users\\zed\\IdeaProjects\\CmdShop\\src\\users.xlsx");
            InputStream in = Class.forName("Test").getResourceAsStream("/users.xlsx"); // /表示的是classpath
            ReadUsersExcel readUsersExcel = new ReadUsersExcel();
            User[] users = readUsersExcel.getAllUsers(in);

            proIn = Class.forName("Test").getResourceAsStream("/products.xlsx"); // /表示的是classpath
            readProductsExcel = new ReadProductsExcel();
            products = readProductsExcel.getAllProducts(proIn);

            for (User user : users) {
                if (testName.equals(user.getName()) && testPassword.equals(user.getPassword())) {
                    System.out.println("登录成功");
                    flag = false;
                    /*
                    显示商品
                    */
                    /*
                    1、查看购物车
                     （1）购物车是用数组模拟的
                     （2）对数组遍历
                    2、继续购物
                       显示所有的商品
                    */
                    while (true) {
                        System.out.println("查看已购商品请输入1\n购物请输入2\n结账请输入3\n退出商城请输入4");
                        int number = scanner.nextInt();
                        if (number == 1 || number == 2) {
                            //调用Mothod类的mothod方法
                            choose(number);
                        } else if (number == 3) {
                            /*
                             * 1.产生订单（必须有订单类）
                             * 2.用poi创建Order.xlsx文件
                             * 3.把购物车的商品写入Order.xlsx文件
                             * */
                            Order order = new Order();
                            order.setUser(user);//订单关联用户
                            Product carts[] = new Product[count];
                            for(int i = 0 ; i < carts.length; i++){
                                if(proSelected[i] != null) {
                                    carts[i] = proSelected[i];
                                }
                            }
                            order.setProduct(carts);//订单关联商品

                            order.setAmount(amount);

                            //order.setTotalPay();
                            long currentTime = System.currentTimeMillis();
                            Date date = new Date(currentTime);
                            order.setOrderDate(date);
                            //下订单
                            CreateOrder.createOrder(order);

                        } else if (number == 4) {
                            System.out.println("商城已退出！");
                            break;
                        }
                    }
                }
            }
            if (flag == true)
                System.out.println("登录失败");
        }
    }

    //方法抽取
    public static void choose(int number) throws ClassNotFoundException {
        /*
        1、查看购物车
         （1）购物车是用数组模拟的
         （2）对数组遍历
        2、继续购物
           显示所有的商品
        */
        if (number == 1) {
            //显示已购商品
            for (Product p : proSelected) {
                if (p == null) {
                    break;
                }
                System.out.print(p.getProId());
                System.out.print("\t" + p.getProName());
                System.out.print("\t\t" + p.getPrice());
                System.out.print("\t\t" + p.getDecription());
                System.out.println("\t购买数量X" + amount.get(p));
            }
        } else if (number == 2) {
            //显示商品
            for (Product p : products) {
                System.out.print("" + p.getProId());
                System.out.print("\t\t" + p.getProName());
                System.out.print("\t\t" + p.getPrice());
                System.out.println("\t\t" + p.getDecription());
            }
            System.out.println("请输入商品ID和数量:");
            pId = scanner.next();
            int proAmount = scanner.nextInt();
            proIn = Class.forName("Test").getResourceAsStream("/products.xlsx");
//                        ReadProductsExcel readProductsExcel2 = new ReadProductsExcel();
            product = readProductsExcel.getProById(pId, proIn);
            if (product != null) {
                        /*
                        把商品加入购物车
                        */
//                        proIn = Class.forName("Test").getResourceAsStream("/products.xlsx");
                if (count < 5) {
                    proSelected[count] = product;
                    amount.put(proSelected[count],proAmount);
                    count++;
                    System.out.println("商品已加入购物车");
                    System.out.println("");
                } else {
                    System.out.println("购物车已满,无法加入新的商品");
                }
            }
        }
    }
}
