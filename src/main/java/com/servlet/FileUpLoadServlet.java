package com.servlet;

import com.google.gson.Gson;
import com.util.JsonUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author: xj0927
 * @Description:
 * @Date Created in 2021-11-12 15:12
 * @Modified By:
 */
@WebServlet("/FileUpLoadServlet")
public class FileUpLoadServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        resp.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        resp.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        resp.setHeader("Access-Control-Max-Age", "4200");
        /* 允许跨域的请求头 */
        resp.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        DiskFileItemFactory diskFileItemFactory =  new DiskFileItemFactory();

        //2创建ServletFileUpload对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);



        try {


            //如参数为:  url?name=admin&age=10
            //在文件上传的过程，把name=admin 封装在一个FileItem对象中
            //  把age=10 封装在一个另一个  FileItem对象中
            // 把上传的文件内容也放在了一个FileItem对象中
            //解析  reqeust对象，将请求参数封装成List<FileItem>
            // 把前端传递的数据，解析为List<FileItem>集合
            List<FileItem> fileItemList = servletFileUpload.parseRequest(req);


            //4遍历List<FileItem>
            for (FileItem fileItem : fileItemList) {
                if (fileItem.isFormField()) {// 判断FileItem对象封装的数据是属于一个普通表单字段，还是属于一个文件表单字段，如果是普通表单字段返回true，文件字段返回false
                    String paramName = fileItem.getFieldName();//得到请求参数的名称  如url?name=admin&age=10  此方法会分别返回 name  age
                    String paramValue = fileItem.getString();//得到请求参数的值 如返回  admin   10
                    System.out.println("请求参数名为:" + paramName + "请求参数值为:" + paramValue);
                } else {
                    //解析上传的文件内容
                    //上传的文件内容(二进制流)
                    String fileName = fileItem.getName();//得到文件 的名称
                    String contentType = fileItem.getContentType();//得到文件的类型
                    System.out.println("上传的文件名为:" + fileName + "上传的文件类型为:" + contentType);
                    try {
                        //如果上传的文件要通过url访问到，需要将图片保存到tomcat运行该项目的webapp目录下

                        String webappDir = req.getRealPath("/imgs/gameInfo");//

                        System.out.println("tomcat运行该项目的webapp目录:" + webappDir);
                        File fileDir = new File(webappDir);//把路径封装成一个File对象
                        fileDir.mkdirs();//当images目录不存在时，创建目录
                        //   C:\Users\Administrator\Desktop\中级部分项目\后端代码\dormManagerSystem\src\main\webapp\img

                        File filePath = new File(fileDir, fileName);//根据父目录名称和文件名称，创建一个文件对象
                        fileItem.write(filePath);//把上传的文件保存到webapp->images目录下


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        } catch (FileUploadException e) {
            e.printStackTrace();
        }


    }




}
