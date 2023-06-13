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

package com.reach.deva.data.task.repository

import com.reach.deva.data.task.local.TaskLocalSource
import com.reach.deva.data.task.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks(): Flow<Map<Task, List<Task>>>

    fun getTaskDetail(id: String): Flow<Map<Task, List<Task>>>

    fun upsertTask(task: Task)

    fun upsertSubTask(task: Task)

    fun deleteTask(id: String)

    fun deleteSubTask(id: String)
}

class DefaultTaskRepository(
    private val localSource: TaskLocalSource,
) : TaskRepository {
    override fun getTasks(): Flow<Map<Task, List<Task>>> = localSource.getTasks()

    override fun getTaskDetail(id: String): Flow<Map<Task, List<Task>>> =
        localSource.getTaskDetail(id)

    override fun upsertTask(task: Task) = localSource.upsertTask(task)

    override fun upsertSubTask(task: Task) = localSource.upsertSubTask(task)

    override fun deleteTask(id: String) = localSource.deleteTask(id)

    override fun deleteSubTask(id: String) = localSource.deleteSubTask(id)
}
