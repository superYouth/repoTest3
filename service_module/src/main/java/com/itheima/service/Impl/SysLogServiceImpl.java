package com.itheima.service.Impl;

import com.itheima.dao.SysLogDao;
import com.itheima.domain.SysLog;
import com.itheima.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;
    @Override
    public List<SysLog> findAllLogs() {
        return sysLogDao.findAll();
    }

    @Transactional
    @Override
    public void saveLog(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }
}
