package com.chixing.controller;

import com.chixing.common.JsonResult;
import com.chixing.entity.Customer;
import com.chixing.entity.Note;
import com.chixing.service.CustomerService;
import com.chixing.service.NoteService;
import com.chixing.util.PageHelperUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private CustomerService customerService;
    @RequestMapping("goToadd")
    public String goToadd(){
        System.out.println("跳转到写游记页面");
        return "note/add";
    }

    //游记头图上传
    @RequestMapping("headImgUpload")
    @ResponseBody
    //文件上传操作处理
    public JsonResult upload(HttpServletRequest request, MultipartFile upload){
        System.out.println("文件上传操作处理。。。。。。。");
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


    //提交游记保存
    @RequestMapping("add")
    @ResponseBody
    public JsonResult save(Note note,HttpServletRequest request){
        System.out.println("添加游记 Controller......");
        int custId = (int)request.getSession().getAttribute("customerId");
        System.out.println("添加游记 custId......"+custId);
        note.setCustId(custId);
        note.setNoteCreateTime(new Date());
        if(this.noteService.save(note)){
            System.out.println("添加成功");
           return JsonResult.createSuccessJsonResult(null);
        }else {
            Map<String,Object> data = new HashMap<>();
            data.put("reason","添加游记失败");
            System.out.println("添加失败");
            return JsonResult.createFailJsonResult(data);
        }

    }

    //跳转到游记详情界面
    @RequestMapping("goToDetail")
    public String goToDetial(){
        return "note/detail";
    }

    //查询我的最近一条的游记
    @RequestMapping("myLastNote")
    @ResponseBody
    public JsonResult myLastNote(HttpServletRequest request){
        System.out.println("查询我的最近一条的游记");
        int custId =(int) request.getSession().getAttribute("customerId");
        System.out.println("custId======"+custId);
        Note note = this.noteService.getOne(custId);
        Map<String,Object> data = new HashMap<>();
        data.put("note",note);
        return JsonResult.createSuccessJsonResult(data);
    }

    //响应页面/ssm_demo/note/gotoMyNote 跳转到我的游记界面
    @RequestMapping("gotoMyNote")
    public String gotoMyNote(){
        System.out.println("跳转到我的游记页面。。。。。");
        return "note/myNoteList";
    }

    //查询最近的十条游记记录
    @RequestMapping("myLastTenNote")
    @ResponseBody
    public JsonResult myLastTenNote(HttpServletRequest request){
        System.out.println("查询我的最近十条记录");
        int custId = (int) request.getSession().getAttribute("customerId");
        System.out.println("custId=========="+custId);
        List<Note> noteList = noteService.getLastTen(custId);
        Map<String,Object> data = new HashMap<>();
        if(noteList!=null && noteList.size()>0){
            Customer customer = customerService.getById(custId);
            data.put("noteList",noteList);
            data.put("customer",customer);
            return JsonResult.createSuccessJsonResult(data);
        }else {
            data.put("reason","您未写任何游记。");
            return JsonResult.createFailJsonResult(null);
        }


    }

    //获取全部游记
    @RequestMapping("getall/{pageNum}")
    @ResponseBody
    public JsonResult getByPage(@PathVariable("pageNum") int pageNum){
        //5条游记
        List<Note> noteList = this.noteService.getAll(pageNum);
        int totalCount = this.noteService.getCount();//游记总记录数
        int totalPage = (int)Math.ceil(totalCount%PageHelperUtil.PAGE_SIZE==0?totalCount/PageHelperUtil.PAGE_SIZE:totalCount/PageHelperUtil.PAGE_SIZE+1);//共几页
        // 游记作者信息
        for (Note note:noteList){
            Customer customer = this.customerService.getById(note.getCustId());
            note.setOthers1(customer.getCustName());
        }
        Map<String,Object> data = new HashMap<>();
        data.put("noteList",noteList);
        data.put("totalCount",totalCount);
        data.put("totalPage",totalPage);
        return JsonResult.createSuccessJsonResult(data);

    }
}
