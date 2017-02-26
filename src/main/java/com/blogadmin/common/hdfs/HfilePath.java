package com.blogadmin.common.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.jcodings.transcode.specific.Eucjp_to_stateless_iso2022jp_Transcoder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangkuiqing
 * @Date 2016/11/9 16:02
 * @ClassName:HfilePath
 * @Description:获取指定文件夹下的文件路径
 */
public class HfilePath {
    static List<String> hpaths = new ArrayList<String>();

    public static List<String> getHfilePath(Configuration conf, String hfilePath) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hfilePath), conf);
        Path path = new Path(hfilePath);
        if (fs.isDirectory(path)) {
            FileStatus[] status = fs.listStatus(path);
            for (FileStatus f : status) {
                getHfilePath(conf, f.getPath().toString());
            }
        } else {
            hpaths.add(path.toString());
        }
        return hpaths;
    }
//    public static void main(String[] args) throws IOException {
//        hpaths= HfilePath.getHfilePath(conf,hfilePah1);
//        System.out.println(hpaths.size());
//        for(String p:hpaths){
//            System.out.println(p);
//        }
//    }
}
