package com.nott.pms.consts;

/**
 * @Author zzzwlong
 * @Date 2022/2/26 21:56
 */
public interface SysConstant {
    public static final String EMP_PERMISSION = "emp";
    public static final String DEPT_PERMISSION = "dept";
    public static final String USER_PERMISSION = "user";
    public static final String SYS_PERMISSION = "sys";
    public static final String MSG_PERMISSION = "msg";
    public static final String File_PERMISSION = "file";

    interface delflag{
        public static final String NORMAL = "0";
        public static final String del = "1";
    }

    interface read{
        public static final String UNREAD = "0";
        public static final String READ = "1";
    }
    interface msgType{
        public static final String MSG = "0";
        public static final String ANNOUNCE = "1";
    }

    interface roleId{
        public static final String NORMAL = "0";
        public static final String SUPADMIN = "1";
        public static final String ADMIN = "2";
    }

    interface status{
        public static final Integer WAIT = 0;
    }
}
