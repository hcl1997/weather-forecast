package com.chixing.service;

import com.chixing.entity.Note;

import java.util.List;

public interface NoteService {
    public boolean save(Note note);
    public Note getOne(int custId);
    public List<Note> getLastTen(int custId);
    /*
    * 分页查询
    * */
    public List<Note> getAll(int pageNum);
    //获取游记总记录数
    public int getCount();










}
