import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> list = new ArrayList<>();
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作:1.登录 2.注册 3.忘记密码");
            String choose = sc.next();
            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("谢谢使用,再见");
                    System.exit(0);
                }
                default -> System.out.println("没有这个选项");
            }
        }
    }

    private static void login(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; true; i++) {
            System.out.println("请输入用户名:");
            String username = sc.next();
            //判断用户名是否存在
            boolean flag = contains(list, username);
            if (!flag) {
                System.out.println("用户名:" + username + "未注册,请先注册再登录!");
                return;
            }
            System.out.println("请输入密码:");
            String password = sc.next();
            while (true) {
                String rightCode = getCode();
                System.out.println("请输入当前正确的验证码为:" + rightCode);
                System.out.println(rightCode);
                System.out.println("请输入验证码:");
                String code = sc.next();
                if (code.equalsIgnoreCase(rightCode)) {
                    System.out.println("验证码输入正确");
                    break;
                } else {
                    System.out.println("验证码输入错误");
                }
            }
            //验证用户名和密码是否正确
            //判断集合中是否包含当前用户名和密码
            //定义方法验证用户名和密码是否正确
            //封装思想:把零散的数据封装成一个对象
            //传递参数的时候传递一个整体就行,不需要管这些零散的数据
            User useInfo = new User(username, password, null, null);
            boolean result = checkUserInfo(list, useInfo);
            if (result) {
                System.out.println("登录成功!进入学生管理系统.");
                //创建对象,调用方法,启动学生管理系统
                StudentSystem ss = new StudentSystem();
                ss.startStudentSystem();
                break;
            } else {
                System.out.println("登录失败,用户名或密码错误!");
                if (i == 2) {
                    System.out.println("当前账号" + username + "被锁定,请联系客服:+86 17854255163");
                    //当前账户锁定之后,直接结束方法
                    return;
                } else {
                    System.out.println("用户名或密码错误,还剩下" + (2 - i) + "次机会");
                }
            }
        }
    }

    private static boolean checkUserInfo(ArrayList<User> list, User useInfo) {
        //遍历集合,判断用户是否存在,如果存在登录成功,如果不存在登录失败
        for (User user : list) {
            if (user.getUsername().equals(useInfo.getUsername()) && user.getPassword().equals(useInfo.getPassword())) {
                return true;
            }
        }
        return false;
    }

    private static void register(ArrayList<User> list) {
        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        String personID;
        String phoneNumber;
        while (true) {
            System.out.println("请输入用户名:");
            username = sc.next();
            //开发细节:先验证格式是否正确,再验证是否唯一
            //所有的数据都是存在数据库中的
            boolean flag = checkUsername(username);
            if (!flag) {
                System.out.println("用户名格式不满足条件,需重新输入:");
                continue;
            }
            //此时格式满足,校验用户名是否唯一
            //username到集合中判断是否有存在的
            boolean flag2 = contains(list, username);
            if (flag2) {
                //用户名已存在,无法注册,需要重新注册
                System.out.println("用户名" + username + "已存在,请重新输入");
            } else {
                //不存在,用户名可以使用,继续录入其他数据
                System.out.println("用户名" + username + "可用");
                break;
            }
        }
        //2.键盘录入密码
        while (true) {
            System.out.println("请输入要注册的密码:");
            password = sc.next();
            System.out.println("请再次输入一次密码:");
            String againPassword = sc.next();
            if (!password.equals(againPassword)) {
                System.out.println("两次密码输入错误,请重新输入");
            } else {
                System.out.println("密码一致,继续录入其他数据");
                break;
            }
        }
        //3.键盘录入身份证号码
        while (true) {
            System.out.println("请输入身份证号码");
            personID = sc.next();
            boolean flag = checkPersonID(personID);
            if (flag) {
                System.out.println("身份证号码满足要求.");
                break;
            } else {
                System.out.println("身份证号码格式有误,请重新输入:");
            }
        }
        //4.键盘录入手机号码
        while (true) {
            System.out.println("请输入手机号码:");
            phoneNumber = sc.next();
            boolean flag = checkPhoneNumber(phoneNumber);
            if (flag) {
                System.out.println("手机号码格式正确");
                break;
            } else {
                System.out.println("手机号码格式有误,请重新输入:");
            }
        }
        //把用户信息添加到集合中
        User u = new User(username, password, personID, phoneNumber);
        list.add(u);
        System.out.println("注册成功!");
        printList(list);
    }

    private static void printList(ArrayList<User> list) {
        for (User user : list) {
            System.out.println(user.getUsername() + ",  " + user.getPassword() + ",  " + user.getPersonID() + ",  " + user.getPhoneNumber());
        }
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        //验证长度为11位
        if (phoneNumber.length() != 11) {
            return false;
        }
        //不能以数字0为开头
        if (phoneNumber.startsWith("0")) {
            return false;
        }
        //必须都是数字,遍历
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username = sc.next();
        boolean flag = contains(list, username);
        if (!flag) {
            System.out.println("当前用户" + username + "未注册,请先注册");
            return;
        }
        //键盘录入身份证和手机号
        System.out.println("请输入身份证号码:");
        String personID = sc.next();
        System.out.println("请输入手机号码:");
        String phoneNumber = sc.next();
        //比较输入的身份证和手机号是否正确
        //取出用户对象by索引
        int index = findIndex(list, username);
        User user = list.get(index);
        //比较用户对象中的手机号码和身份证
        if (!user.getPersonID().equalsIgnoreCase(personID) && user.getPhoneNumber().equals(phoneNumber)) {
            System.out.println("身份证号码或手机号码有误,不能修改密码");
            return;
        }
        //代码执行到这里,所有数据验证成功,直接修改密码即可.
        String password;
        while (true) {
            System.out.println("请输入新的密码");
            password = sc.next();
            System.out.println("请确认新密码");
            String againPassword = sc.next();
            if (password.equals(againPassword)) {
                System.out.println("两次密码输入一致");
                break;
            } else {
                System.out.println("两次密码输入不一致,请重新输入");
            }
        }
        //直接修改
        user.setPassword(password);
        System.out.println("密码修改成功");
    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean checkPersonID(String personID) {
        if (personID.length() != 18) {
            return false;
        }
        boolean flag = personID.startsWith("0");
        if (flag) {
            return false;
        }
        for (int i = 0; i < personID.length() - 1; i++) {
            char c = personID.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        char endChar = personID.charAt(personID.length() - 1);
        return (endChar >= '0' && endChar <= '9') || (endChar == 'x') || (endChar == 'X');
    }

    private static boolean contains(ArrayList<User> list, String username) {
        //循环遍历集合,得到每一个用户对象,拿出用户对象的用户名进行比较
        for (User user : list) {
            String rightUsername = user.getUsername();
            if (rightUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkUsername(String username) {
        //1.校验长度
        int length = username.length();
        if (length < 3 || length > 15) {
            return false;
        }
        //2.1长度符合要求,校验字母和数字的组合
        // 循环得到每一个字符
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }
        //执行到这里,表示用户名满足长度和内容的要求(字母+数字)
        //2.2校验不能是纯数字
        //统计在用户名中,有多少字母
        int count = 0;
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
                break;
            }
        }
        return count > 0;
    }

    private static String getCode() {
        //1.创建一个集合,添加所有的大小写字母
        //不能加数字,结果会导致验证码数字出现多次
        ArrayList<Character> list2 = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list2.add((char) ('a' + i));
            list2.add((char) ('A' + i));
        }
        //2.随机抽取四个字符
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            //获取随机索引
            int index = r.nextInt(list2.size());
            char c = list2.get(index);
            sb.append(c);
        }
        //3.把随机数字添加到末尾
        int number = r.nextInt(10);
        sb.append(number);
        //4.修改字符串中的内容:将字符串变成字符数组,在数组中修改,再创建一个新的字符串
        char[] arr = sb.toString().toCharArray();
        int randomIndex = r.nextInt(5);
        //最大索引与随机索引的元素进行交换
        char temp = arr[randomIndex];
        arr[randomIndex] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;
        return new String(arr);
    }
}
