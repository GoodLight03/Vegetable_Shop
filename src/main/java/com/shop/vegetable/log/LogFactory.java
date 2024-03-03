package com.shop.vegetable.log;

import org.apache.log4j.Logger;

public class LogFactory {
    public static Logger getLogger(){
        //Thay gọi class-> Khi chạy không phân biệt là hàm nào
        //Kết quả:LogFactory.class--> LogFactory:81 - Quang signed in
        Logger logger=Logger.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
        return logger;
    }
}
