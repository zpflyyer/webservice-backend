package com.hui.personal.util;

import com.hui.personal.WebServiceConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DateUtil implements WebServiceConstants{
    public static String getNow() {
        return new SimpleDateFormat(standardFormat).format(new Date());
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(standardFormat).format(date);
    }

    public static String getSnapShot(){
        return new SimpleDateFormat(snapFormat).format(new Date());
    }

    public static String getLatestFileIfExists(){
        File dir = new File(pvParentPath);
        List<Long> snapShots = new ArrayList<>();

        if (dir.exists() && dir.isDirectory()){
            File[] files = dir.listFiles();
            Arrays.asList(files).stream()
                    .filter(file -> file.getName().endsWith(suffix))
                    .forEach(file ->  {
                        String fileName = file.getName();
                        log.info("file name: \"{}\"",fileName);
                        snapShots.add(Long.valueOf(fileName.substring(0, snapFormat.length())));
            });
            log.info("{} ticfile file(s) founded", snapShots.size());
        } else {
            log.warn("no ticfile founded");
        }

        if (0 == snapShots.size()){
            return null;
        } else {
            Long latest = snapShots.get(0);
            for (Long snapShot :
                    snapShots) {
                latest = snapShot > latest ? snapShot : latest;
            }
            return String.valueOf(pvParentPath + File.separator + latest + suffix);
        }
    }

    public static boolean withinTwoDays(Calendar wholeHour){
        Calendar now = Calendar.getInstance();
        Calendar tommorow = Calendar.getInstance();
        tommorow.add(Calendar.DATE,1);
        tommorow.set(Calendar.HOUR_OF_DAY, 23);

        return !now.after(wholeHour) && !tommorow.before(wholeHour);
    }
}
