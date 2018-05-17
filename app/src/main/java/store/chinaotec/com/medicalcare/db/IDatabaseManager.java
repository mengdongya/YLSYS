package store.chinaotec.com.medicalcare.db;

import android.content.ContentValues;

import java.util.ArrayList;

/**
 * Created by seven on 2018/3/5 0007.
 */
public interface IDatabaseManager {
    public boolean insert(ContentValues mContentValues);
    public <T> ArrayList<T> query(String sql);
    public Object query(int id);
    public boolean update(ContentValues mContentValues);
    public boolean delete(int id);
    public void execSQL(String sql);
    public void closeDB();
}
