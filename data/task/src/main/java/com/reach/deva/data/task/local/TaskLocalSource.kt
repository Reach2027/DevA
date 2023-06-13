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

package com.reach.deva.data.task.local

import com.reach.deva.core.database.dao.SubTaskDao
import com.reach.deva.core.database.dao.TaskDao
import com.reach.deva.data.task.model.Task
import com.reach.deva.data.task.model.asModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface TaskLocalSource {

    fun getTasks(): Flow<Map<Task, List<Task>>>

    fun getTaskDetail(id: String): Flow<Map<Task, List<Task>>>

    fun upsertTask(task: Task)

    fun upsertSubTask(task: Task)

    fun deleteTask(id: String)

    fun deleteSubTask(id: String)
}

class DefaultTaskLocalSource(
    private val taskDao: TaskDao,
    private val subTaskDao: SubTaskDao,
    private val dispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope,
) : TaskLocalSource {
    override fun getTasks(): Flow<Map<Task, List<Task>>> =
        taskDao.getTasks().map { data ->
            data.mapKeys { it.key.asModel() }
        }.map { data ->
            data.mapValues { entry -> entry.value.map { it.asModel() } }
        }

    override fun getTaskDetail(id: String): Flow<Map<Task, List<Task>>> =
        taskDao.getTaskDetail(id).map { data ->
            data.mapKeys { it.key.asModel() }
        }.map { data ->
            data.mapValues { entry -> entry.value.map { it.asModel() } }
        }

    override fun upsertTask(task: Task) {
        scope.launch(dispatcher) {
            taskDao.upsertTask(task.asTaskEntity())
        }
    }

    override fun upsertSubTask(task: Task) {
        scope.launch(dispatcher) {
            subTaskDao.upsertSubTask(task.asSubTaskEntity())
        }
    }

    override fun deleteTask(id: String) {
        scope.launch(dispatcher) {
            taskDao.deleteTask(id)
        }
    }

    override fun deleteSubTask(id: String) {
        scope.launch(dispatcher) {
            subTaskDao.deleteSubTask(id)
        }
    }
}
