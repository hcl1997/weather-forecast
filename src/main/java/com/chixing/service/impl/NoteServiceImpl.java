package com.chixing.service.impl;

import com.chixing.dao.NoteDao;
import com.chixing.entity.Note;
import com.chixing.service.NoteService;

import com.chixing.util.PageHelperUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;
    @Override
    public boolean save(Note note) {
        return noteDao.insert(note)>0;
    }

    @Override
    public Note getOne(int custId) {
        return noteDao.selectByCreateTimeDescOne(custId);
    }

    @Override
    public List<Note> getLastTen(int custId) {
        return noteDao.selectByCreateTimeDescTen(custId);
    }

    @Override
    public List<Note> getAll(int pageNum) {
        //pageHelper拦截所有的select操作，分页
        PageHelper.startPage(pageNum,PageHelperUtil.PAGE_SIZE);
        return this.noteDao.selectByExample(null);
    }

    @Override
    public int getCount() {
        return noteDao.selectCount();
    }


}
