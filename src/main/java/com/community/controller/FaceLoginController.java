package com.community.controller;


import com.alibaba.fastjson.JSONObject;
import com.community.mapper.UserMapper;
import com.community.pojo.User;
import com.community.service.LoignService;
import com.community.service.NotifyService;
import com.wln.util.FaceUtil;
import com.wln.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
public class FaceLoginController {

    @Autowired
    private upload upload;
    @Autowired
    private LoignService loignService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotifyService notifyService;

    @RequestMapping("/faceController")
    public void faceLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        //1、获取客户上传的图片--上传到指定的文件夹中upimg
        File file= ImageUtils.uploadImg(request, "imgData", "upimg");
        boolean res=false;
        boolean delFlag=true;
        boolean temofalg = false;
        try {
            //2、判断是否包含人脸信息： detect
            String faceToken= FaceUtil.detect(file);
            //判定用户请求的类型

            if(faceToken!=null){// 2.1 包含：
                // 在faceset中查找是否有相似度高的人脸信息： search
                String type=request.getParameter("type");
                res= FaceUtil.search(faceToken);//    有：登录成功；删除照片 没有：登录失败；删除照片

                if("login".equals(type)) {//如果是登录
                if (res){
                    List<User> allUser = userMapper.findAllUser();
                    RestTemplate restTemplate = new RestTemplate();
                    for (User user : allUser){
                        if (user.getFaceUrl() == null || user.getFaceUrl() == "" || user.getFaceUrl().length()<=1){
                            continue;
                        }
                        String url = "https://api-cn.faceplusplus.com/facepp/v3/compare";
                        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
                        map.add("api_key","6BM84NbTZ2gJ2cmeLLKL8BXmcf9MxbDJ");
                        map.add("api_secret","fsrRkcUDugWtxmsPVvpGcsbhSUA9nsgn");
                        map.add("face_token1",faceToken);
                        map.add("image_url2",user.getFaceUrl());
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        HttpEntity<MultiValueMap<String, String>> requestone = new HttpEntity<MultiValueMap<String, String>>(map, headers);
                        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestone, String.class);
                        String body = responseEntity.getBody();
                        System.out.println(body);
                        JSONObject jsonObject = JSONObject.parseObject(body);
                        Object confidence = jsonObject.get("confidence");
                        System.out.println(confidence);
                        double aDouble = Double.parseDouble(confidence.toString());
                        if (aDouble>80){
                            temofalg = true;
                            session.setAttribute("user",user);
                            Integer countNotifys = notifyService.countNotifys(user.getUserCode());
                            session.setAttribute("countNotifys",countNotifys);
                            break;
                        }
                    }
                    res = temofalg;

                }}

                if("register".equals(type)) {//如果是注册
                    if(res) {//有：已经注册过;删除照片
                        res=false;
                    }else {//没有：可以注册，添加facetoken到faceset中；保留照片
                        User user = (User) session.getAttribute("user");
                        if (user.getFaceUrl() == null || user.getFaceUrl() == "" || user.getFaceUrl().length()<1){
                            FileInputStream input = new FileInputStream(file);
                            // 图片文件
                            MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(),"image/jpeg", input );
                            String faceurl = (String)this.upload.Upload(multipartFile, session);
                            Integer registerFaceLogin = loignService.registerFaceLogin(user.getUserName(), faceurl);
                            if (registerFaceLogin !=0){
                                res= FaceUtil.addFace(faceToken);
                            }
                        }
                        delFlag=false;//不删除照片
                    }
                }
            }else {// 2.2 不包含：登录失败；删除照片

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //删除照片
            if(delFlag) {
                file.delete();
            }
            //返回数据给客户端 response
            boolean success = res;
            System.out.println(res);
            //返回数据给客户端 response
            PrintWriter pw=response.getWriter();
            String msg="{\"success\":"+res+"}";
            pw.write(msg);
            pw.close();
        }
    }
}
