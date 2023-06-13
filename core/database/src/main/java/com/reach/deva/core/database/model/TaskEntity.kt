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

package com.reach.deva.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "create_time")
    val createTime: Instant,
    @ColumnInfo(name = "update_time")
    val updateTime: Instant,
    @ColumnInfo(name = "finish_time")
    val finishTime: Instant?,

    val content: String,
    val description: String,
    val finished: Boolean,
)