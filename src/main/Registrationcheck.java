package main;
class Registrationcheck {
    //账号check
    public static boolean checkAccount(String account){
        //账号必须是10位数字,第一位不能是0
        String accCheck = "[^0]\\d{9}$";
        return account.matches(accCheck);
    }
    //密码check
    public static boolean checkPassword(String password){
        //密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-10之间
        String passCheck ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";
        return password.matches(passCheck);
    }
    //姓名check
    public static boolean checkName(String name){
        //姓名必须是中文，至少有一位
        String namecheck = "[\\u4e00-\\u9fa5]+";
        return name.matches(namecheck);
    }}
