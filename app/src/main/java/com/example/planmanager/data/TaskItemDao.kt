package com.example.planmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskItemDao {
    @Insert
    suspend fun insert(task: TaskItem)

    @Delete
    suspend fun delete(task: TaskItem)

    @Update
    suspend fun update(task: TaskItem)

    @Query("SELECT * FROM TaskItem")
    fun getAllLocalTasks() : Flow<MutableList<TaskItem>?>

    @Query("SELECT * FROM TaskItem WHERE " +
            "(isDeadline = 1 AND startDateDeadline <= date('now','localtime') AND dateDeadline >= date('now','localtime'))" +
            "OR (isToDo = 1 AND dateToDo <= date('now','localtime')) " +
            "OR (isSchedule = 1 AND dateSchedule = date('now','localtime'))"+
            "ORDER BY universalDateUsingStartDate DESC, timeCreated DESC")
    fun getAllLocalTasksToday() : Flow<MutableList<TaskItem>?>

    @Query("SELECT * FROM TaskItem WHERE " +
            "(isDeadline = 1 AND startDateDeadline <= :date AND dateDeadline >= :date)" +
            "OR (isToDo = 1 AND dateToDo <= :date) " +
            "OR (isSchedule = 1 AND dateSchedule = :date)"+
            "ORDER BY universalDateUsingStartDate DESC, timeCreated DESC")
    fun getAllLocalTasksOnDate(date: String) : Flow<MutableList<TaskItem>?>
}