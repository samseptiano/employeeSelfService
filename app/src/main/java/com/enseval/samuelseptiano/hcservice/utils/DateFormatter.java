package com.enseval.samuelseptiano.hcservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public String indonesiaDate(String date){
        Date date1= null;
        String dateFormat="";
        try {
            date1 = new SimpleDateFormat("yyyy-mm-dd").parse(date);
            dateFormat = date1.toString().substring(7,10)+" "+date1.toString().substring(4,7)+" "+date1.toString().substring(30,34);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat;

    }
}
