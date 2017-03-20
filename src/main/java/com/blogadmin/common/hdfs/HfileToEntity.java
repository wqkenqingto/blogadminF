//package com.blogadmin.common.hdfs;
//
//import com.blogadmin.blog.model.Unit;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author wangkuiqing
// * @Date 2016/11/9 10:30
// * @ClassName:HfileToEntity
// * @Description:将hdfs中的文件封装成实体
// */
//public class HfileToEntity {
//    public static List<Unit> htoUnit(String hpath) throws IOException {
//        Configuration conf = new Configuration();
//        FileSystem fs = FileSystem.get(URI.create(hpath), conf);
//        Path path = new Path(hpath);
//        FSDataInputStream in = fs.open(path);
//        byte[] bs = new byte[1024];
//        FileStatus stat = fs.getFileStatus(path);
//        String line, line1;
//        List<String> list = new ArrayList<String>();
//        try {
//            while (in.readLine() != null) {
//                line = in.readLine();
//                line = new String(line.getBytes("iso8859-1"), "UTF-8");
//                String[] ls = line.split("\t");
//                if (list.contains(ls[0])) {
//                    continue;
//                }
//                list.add(ls[0]);
//            }
//        } catch (Exception e) {
//            System.out.println("this is HfileToEntity's Exception --" + e);
//        }
//        System.out.println("list的长度" + list.size());
//        System.out.println("list" + list.toString());
//        List<Unit> units = new ArrayList<Unit>();
//        Unit unit = new Unit();
//        for (String s : list) {
//            unit = EntityProduct.productUnit(s, "c001");
//            units.add(unit);
//        }
//        in.close();
//        fs.close();
//        return units;
//    }
//
//
//}
