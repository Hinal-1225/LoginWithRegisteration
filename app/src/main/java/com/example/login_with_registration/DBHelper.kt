package com.example.login_with_registration

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(var context: Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    companion object {
        private var DB_NAME = "PracticeDB"
        private var TB_USER = "UserDetail"
        private var DB_VERSION = 1
        private val USER_ID = "ur_id"
        private val USER_EMAIL = "ur_email"
        private val USER_CITY = "ur_city"
        private val USER_PASS = "ur_password"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var sql1 = "CREATE TABLE $TB_USER ($USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $USER_EMAIL TEXT,  $USER_CITY TEXT, $USER_PASS TEXT)"
        p0?.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertUser(user: User): Long {
        var db = writableDatabase
        var cn = ContentValues()
        cn.put(USER_EMAIL, user.ur_email)
        cn.put(USER_CITY, user.ur_city)
        cn.put(USER_PASS, user.ur_password)

        var res = db.insert(TB_USER, null, cn)
        return res
        db.close()
    }

    fun getUser(uname: String): ArrayList<User> {
        var db = readableDatabase
        var sql = "Select * from $TB_USER where $USER_EMAIL = '$uname'"
        var arr = ArrayList<User>()
        var cursor = db.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            var id = cursor.getInt(0)
            var email = cursor.getString(1)
            var city = cursor.getString(2)
            var password = cursor.getString(3)
            var user = User(id, email, city, password)

            arr.add(user)
        }
        return arr
        db.close()
    }
}