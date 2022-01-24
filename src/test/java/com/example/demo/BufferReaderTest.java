package com.example.demo;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BufferReaderTest {

    public static void main(String[] args) throws ParseException, IOException {

        String basePath = "~/Desktop/";

        BufferedReader br = new BufferedReader(new FileReader(basePath + "001.sql"));
        BufferedReader br2 = new BufferedReader(new FileReader(basePath + "002.sql"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(basePath + "out.sql"));

        String str;String str2;String str3;
        int i = 0;
        while ((str = br.readLine()) != null && (str2 = br2.readLine()) != null){


            //String out = "INSERT INTO admin.t_code(referral_code, principal_name, team_name) VALUES('"+str2+"', '"+str3+"', '"+str+"');\n";
            //String out = "{\"value\": \"" + str2 + "\",\"label\": \"" + str3 + "\",\"parentId\": \""+str+"\"},";

            String out = "update cms_referral_code set principal_id_card = '"+str+"' where referral_code  = '"+str2+"';\n";

            System.out.println(out);
            bw.write(out);
            bw.flush();
        }
        br.close();
        bw.close();
    }



    public static void main3(String[] args) throws ParseException, IOException {

        String basePath = "~/Desktop/";

        BufferedReader br = new BufferedReader(new FileReader(basePath + "b1.sql"));
        BufferedReader br2 = new BufferedReader(new FileReader(basePath + "b2.sql"));
        BufferedReader br3 = new BufferedReader(new FileReader(basePath + "b3.sql"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(basePath + "out.sql"));

        String str;String str2;String str3;
        int i = 0;
        while ((str = br.readLine()) != null && (str2 = br2.readLine()) != null && (str3 = br3.readLine()) != null){


            //String out = "INSERT INTO admin.t_code(referral_code, principal_name, team_name) VALUES('"+str2+"', '"+str3+"', '"+str+"');\n";
            String out = "{\"value\": \"" + str2 + "\",\"label\": \"" + str3 + "\",\"parentId\": \""+str+"\"},";

            System.out.println(out);
            bw.write(out);
            bw.flush();
        }
        br.close();
        bw.close();
    }

    public static void main2(String[] args) throws ParseException, IOException {

        String basePath = "~/Desktop/";

        BufferedReader br = new BufferedReader(new FileReader(basePath + "org.sql"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(basePath + "out.sql"));

        String str;
        int i = 0;
        while ((str = br.readLine()) != null){
            String out = "{\"value\": \"" + (++i) + "\",\"label\": \"" + str + "\",\"parentId\": \"\"},";

            System.out.println(out);
            bw.write(out);
            bw.flush();
        }
        br.close();
        bw.close();
    }

    public static void main1(String[] args) throws ParseException, IOException {

        String basePath = "~/Desktop/";

        BufferedReader br = new BufferedReader(new FileReader(basePath + "a.sql"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(basePath + "file.sql"));

        String str;
        while ((str = br.readLine()) != null){
            String out = "insert into t_org_test(org_name) values ('" + str + "');\n";

            System.out.println(out);
            bw.write(out);
            bw.flush();
        }
        br.close();
        bw.close();
    }


}
