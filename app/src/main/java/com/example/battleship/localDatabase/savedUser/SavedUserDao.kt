package com.example.battleship.localDatabase.savedUser

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.battleship.localDatabase.savedUser.SavedUser
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(savedUser: SavedUser):Long

    @Query("DELETE FROM savedUsers WHERE account = :account")
    suspend fun unRemember(account:String)

    @Query("SELECT * FROM savedUsers")
    fun getSavedUsers(): Flow<List<SavedUser>>

    @Query("DELETE FROM savedUsers")
    suspend fun deleteAll()
}