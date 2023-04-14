package com.servlet;

import com.bean.GamesBean;
import com.google.gson.Gson;
import com.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@WebServlet("/dow")
public class FileDow extends BaseServlet {
    public static void downloadNet(HttpServletResponse response) throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL("windine.blogdriver.com/logo.gif");

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("c:/abc.gif");

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

//    public byte[] getFile(String url) throws IOException {
//        URL urlConet = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) urlConet.openConnection();
//        con.setRequestMethod("GET");
//        con.setConnectTimeout(4 * 1000);
//        InputStream inputStream = con.getInputStream();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[2048];
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteArrayOutputStream.write(buffer, 0, len);
//        }
//        inputStream.close();
//        byte[] data = byteArrayOutputStream.toByteArray();
//        byteArrayOutputStream.close();
//        return data;
//    }

    public void dow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getParameter("path");
        System.out.println(path);
        download(path,resp);

    }
}