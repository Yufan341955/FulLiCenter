package ucai.cn.fulicenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;

import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.L;

/**
 * Created by Administrator on 2016/10/24.
 */
public class DBManager {
    private static DBManager dbmgr=new DBManager();
    private static DBHelper mHelper;
    public DBManager(){

    }
    void onInit(Context context){
        if(mHelper==null){
            mHelper=DBHelper.onInit(context);
        }

    }
    public static synchronized DBManager getInstance(){
        return dbmgr;
    }
    public synchronized void closeDB(){
        if(mHelper!=null){
            mHelper.closeDB();
        }
    }

    public synchronized boolean savaUser(UserAvatar user){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UserDao.USER_NAME,user.getMuserName());
        values.put(UserDao.USER_NICK,user.getMuserNick());
        values.put(UserDao.USER_AVATAR_ID,user.getMavatarId());
        values.put(UserDao.USER_AVATAR_TYPE,user.getMavatarType());
        values.put(UserDao.USER_AVATAR_PATH,user.getMavatarPath());
        values.put(UserDao.USER_AVATAR_SUFFIX,user.getMavatarSuffix());
        values.put(UserDao.USER_AVATAR_LASTUPDATE_TIME,user.getMavatarLastUpdateTime()+"");
        L.e("DBManager");
        if(db.isOpen()){
            return  db.replace(UserDao.USER_TABLE_NAME,null,values)!=-1;
        }
        return false;
    }

    public synchronized UserAvatar getUser(String username) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql="select * from "+UserDao.USER_TABLE_NAME
                +" where "+UserDao.USER_NAME+" =?";
        UserAvatar user=null;
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        while (cursor.moveToNext()){
            user=new UserAvatar();
            int id = cursor.getInt(cursor.getColumnIndex(UserDao.USER_AVATAR_ID));
            String name=cursor.getString(cursor.getColumnIndex(UserDao.USER_NAME));
            String nick=cursor.getString(cursor.getColumnIndex(UserDao.USER_NICK));
            int type = cursor.getInt(cursor.getColumnIndex(UserDao.USER_AVATAR_TYPE));
            String path = cursor.getString(cursor.getColumnIndex(UserDao.USER_AVATAR_PATH));
            String suffix = cursor.getString(cursor.getColumnIndex(UserDao.USER_AVATAR_SUFFIX));
            long lastime=Integer.valueOf(cursor.getString(cursor.getColumnIndex(UserDao.USER_AVATAR_LASTUPDATE_TIME)));
            user.setMavatarId(id);
            user.setMuserName(name);
            user.setMuserNick(nick);
            user.setMavatarPath(path);
            user.setMavatarType(type);
            user.setMavatarSuffix(suffix);
            user.setMavatarLastUpdateTime(lastime);
        }
        return user;
    }

    public boolean updateUser(UserAvatar user) {
        int result=-1;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UserDao.USER_NAME,user.getMuserName());
        values.put(UserDao.USER_NICK,user.getMuserNick());
        values.put(UserDao.USER_AVATAR_ID,user.getMavatarId());
        values.put(UserDao.USER_AVATAR_TYPE,user.getMavatarType());
        values.put(UserDao.USER_AVATAR_PATH,user.getMavatarPath());
        values.put(UserDao.USER_AVATAR_SUFFIX,user.getMavatarSuffix());
        values.put(UserDao.USER_AVATAR_LASTUPDATE_TIME,user.getMavatarLastUpdateTime()+"");
        if(db.isOpen()){
            result = db.update(UserDao.USER_TABLE_NAME,values,UserDao.USER_NAME+"=?",new String[]{user.getMuserName()});
        }
        return result>0;
    }
}
