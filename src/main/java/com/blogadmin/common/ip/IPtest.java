package com.blogadmin.common.ip;


public class IPtest {
    public static void main(String[] args) {
        // 指定纯真数据库的文件名，所在文件夹
        IPSeeker ip = new IPSeeker("QQWry.Dat", "f:/");
        String temp = "169.254.111.173";
        // 测试IP 58.20.43.13
        System.out.println(ip.getIPLocation(temp).getCountry() + ":" + ip.getIPLocation(temp).getArea());
    }

}
