package store.chinaotec.com.medicalcare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by seven on 2018/3/5 0007.
 */
public abstract class DataBaseManager<T> implements IDatabaseManager,DatabaseOpenHelper.SQLiteDataTable {

    private static final String TAG = "sqlite_log";
    private SQLiteDatabase mSqLiteDatabase;
    private int id;

    public DataBaseManager(Context mContext){
        DatabaseOpenHelper mDatabaseOpenHelper = DatabaseOpenHelper.getDatabaseOpenHelper(mContext);
        //设置事件监听
        mDatabaseOpenHelper.setOnSQLiteDataTable(this);
        //获取SQLiteDatabase对象，创建或打开数据库
        mSqLiteDatabase = mDatabaseOpenHelper.getWritableDatabase();
    }

    /**
     * 插入数据操作
     * @param mContentValues 插入的数据集合
     * @return boolean 布尔值，true为插入成功，false插入失败
     */
    @Override
    public boolean insert(ContentValues mContentValues) {
        mSqLiteDatabase.beginTransaction();
        try {
            long rowId = mSqLiteDatabase.insertOrThrow(getTableName(), null, mContentValues);
            mSqLiteDatabase.setTransactionSuccessful();
            return rowId != -1;
        } catch (Exception e) {
            Log.e(TAG, "The insert operation failed");
        }finally{
            mSqLiteDatabase.endTransaction();
        }
        return false;
    }

    /**
     * 更新数据操作
     * @param mContentValues 需要更新的数据集合
     * @return boolean 布尔值，true为更新成功，false更新失败
     */
    @Override
    public boolean update(ContentValues mContentValues) {
        mSqLiteDatabase.beginTransaction();
        try {
            int rows = mSqLiteDatabase.update(getTableName(), mContentValues,  "record_id = ?",
                    new String[]{String.valueOf(getRecordDiseaseID())});

            mSqLiteDatabase.setTransactionSuccessful();
            return rows > 0;
        } catch (Exception e) {
            Log.e(TAG, "The update operation failed");
        }finally{
            mSqLiteDatabase.endTransaction();
        }
        return false;
    }

    /**
     * 删除数据操作
     * @return boolean 布尔值，true为删除成功，false删除失败
     */
    @Override
    public boolean delete(int id) {
        mSqLiteDatabase.beginTransaction();
        try {
            int rows = mSqLiteDatabase.delete(getTableName(), getPrimayKeyID() +"= ?", new String[]{String.valueOf(id)});
            mSqLiteDatabase.setTransactionSuccessful();
            return rows > 0;
        } catch (Exception e) {
            Log.e(TAG, "The delete operation failed");
        }finally{
            mSqLiteDatabase.endTransaction();
        }
        return false;
    }

    /**
     * 使用标准SQL语句查询数据列表
     * @param sql 标准SQL语句
     * @return mList 查询后的数据列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<T> query(String sql) {

        ArrayList<T> mList = new ArrayList<T>();
        Cursor mCursor = mSqLiteDatabase.rawQuery(sql, null);
        while(mCursor.moveToNext()){
            T mObject = getResultFromCursor(mCursor);
            mList.add(mObject);
        }
        return mList;
    }

    /**
     * 根据ID查询数据
     * @param id 需要查询的数据ID
     * @return T 查询后获取到的对象
     */
    @Override
    public T query(int id) {
        Cursor mCursor = mSqLiteDatabase.query(getTableName(), null, getPrimayKeyID() + "=?", new String[]{String.valueOf(id)}, null, null, null);
        return getResultFromCursor(mCursor);
    }

    /**
     * 执行一些较为复杂的CRUD操作
     */
    @Override
    public void execSQL(String sql) {

    }

    /**
     * 执行对数据库中数据的操作后关闭数据库
     */
    @Override
    public void closeDB() {
        mSqLiteDatabase.close();
    }

    /**
     * 从子类获取表名
     * @return String 表的名称
     */
    public abstract String getTableName();
    /**
     * 获取表的主键名称
     * @return String 主键名
     */
    public abstract String getPrimayKeyID();
    /**
     * 获取疾病ID
     */
    public abstract int getRecordDiseaseID();
    /**
     * 使用Cursor转换对象
     * @param mCursor 需要转换的Cursor对象
     * @return T 创建的对象
     */
    public abstract T getResultFromCursor(Cursor mCursor);
}
