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

package com.reach.deva.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.reach.deva.core.database.model.SubTaskEntity
import com.reach.deva.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query(
        """
        SELECT * FROM tasks 
        LEFT JOIN sub_tasks ON tasks.id = sub_tasks.parent_id 
        ORDER BY tasks.update_time DESC, sub_tasks.update_time DESC 
    """,
    )
    fun getTasks(): Flow<Map<TaskEntity, List<SubTaskEntity>>>

    @Query(
        """
        SELECT * FROM tasks 
        LEFT JOIN sub_tasks ON tasks.id = sub_tasks.parent_id 
        WHERE tasks.id = :id
        ORDER BY tasks.update_time DESC, sub_tasks.update_time DESC 
    """,
    )
    fun getTaskDetail(id: String): Flow<Map<TaskEntity, List<SubTaskEntity>>>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Upsert
    suspend fun upsertTasks(vararg task: TaskEntity)

    @Query(
        """
        DELETE FROM tasks
        WHERE id = :id
    """,
    )
    suspend fun deleteTask(id: String)

    @Query(
        """
        DELETE FROM tasks
        WHERE id in (:ids)
    """,
    )
    suspend fun deleteTasks(vararg ids: String)
}
