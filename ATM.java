import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    ArrayList<Acount> acounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private Random r = new Random();
    private Acount currentAcount;
    void start()//��ӭҳ��
    {
        while(true)
        {
            System.out.println("=====��ӭ��ʹ�ñ�ATMϵͳ=====");
            System.out.println("=====��ѡ����Ҫ�����ҵ��=====");
            System.out.println("=====1.ע���˻�=====");
            System.out.println("=====2.��¼�˻�=====");
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                default:
                    System.out.println("û�д˲���");
                    break;
            }
        }
        
    }   
    //ע�Ὺ��
    public void register()
    {   String name= "";
        String sex = "";
        String idCard= "";
        String passWord= "";
        String passWordAgain= " ";
        System.out.println("�������������");
        name = sc.next();
        //�Ա�����ж�
        while(!(sex.equals("��")||sex.equals("Ů")))
        {
            System.out.println("����������Ա�");
            sex = sc.next();
            if(!(sex.equals("��")||sex.equals("Ů")))
            System.out.println("������������������л�Ů");
        }
        //ȷ������
        while(!(passWord.equals(passWordAgain)))
        {
            System.out.println("�������������");
            passWord = sc.next();
            System.out.println("�ٴ�ȷ���������");
            passWordAgain = sc.next();
            if(!(passWord.equals(passWordAgain)))
            System.out.println("�������벻ͬ������������");
        }
        //�������ʮλid����,ͬʱ�ж��Ƿ�����п�����ͬ
        idCard = createIdCard();
        Acount acount = new Acount(name,sex,idCard,passWord);
        acounts.add(acount);
        System.out.println("��ϲ�㣬" + acount.getName() +",�����ɹ�"+",��Ŀ���Ϊ"+acount.getidCard());
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
            //�ж��Ƿ񿨺��ظ�
            //���ݿ��ű������Ͽ��Ƿ����
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
        /*�ж�ϵͳ���Ƿ����˻�����*/
        if(acounts.size() == 0)
        {
            System.out.println("ϵͳ�������˻�������ע�Ῠ��");
            return;
        }
        else
        {
            String idCard = "";
            String passWord = "";

            System.out.println("=====��ӭ�����¼����=====");
            System.out.println("���������Ŀ���");
            idCard = sc.next();
            while(null == getAcountByIdCard(idCard))
            {
                System.out.println("������Ŀ�����������������");
                idCard = sc.next();
            }
            System.out.println("������ȷ������������");
            passWord = sc.next();
            // System.out.println(getAcountByIdCard(idCard).getPassWord());
            // System.out.println(passWord);
            while(!(getAcountByIdCard(idCard).getPassWord().equals(passWord)))
            {
                System.out.println("���������������������");
                passWord = sc.next();
            }
            System.out.println("���ڵ�¼...");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ex)
            {
                System.out.println("�����쳣");
            }
            currentAcount = getAcountByIdCard(idCard);
            System.out.println("��ӭ����"+getAcountByIdCard(idCard).getName());
            //չʾ����ҳ��
            showUserCommand();
            return;
        }
    }

    private void showUserCommand()
    {
        while(true)
        {
            System.out.println("������ѡ�������˻�����");
            System.out.println("1.��ѯ�˻�");
            System.out.println("2.���");
            System.out.println("3.ȡ��");
            System.out.println("4.ת��");
            System.out.println("5.�����޸�");
            System.out.println("6.�˳�");
            System.out.println("7.ע����ǰ�˻�");
            System.out.println("���������ѡ��");
            int select = sc.nextInt();
            switch(select)
            {
                case 1://��ѯ�˻�
                    getAcountInfo();
                    break;
                case 2://���
                    deposit();
                    break;
                case 3://ȡ��
                    withdraw();
                    break;
                case 4://ת��
                    tranMoney();
                    break;
                case 5://�����޸�
                    changePassWord();
                    break;
                case 6://�˳�
                    exit();
                    return;
                case 7://ע����ǰ�˻�
                    if(!(deleteCurrentAcount()))
                        break;
                    else
                        return;
            }
        }
        
    }

    private void getAcountInfo()
    {
        System.out.println("������"+ currentAcount.getName());
        System.out.println("�Ա�"+ currentAcount.getSex());
        System.out.println("���ţ�"+ currentAcount.getidCard());
        System.out.println("���룺"+ currentAcount.getPassWord());
        System.out.println("��"+ currentAcount.getMoney());
        System.out.println("��ȣ�"+ currentAcount.getMoneyLimit());
    }

    private void deposit()
    {
        System.out.println("��������Ҫ��Ľ��");
        while(true)
        {
            double moneyIn = sc.nextDouble();
            if(moneyIn <= 0)
            {
                System.out.println("������Ľ����Ч�����������0�Ľ��");
            }
            else
            {
                currentAcount.setMoney(moneyIn + currentAcount.getMoney());
                break;
            }
        }
        System.out.println("����ɹ����������˻������Ϊ" + currentAcount.getMoney());
    }

    private void withdraw()
    {
        System.out.println("��������Ҫȡ�Ľ��");
        double moneyOut = sc.nextDouble();
        while(moneyOut > currentAcount.getMoneyLimit())
        {
            System.out.println("�������ȡǮ��ȣ��������ȡǮ���Ϊ"+ currentAcount.getMoneyLimit() + "������������");
            moneyOut = sc.nextDouble();
        }
        while(moneyOut > currentAcount.getMoney())
        {
            System.out.println("�����˻����Ϊ��"+currentAcount.getMoney()+"���޷�ȡ��"+moneyOut + "������������");
            moneyOut = sc.nextDouble();
        }
        double moneyNow = currentAcount.getMoney() - moneyOut;
        currentAcount.setMoney(moneyNow);
        System.out.println("ȡǮ�ɹ������˻������Ϊ��" + currentAcount.getMoney());
    }

    private void tranMoney()
    {
        System.out.println("�������տ�Ŀ���");
        String inIdCard ="";
        inIdCard = sc.next();
        while(null == getAcountByIdCard(inIdCard))
        {
            System.out.println("�˿��Ų����ڣ�����������");
            inIdCard = sc.next();
        }
        System.out.println("������ת�˽��");
        double moneyOut = sc.nextDouble();
        while(moneyOut > currentAcount.getMoneyLimit())
        {
            System.out.println("�������ת�˶�ȣ��������ת�˶��Ϊ"+ currentAcount.getMoneyLimit() + "������������");
            moneyOut = sc.nextDouble();
        }
        while(moneyOut > currentAcount.getMoney())
        {
            System.out.println("�����˻����Ϊ��"+currentAcount.getMoney()+"���޷�ת��"+moneyOut);
            moneyOut = sc.nextDouble();
        }
        double moneyNowOut = currentAcount.getMoney() - moneyOut;
        currentAcount.setMoney(moneyNowOut);
        System.out.println("ת�˳ɹ������˻������Ϊ��" + currentAcount.getMoney());
        Acount acountIn = getAcountByIdCard(inIdCard);
        double moneyNowIn = acountIn.getMoney() + moneyOut;
        acountIn.setMoney(moneyNowIn);
    }

    private void changePassWord()
    {
        System.out.println("����������ǰ������");
        String passWardNow = sc.next();
        while(true)
        {
            while(null == getAcountByPassWord(passWardNow))
            {
                System.out.println("���������������������");
                passWardNow = sc.next();
            }
            System.out.println("�����������ĺ������");
            String passWardLater = sc.next();
            while(passWardLater.equals(passWardNow))
            {
                System.out.println("�����벻�����������ͬ������������");
                passWardLater = sc.next();
            }
            System.out.println("���ٴ�����������ȷ��");
            String passWardLaterAgain = sc.next();
            if(!(passWardLaterAgain.equals(passWardLater)))
            {
                System.out.println("�������벻��ͬ����������������");
            }
            else
            {
                System.out.println("�������óɹ�");
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
        System.out.println(currentAcount.getName() + ",���˳�ϵͳ�ɹ�");
    }

    private boolean deleteCurrentAcount()
    {
        System.out.println("��ȷ��Ҫ��������������y/n");
        String select = sc.next();
        switch(select){
            case "y":
            if(currentAcount.getMoney() != 0)
            {
                System.out.println("�����˻�������������");
                return false;
            }
            else
            {
                acounts.remove(currentAcount);
                System.out.println("�ɹ�����");
                return true;
            }
            default:
                System.out.println("�õģ������˻��ѱ���");
                return false;
        }
        
    }
}
