package com;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MainController {

    @RequestMapping(value = "/report/{reportId}", method = RequestMethod.PUT)
    public HttpStatus update(@RequestBody QueryCriteria queryCriteria, @PathVariable int reportId) {
        HibernateDBUtil.updateOrInsert(queryCriteria, reportId);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/report/{reportId}", method = RequestMethod.DELETE)
    public HttpStatus deleteReport(@PathVariable int reportId) {
        HibernateDBUtil.deleteReport(reportId);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/report", method = RequestMethod.DELETE)
    public HttpStatus deleteReports() {
        HibernateDBUtil.deleteReports();
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/report/{reportId}", method = RequestMethod.GET)
    public ResponseEntity<ReportEntity> getReport(@PathVariable int reportId){
        ReportEntity re = HibernateDBUtil.getReport(reportId);
        return new ResponseEntity<>(re, HttpStatus.OK);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity getReports() {
        List results = HibernateDBUtil.getReports();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
