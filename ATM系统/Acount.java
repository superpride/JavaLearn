public class Acount {
    private String name;
    private String sex;
    private String idCard;
    private String passWord;
    private double money;
    private double moneyLimit;

    Acount()
    {

    }

    Acount(String name,String sex,String idCard,String passWord)
    {
        this.idCard = idCard;
        this.money = 0;
        this.moneyLimit = 5000;
        this.name = name;
        this.passWord = passWord;
        this.sex = sex;
    }

    public String getName()
    {
        if(sex.equals("男"))
        return this.name + "先生";
        else
        return this.name + "女士";
    }
    public String getidCard()
    {
        return this.idCard;
    }
    public String getSex()
    {
        return this.sex;
    }
    public String getPassWord()
    {
        return this.passWord;
    }
    public double getMoney()
    {
        return this.money;
    }
    public double getMoneyLimit()
    {
        return this.moneyLimit;
    }
    public void setMoney(double money)
    {
        this.money = money;
    }
    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

}
