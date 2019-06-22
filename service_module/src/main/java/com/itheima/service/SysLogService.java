package com.itheima.service;

import com.itheima.domain.SysLog;

import java.util.List;

public interface SysLogService {
    List<SysLog> findAllLogs();
    void saveLog(SysLog sysLog);
}
