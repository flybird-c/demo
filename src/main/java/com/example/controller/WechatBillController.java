package com.example.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author lzp
 * @version 1.0.0
 * @date 2022/4/27 17:30:26
 * @apiNote 微信账单控制层
 */
@RestController
@RequestMapping
public class WechatBillController {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;
    @GetMapping("startBatch")
    public String runTask(){
        try {
            jobLauncher.run(job,new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
