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

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reach.deva.core.database.dao.SubTaskDao
import com.reach.deva.core.database.dao.TaskDao
import com.reach.deva.core.database.model.SubTaskEntity
import com.reach.deva.core.database.model.TaskEntity
import com.reach.deva.core.database.util.InstantConverter

@Database(
    entities = [
        TaskEntity::class,
        SubTaskEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(InstantConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun subTaskDao(): SubTaskDao
}
