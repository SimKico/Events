package com.app.events.constants;

/**
 * SectorConstants
 */
public class SectorConstants {

    /*
        info of persisted sector from data2.sql script
    */
    public static final Long PERSISTED_SECTOR_ID = 1l;
    public static final String PERSISTED_SECTOR_NAME = "sektor1";
    public static final int PERSISTED_SECTOR_COLUMNS = 1;
    public static final int PERSISTED_SECTOR_ROWS = 1;
    public static final Long PERSISTED_SECTOR_HALL_ID = 1l;

    public static final Long PERSISTED_SECTOR_ID2 = 2l;
    public static final Long PERSISTED_SECTOR_ID3 = 3l;
    public static final String PERSISTED_SECTOR_NAME3 = "sektor3";
    public static final int PERSISTED_SECTOR_COLUMNS3 = 1;
    public static final int PERSISTED_SECTOR_ROWS3 = 1;
    public static final Long PERSISTED_SECTOR_HALL_ID3 = 2l;
    public static final int INVALID_SECTOR_COLUMNS = -2;
    public static final int INVALID_SECTOR_ROWS = -2;

    /*
        constants for pesisting new sector in db
    */
    public static final String VALID_SECTOR_NAME_FOR_PERSISTANCE = "Sector10";

    public static final Long INVALID_SECTOR_ID = -1l;
    public static final Long INVALID_SECTOR_HALL_ID = -1l;
	public static final String URI_PREFIX = "/api/sector/";
}
