package com.chixing.controller;

import com.chixing.common.JsonResult;
import com.chixing.common.ResultStatus;
import com.chixing.entity.Customer;
import com.chixing.service.CustomerService;
import com.chixing.util.GetMessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("gotoLogin")
    public String gotoLogin(){
        System.out.println("跳转到登录界面");
        return "login";
    }

    //获得手机验证码
    @RequestMapping("getCode")
    @ResponseBody
    public String sendSMS(String phone){
        System.out.println("获得前端请求，导入controller层......................");
        //手机验证码
        /*String code = GetMessageCode.getCode(phone);*/
        String code = "123456";
        System.out.println("code========="+code);
        return code;
    }

    //手机号登录
    @RequestMapping("loginByPhone")
    @ResponseBody
    public JsonResult loginByTelno(long telno,HttpServletRequest request){
        System.out.println("login   loginByPhone=========================================");
        System.out.println("telno====="+telno);
        //1、查看该用户是否存在过，若存在，则从数据库中调出其信息，并且将其放进session中
        Customer customer = customerService.getByTelno(telno);
        System.out.println("customer:"+customer);
        if(customer!=null){//数据库中有
            request.getSession().setAttribute("customerTelno",customer.getCustTelno());
            request.getSession().setAttribute("customerId",customer.getCustId());
            System.out.println("session中已经放入值");
            Map<String,Object> data = new HashMap<>();
            data.put("customerTelno",customer.getCustTelno());
            return JsonResult.createSuccessJsonResult(data);
        }else {//数据库中没有，则添加一条记录 customer==null
            Customer newCustomer = new Customer();
            newCustomer.setCustTelno(telno);
            boolean flag = customerService.save(newCustomer);
            if(flag){
                //添加成功，登陆成功，先查询出来在绑定session
                customer = customerService.getByTelno(telno);
                request.getSession().setAttribute("customerTelno",customer.getCustTelno());
                request.getSession().setAttribute("customerId",customer.getCustId());
                Map<String,Object> data = new HashMap<>();
                data.put("customerTelno",customer.getCustTelno());
                return JsonResult.createSuccessJsonResult(data);
            }else {//添加失败
                return JsonResult.createFailJsonResult(null);
            }

        }

    }

    //获得用户登录手机号
    @RequestMapping("getLoginTelno")
    @ResponseBody
    public JsonResult getLoginTelno( HttpServletRequest request){
        System.out.println("getLoginTelno.....................................");
        Object result =  request.getSession().getAttribute("customerTelno");
        System.out.println("result============"+result);
        if(result!=null) { //已登录
            long customerTelno = (long) result;
            Map<String, Object> data = new HashMap<>();
            data.put("customerTelno", customerTelno);
            return JsonResult.createHasLoginJsonResult(data);
        }else{
            return JsonResult.createNeedLoginJsonResult(null);//未登录
        }
    }

    //退出操作
    @RequestMapping("logout")
    @ResponseBody
    public JsonResult logout(HttpServletRequest request){
        System.out.println("用户退出");
        request.getSession().invalidate();
        return JsonResult.createSuccessJsonResult(null);
    }
    @RequestMapping("goToInfo")
    public String goToInfo(){
        return "info";
    }

    //根据手机号查询用户信息
    @RequestMapping("info")
    @ResponseBody
    public JsonResult getInfoByTelno(long telno){
        System.out.println("根据手机号查询用户信息........");
        Customer customer = customerService.getByTelno(telno);
        Map<String,Object> data = new HashMap<>();
        data.put("customer",customer);
        return JsonResult.createSuccessJsonResult(data);
    }

    //修改用户头像
    @RequestMapping("changelogo")
    @ResponseBody
    public JsonResult changelogo(HttpServletRequest request, MultipartFile upload){
        System.out.println("用户修改头像上传操作处理。。。。。。。");
        String path = request.getServletContext().getRealPath("/upload");
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        String fileName =   upload.getOriginalFilename();
        String uuid =    UUID.randomUUID().toString();

        fileName = uuid+"_"+fileName;
        try {
            File uploadFile = new File(file,fileName);
            upload.transferTo(uploadFile);
            Map<String ,Object> data = new HashMap<>();
            data.put("filePath","upload/"+fileName);
            return JsonResult.createSuccessJsonResult(data);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.createFailJsonResult(null);
        }

    }


}
