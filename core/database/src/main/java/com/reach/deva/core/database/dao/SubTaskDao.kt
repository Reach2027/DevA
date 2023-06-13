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

@Dao
interface SubTaskDao {

    @Upsert
    suspend fun upsertSubTask(task: SubTaskEntity)

    @Upsert
    suspend fun upsertSubTasks(vararg task: SubTaskEntity)

    @Query(
        """
        DELETE FROM sub_tasks
        WHERE id = :id
    """,
    )
    suspend fun deleteSubTask(id: String)

    @Query(
        """
        DELETE FROM sub_tasks
        WHERE id in (:ids)
    """,
    )
    suspend fun deleteSubTasks(vararg ids: String)
}
