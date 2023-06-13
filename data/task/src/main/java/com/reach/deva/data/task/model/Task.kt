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

package com.reach.deva.data.task.model

import com.reach.deva.core.database.model.SubTaskEntity
import com.reach.deva.core.database.model.TaskEntity
import kotlinx.datetime.Instant

data class Task(
    val id: String,
    val parentId: String,

    val createTime: Instant,
    val updateTime: Instant,
    val finishTime: Instant?,

    val content: String,
    val description: String,
    val finished: Boolean,
) {

    fun asTaskEntity() = TaskEntity(
        id = id,
        createTime = createTime,
        updateTime = updateTime,
        finishTime = finishTime,
        content = content,
        description = description,
        finished = finished,
    )

    fun asSubTaskEntity() = SubTaskEntity(
        id = id,
        parentId = parentId,
        createTime = createTime,
        updateTime = updateTime,
        finishTime = finishTime,
        content = content,
        description = description,
        finished = finished,
    )
}

fun TaskEntity.asModel() = Task(
    id = id,
    parentId = "",
    createTime = createTime,
    updateTime = updateTime,
    finishTime = finishTime,
    content = content,
    description = description,
    finished = finished,
)

fun SubTaskEntity.asModel() = Task(
    id = id,
    parentId = parentId,
    createTime = createTime,
    updateTime = updateTime,
    finishTime = finishTime,
    content = content,
    description = description,
    finished = finished,
)
