package store.chinaotec.com.medicalcare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.javabean.DiseaseRecord;

/**
 * Created by seven on 2018/3/5 0005.
 * 突发伤病浏览记录表
 */

public class UpdateRecordTable extends DataBaseManager<DiseaseRecord> {

    //表的名称
    private static final String RECORD_TABLE_NAME = "record_disease";
    private static final String RECORD_FIELD_ID = "id";
    private static final String RECORD_FIELD_VERSIONNAME = "name";
    private static final String RECORD_FIELD_STARTTIME = "start_time";
    private static final String RECORD_FIELD_IMAGE = "image";
    private static final String RECORD_FIELD_RECORDID = "record_id";
    private static final String RECORD_FIELD_URL = "url";
    //定义主键是否为空
    private static final int PRIMAY_KEY_IS_NULL = -1;
    private int memberSickDealId;

    public UpdateRecordTable(Context mContext) {
        super(mContext);
    }

    public void insertRecordObject(DiseaseRecord mRecord) {
        insert(createParams(mRecord));
    }

    public boolean checkSickDealId(int id){
        memberSickDealId = id;
        ArrayList<DiseaseRecord> diseaseRecords = queryAllRecords();
        for (int i = 0;i <diseaseRecords.size();i++){
            if (id == diseaseRecords.get(i).getMemberSickDealId()){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getRecordDiseaseID() {
        return !"null".equals(memberSickDealId) ? memberSickDealId : 0;
    }

    public void updateRecordObject(DiseaseRecord mRecord) {
        update(createParams(mRecord));
    }

    public void updateRecord(int id, String name,long startTime,int recordId, String image) {
        update(createParams(id, name, startTime,recordId,image));
    }

    public ArrayList<DiseaseRecord> queryAllRecords() {
        String sql = "select * from " + RECORD_TABLE_NAME + " order by " + RECORD_FIELD_STARTTIME +" desc";
        return query(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase mSqLiteDatabase) {
        // 创建表的SQL语句
        String DATABASE_CREATE_PERSON_TABLE = "CREATE TABLE "
                + RECORD_TABLE_NAME + " (" + "" + RECORD_FIELD_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ""
                + RECORD_FIELD_VERSIONNAME + " VARCHAR NOT NULL," + ""
                + RECORD_FIELD_STARTTIME + " VARCHAR NOT NULL," + ""
                + RECORD_FIELD_IMAGE + " VARCHAR NOT NULL," + ""
                + RECORD_FIELD_RECORDID + " INTEGER(10) NOT NULL," + ""
                + RECORD_FIELD_URL + " VARCHAR NOT NULL" + ")";
        // 执行创建表的操作
        mSqLiteDatabase.execSQL(DATABASE_CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase mSqLiteDatabase) {
        mSqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE_NAME);
    }

    /**
     * 创建参数集合
     * @param mRecord
     * @return mValues
     */
    public ContentValues createParams(DiseaseRecord mRecord) {
        ContentValues mValues = new ContentValues();
        if (mRecord.getId() != PRIMAY_KEY_IS_NULL) {
            mValues.put(RECORD_FIELD_ID, mRecord.getId());
        }
        mValues.put(RECORD_FIELD_VERSIONNAME, mRecord.getName());
        mValues.put(RECORD_FIELD_STARTTIME, mRecord.getStartTime());
        mValues.put(RECORD_FIELD_IMAGE, mRecord.getImage());
        mValues.put(RECORD_FIELD_RECORDID,mRecord.getMemberSickDealId());
        mValues.put(RECORD_FIELD_URL,mRecord.getUrl());
        return mValues;
    }

    /**
     * 创建参数集合
     * @param id
     * @param name
     * @param startTime
     * @return mValues
     */
    public ContentValues createParams(int id, String name, long startTime,int recordId,String image) {
        ContentValues mValues = new ContentValues();
        if (id != PRIMAY_KEY_IS_NULL) {
            mValues.put(RECORD_FIELD_ID, id);
        }
        mValues.put(RECORD_FIELD_VERSIONNAME, name);
        mValues.put(RECORD_FIELD_STARTTIME, startTime);
        mValues.put(RECORD_FIELD_RECORDID,recordId);
        mValues.put(RECORD_FIELD_IMAGE, image);
        return mValues;
    }

    @Override
    public String getTableName() {
        return RECORD_TABLE_NAME;
    }

    @Override
    public String getPrimayKeyID() {
        return RECORD_FIELD_ID;
    }

    @Override
    public DiseaseRecord getResultFromCursor(Cursor mCursor) {
        DiseaseRecord mRecord = new DiseaseRecord();
        mRecord.setId(mCursor.getInt(mCursor.getColumnIndex(RECORD_FIELD_ID)));
        mRecord.setName(mCursor.getString(mCursor.getColumnIndex(RECORD_FIELD_VERSIONNAME)));
        mRecord.setStartTime(mCursor.getLong(mCursor.getColumnIndex(RECORD_FIELD_STARTTIME)));
        mRecord.setMemberSickDealId(mCursor.getInt(mCursor.getColumnIndex(RECORD_FIELD_RECORDID)));
        mRecord.setImage(mCursor.getString(mCursor.getColumnIndex(RECORD_FIELD_IMAGE)));
        mRecord.setUrl(mCursor.getString(mCursor.getColumnIndex(RECORD_FIELD_URL)));
        return mRecord;
    }
}
