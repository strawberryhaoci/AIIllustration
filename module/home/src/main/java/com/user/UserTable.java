package com.user;/*
 *name: AIIllustration
 *description:
 */

/**
 * @program: AIIllustration
 * @description: user
 */
public class UserTable {
    public static final String TABLE_NAME = "userTable";
    public static final String COL_ID = "userid";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_PICNUM = "picNum";
    public static final String COL_ADD = "testAddCol";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            +COL_ID+" integer primary key autoincrement,"
            +COL_USERNAME+" varchar(64),"
            +COL_PASSWORD+" varchar(16),"
            +COL_PICNUM+" integer"
            +")";

    public static final String UPGRADE_TABLE = "alter table "+TABLE_NAME+ " add " +COL_ADD+ " text";
    public static final String DOWNGRADE_TABLE = "drop table if exists "+TABLE_NAME;
}
