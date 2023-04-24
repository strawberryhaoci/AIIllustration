package com.user;/*
 *name: AIIllustration
 *description:
 */

/**
 * @program: AIIllustration
 * @description: pic sql
 */
public class PicTable {
    public static final String TABLE_NAME = "picTable";
    public static final String COL_ID = "picid";
    public static final String COL_DES = "des";
    public static final String COL_REDRAWFACTOR = "redrawfactor";
    public static final String COL_SEED = "seed";
    public static final String COL_USERNAME = "username";
    public static final String COL_ORIPATH = "oripath";
    public static final String COL_GENPATH = "genpath";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            +COL_ID+" integer primary key autoincrement,"
            +COL_USERNAME+" varchar(64),"
            +COL_DES+" varchar(64),"
            +COL_SEED+" integer,"
            +COL_REDRAWFACTOR+" integer,"
            +COL_ORIPATH+" varchar(64),"
            +COL_GENPATH+" varchar(64)"
            +")";
    public static final String DOWNGRADE_TABLE = "drop table if exists "+TABLE_NAME;


}
