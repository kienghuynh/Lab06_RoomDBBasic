package com.example.danhsachsinhvien;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface IMainDao {
    @Insert(onConflict = REPLACE)
    void insert(User us);
    @Delete
    void delete(User user);
    @Delete
    void deleteAll(List<User> lstUs);

    @Query("SELECT * FROM user")
    List<User> getAllUser();
}
