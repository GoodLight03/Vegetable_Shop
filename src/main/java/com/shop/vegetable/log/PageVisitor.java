package com.shop.vegetable.log;

import java.sql.Time;
import java.util.Date;

import org.apache.log4j.Logger;

import com.shop.vegetable.entity.Users;

public class PageVisitor {
    private static final Logger logger=LogFactory.getLogger();
    public void visit(String name, Date ddte){
        logger.info(name+" Visit Shop "+ddte);
        //logger.warn(users+"Login"+time);
        
    }
}
