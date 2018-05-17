package store.chinaotec.com.medicalcare.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *该类是SQLiteDatabase的帮助类，主要管理数据库的创建和版本的更新
 * Created by on 2018/3/5  0007.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static DatabaseOpenHelper mDatabaseOpenHelper;
    /**
     * 数据库版本号
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "record_manager";

    /**
     * 定义一个事件监听回调，将创建表和更新数据库表的操作让子类实现
     */
    public SQLiteDataTable mDataTable;

    public interface SQLiteDataTable{
        public void onCreate(SQLiteDatabase mSqLiteDatabase);
        public void onUpgrade(SQLiteDatabase mSqLiteDatabase);
    }

    public void setOnSQLiteDataTable(SQLiteDataTable mDataTable){
        this.mDataTable = mDataTable;
    }

    /**
     * 初始化数据库信息
     * @param context 应用程序上下文
     * @param name 数据库名称
     * @param factory cursor工厂对象
     * @param version 数据库版本号
     * @param errorHandler 数据库错误处理对象
     */
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, new DatabaseErrorHandler() {

            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
            }
        });
    }

    /**
     * 使用单例模式，获取数据库唯一实例
     * @param mContext 应用程序上下文
     * @return mDatabaseOpenHelper 该对象用于获取SQLiteDatabase实例
     */
    public synchronized static DatabaseOpenHelper getDatabaseOpenHelper(Context mContext){
        if(mDatabaseOpenHelper == null){
            mDatabaseOpenHelper = new DatabaseOpenHelper(mContext);
        }
        return mDatabaseOpenHelper;
    }

    /**
     * 创建数据库时调用，一般执行创建表的操作
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一系列的数据库表
        mDataTable.onCreate(db);
    }

    /**
     * 当数据库需要修改的时系统会自动调用此方法。一般我们在这个方法里边删除数据库表，
     * 并建立新的数据库表，当然是否还需要其它操作，完全取决于应用的需求
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDataTable.onUpgrade(db);
        onCreate(db);
    }
}
