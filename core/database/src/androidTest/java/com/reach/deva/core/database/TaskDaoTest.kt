/*
 * Copyright 2023 Reach2027
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reach.deva.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.reach.deva.core.database.dao.SubTaskDao
import com.reach.deva.core.database.dao.TaskDao
import com.reach.deva.core.database.model.SubTaskEntity
import com.reach.deva.core.database.model.TaskEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskDaoTest {

    private lateinit var db: AppDatabase

    private lateinit var taskDao: TaskDao

    private lateinit var subTaskDao: SubTaskDao

    @BeforeTest
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()
        taskDao = db.taskDao()
        subTaskDao = db.subTaskDao()
    }

    @AfterTest
    fun closeDb() {
        db.close()
    }

    @Test
    fun getTasksMapTest() = runTest {
        taskData()
        val tasks = taskDao.getTasks().first()
        assertEquals(3, tasks.size)
    }

    @Test
    fun getTaskDetailTest() = runTest {
        val parent2 = newTask()
        delay(50L)
        val child21 = newChildTask(parent2.id)
        delay(50L)
        val child22 = newChildTask(parent2.id)

        taskDao.upsertTasks(parent2)
        subTaskDao.upsertSubTasks(child21, child22)

        val detail = taskDao.getTaskDetail(parent2.id).first()
        assertEquals(2, detail[parent2]?.size)
    }

    private suspend inline fun taskData() {
        taskDao.upsertTask(newTask())
        delay(50L)

        val parent1 = newTask()
        delay(50L)
        val child1 = newChildTask(parent1.id)
        delay(50L)
        val child2 = newChildTask(parent1.id)
        delay(50L)
        val child3 = newChildTask(parent1.id)
        delay(50L)
        val child4 = newChildTask(parent1.id)

        val parent2 = newTask()
        delay(50L)
        val child21 = newChildTask(parent2.id)
        delay(50L)
        val child22 = newChildTask(parent2.id)

        taskDao.upsertTasks(parent1, parent2)

        subTaskDao.upsertSubTasks(child1, child2, child3, child4, child21, child22)
    }

    private fun newTask(): TaskEntity = TaskEntity(
        id = UUID.randomUUID().toString(),
        createTime = Clock.System.now(),
        updateTime = Clock.System.now(),
        finishTime = null,
        content = "test content",
        description = "test desc",
        finished = false,
    )

    private fun newChildTask(parentId: String): SubTaskEntity = SubTaskEntity(
        id = UUID.randomUUID().toString(),
        parentId = parentId,
        createTime = Clock.System.now(),
        updateTime = Clock.System.now(),
        finishTime = null,
        content = "test content",
        description = "test desc",
        finished = false,
    )
}
