import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    ArrayList<Acount> acounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private Random r = new Random();
    private Acount currentAcount;
    void start()//欢迎页面
    {
        while(true)
        {
            System.out.println("=====欢迎您使用本ATM系统=====");
            System.out.println("=====请选择你要办理的业务=====");
            System.out.println("=====1.注册账户=====");
            System.out.println("=====2.登录账户=====");
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                default:
                    System.out.println("没有此操作");
                    break;
            }
        }
        
    }   
    //注册开户
    public void register()
    {   String name= "";
        String sex = "";
        String idCard= "";
        String passWord= "";
        String passWordAgain= " ";
        System.out.println("请输入你的姓名");
        name = sc.next();
        //性别错误判断
        while(!(sex.equals("男")||sex.equals("女")))
        {
            System.out.println("请输入你的性别");
            sex = sc.next();
            if(!(sex.equals("男")||sex.equals("女")))
            System.out.println("输入错误，请重新输入男或女");
        }
        //确认密码
        while(!(passWord.equals(passWordAgain)))
        {
            System.out.println("请输入你的密码");
            passWord = sc.next();
            System.out.println("再次确认你的密码");
            passWordAgain = sc.next();
            if(!(passWord.equals(passWordAgain)))
            System.out.println("两次密码不同，请重新输入");
        }
        //随机生成十位id卡号,同时判断是否跟已有卡号相同
        idCard = createIdCard();
        Acount acount = new Acount(name,sex,idCard,passWord);
        acounts.add(acount);
        System.out.println("恭喜你，" + acount.getName() +",开户成功"+",你的卡号为"+acount.getidCard());
    }

    public String createIdCard()
    {
        while(true)
        {
            String idCard = "";
            for(int i = 0;i < 10;i++)
            {
                idCard += r.nextInt(10);
            }
            //判断是否卡号重复
            //根据卡号遍历集合看是否相等
            if(getAcountByIdCard(idCard) == null)
            {
                System.out.println(idCard);
                return idCard;
            }
        }
            
        
    }

    public Acount getAcountByIdCard(String idCard)
    {
        for(int i = 0;i < acounts.size();i++)
        {
            Acount acount = acounts.get(i);
            if(acount.getidCard().equals(idCard))
            {
                return acount;
            }
        }
        return null;
    }

    public void login()
    {
        /*判断系统中是否有账户对象*/
        if(acounts.size() == 0)
        {
            System.out.println("系统中暂无账户，请先注册卡号");
            return;
        }
        else
        {
            String idCard = "";
            String passWord = "";

            System.out.println("=====欢迎进入登录界面=====");
            System.out.println("请输入您的卡号");
            idCard = sc.next();
            while(null == getAcountByIdCard(idCard))
            {
                System.out.println("您输入的卡号有误，请重新输入");
                idCard = sc.next();
            }
            System.out.println("卡号正确，请输入密码");
            passWord = sc.next();
            // System.out.println(getAcountByIdCard(idCard).getPassWord());
            // System.out.println(passWord);
            while(!(getAcountByIdCard(idCard).getPassWord().equals(passWord)))
            {
                System.out.println("密码错误，请重新输入密码");
                passWord = sc.next();
            }
            System.out.println("正在登录...");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ex)
            {
                System.out.println("出现异常");
            }
            currentAcount = getAcountByIdCard(idCard);
            System.out.println("欢迎您，"+getAcountByIdCard(idCard).getName());
            //展示操作页面
            showUserCommand();
            return;
        }
    }

    private void showUserCommand()
    {
        while(true)
        {
            System.out.println("您可以选择如下账户处理");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出");
            System.out.println("7.注销当前账户");
            System.out.println("请输入你的选择");
            int select = sc.nextInt();
            switch(select)
            {
                case 1://查询账户
                    getAcountInfo();
                    break;
                case 2://存款
                    deposit();
                    break;
                case 3://取款
                    withdraw();
                    break;
                case 4://转账
                    tranMoney();
                    break;
                case 5://密码修改
                    changePassWord();
                    break;
                case 6://退出
                    exit();
                    return;
                case 7://注销当前账户
                    if(!(deleteCurrentAcount()))
                        break;
                    else
                        return;
            }
        }
        
    }

    private void getAcountInfo()
    {
        System.out.println("姓名："+ currentAcount.getName());
        System.out.println("性别："+ currentAcount.getSex());
        System.out.println("卡号："+ currentAcount.getidCard());
        System.out.println("密码："+ currentAcount.getPassWord());
        System.out.println("余额："+ currentAcount.getMoney());
        System.out.println("额度："+ currentAcount.getMoneyLimit());
    }

    private void deposit()
    {
        System.out.println("请输入你要存的金额");
        while(true)
        {
            double moneyIn = sc.nextDouble();
            if(moneyIn <= 0)
            {
                System.out.println("你输入的金额无效，请输入大于0的金额");
            }
            else
            {
                currentAcount.setMoney(moneyIn + currentAcount.getMoney());
                break;
            }
        }
        System.out.println("存入成功，您现在账户的余额为" + currentAcount.getMoney());
    }

    private void withdraw()
    {
        System.out.println("请输入你要取的金额");
        double moneyOut = sc.nextDouble();
        while(moneyOut > currentAcount.getMoneyLimit())
        {
            System.out.println("超过最大取钱额度，您的最大取钱额度为"+ currentAcount.getMoneyLimit() + "，请重新输入");
            moneyOut = sc.nextDouble();
        }
        while(moneyOut > currentAcount.getMoney())
        {
            System.out.println("您的账户余额为："+currentAcount.getMoney()+"，无法取出"+moneyOut + "，请重新输入");
            moneyOut = sc.nextDouble();
        }
        double moneyNow = currentAcount.getMoney() - moneyOut;
        currentAcount.setMoney(moneyNow);
        System.out.println("取钱成功，您账户的余额为：" + currentAcount.getMoney());
    }

    private void tranMoney()
    {
        System.out.println("请输入收款方的卡号");
        String inIdCard ="";
        inIdCard = sc.next();
        while(null == getAcountByIdCard(inIdCard))
        {
            System.out.println("此卡号不存在，请重新输入");
            inIdCard = sc.next();
        }
        System.out.println("请输入转账金额");
        double moneyOut = sc.nextDouble();
        while(moneyOut > currentAcount.getMoneyLimit())
        {
            System.out.println("超过最大转账额度，您的最大转账额度为"+ currentAcount.getMoneyLimit() + "，请重新输入");
            moneyOut = sc.nextDouble();
        }
        while(moneyOut > currentAcount.getMoney())
        {
            System.out.println("您的账户余额为："+currentAcount.getMoney()+"，无法转出"+moneyOut);
            moneyOut = sc.nextDouble();
        }
        double moneyNowOut = currentAcount.getMoney() - moneyOut;
        currentAcount.setMoney(moneyNowOut);
        System.out.println("转账成功，您账户的余额为：" + currentAcount.getMoney());
        Acount acountIn = getAcountByIdCard(inIdCard);
        double moneyNowIn = acountIn.getMoney() + moneyOut;
        acountIn.setMoney(moneyNowIn);
    }

    private void changePassWord()
    {
        System.out.println("请输入您当前的密码");
        String passWardNow = sc.next();
        while(true)
        {
            while(null == getAcountByPassWord(passWardNow))
            {
                System.out.println("密码错误，请重新输入密码");
                passWardNow = sc.next();
            }
            System.out.println("请输入您更改后的密码");
            String passWardLater = sc.next();
            while(passWardLater.equals(passWardNow))
            {
                System.out.println("新密码不能与旧密码相同，请重新设置");
                passWardLater = sc.next();
            }
            System.out.println("请再次输入新密码确认");
            String passWardLaterAgain = sc.next();
            if(!(passWardLaterAgain.equals(passWardLater)))
            {
                System.out.println("两次输入不相同，请重新设置密码");
            }
            else
            {
                System.out.println("密码设置成功");
                currentAcount.setPassWord(passWardLaterAgain);
                break;
            }
        }
        
    }

    private Acount getAcountByPassWord(String passWard)
    {
        for(int i = 0;i < acounts.size();i++)
        {
            if(acounts.get(i).getPassWord().equals(passWard))
            {
                return acounts.get(i);
            }
        }
        return null;
    }

    private void exit()
    {
        System.out.println(currentAcount.getName() + ",您退出系统成功");
    }

    private boolean deleteCurrentAcount()
    {
        System.out.println("您确定要进行销户操作吗y/n");
        String select = sc.next();
        switch(select){
            case "y":
            if(currentAcount.getMoney() != 0)
            {
                System.out.println("您的账户还余额，不可销户");
                return false;
            }
            else
            {
                acounts.remove(currentAcount);
                System.out.println("成功销户");
                return true;
            }
            default:
                System.out.println("好的，您的账户已保留");
                return false;
        }
        
    }
}
