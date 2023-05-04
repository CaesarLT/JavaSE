import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    //主方法
    public static void startStudentSystem() {
        ArrayList<Student> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------");
            System.out.println("| 欢迎来到学生管理系统! |");
            System.out.println("------------------------");
            System.out.println("* 1:添加学生           *");
            System.out.println("* 2:删除学生           *");
            System.out.println("* 3:修改学生           *");
            System.out.println("* 4:查询学生           *");
            System.out.println("* 5:退出               *");
            System.out.println("------------------------");
            System.out.println("请输入您的选择:");
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent(list);
                case "2" -> deleteStudent(list);
                case "3" -> updateStudent(list);
                case "4" -> queryStudent(list);
                case "5" -> {
                    System.out.println("退出");
                    //停止虚拟机运行,Idea右上角的X
                    System.exit(0);
                }
                default -> System.out.println("选项输入错误!");
            }
        }
    }

    //添加学生
    public static void addStudent(ArrayList<Student> list) {
        Student stu = new Student();
        Scanner sc = new Scanner(System.in);
        String id;
        while (true) {
            System.out.println("请输入学生的id");
            id = sc.next();
            boolean flag = contains(list, id);
            if (flag) {
                //表示id已经存在,需要重新录入
                System.out.println("已经存在id为" + id + "的学生,请重新录入id");
            } else {
                //表示id不存在,可以录入
                stu.setId(id);
                break;
            }
        }
        System.out.println("请输入学生的姓名");
        String name = sc.next();
        stu.setName(name);
        System.out.println("请输入学生的年龄");
        int age = sc.nextInt();
        stu.setAge(age);
        System.out.println("请输入学生的家庭住址");
        String address = sc.next();
        stu.setAddress(address);
        //将学生对象添加到集合中
        list.add(stu);
        //提示一下用户
        System.out.println("学生信息添加成功!");
    }

    //删除学生
    public static void deleteStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除的学生id:");
        String id = sc.next();
        //查询id在集合中的索引
        int index = getIndex(list, id);
        if (index >= 0) {
            list.remove(index);
            System.out.println("id为" + id + "的学生删除成功.");
        } else {
            System.out.println("id不存在,删除失败.");
        }
    }

    //修改学生
    public static void updateStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改学生的id:");
        String id = sc.next();
        int index = getIndex(list, id);
        if (index == -1) {
            System.out.printf("要修改的id:%s不存在,请重新输入", id);
            System.out.println();
            //方法直接结束,回到主菜单
            return;
        }
        //当前id是存在的,其对应的索引是index
        //获取要修改的学生对象
        Student stu = list.get(index);
        //输入其他的学生信息并修改
        System.out.println("请输入修改后的学生姓名:");
        String newName = sc.next();
        stu.setName(newName);
        System.out.println("请输入修改后的学生年龄:");
        int newAge = sc.nextInt();
        stu.setAge(newAge);
        System.out.println("请输入修改后的学生家庭住址:");
        String newAddress = sc.next();
        stu.setAddress(newAddress);
    }

    //查询学生
    public static void queryStudent(ArrayList<Student> list) {
        if (list.size() == 0) {
            System.out.println("当前无学生信息,请添加后再查询");
            return;
        }
        //打印表头信息,制表符凑齐八个字符或者是八的倍数
        System.out.println("id\t\t姓名\t\t年龄\t\t家庭住址");
        for (Student stu : list) {
            System.out.println(stu.getId() + "\t\t" + stu.getName() + "\t\t" + stu.getAge() + "\t\t" + stu.getAddress());
        }
        System.out.println();
    }

    //判断id在集合中是否存在
    public static boolean contains(ArrayList<Student> list, String id) {
        //循环遍历集合,得到每一个学生对象的学生id
        //存在:true,不存在:false
        return getIndex(list, id) >= 0;
    }

    //通过id获取索引,存在返回正值,不存在返回负值
    public static int getIndex(ArrayList<Student> list, String id) {
        //遍历集合,得到每一个学生对象
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            //得到每一个学生对象的id
            String sid = stu.getId();
            //拿着集合中的学生id跟要删除的id惊醒比较,如果一样,则返回索引
            if (sid.equals(id)) {
                return i;
            }
        }
        //循环结束后还没有找到,表示不存在索引,返回-1.
        return -1;
    }
}