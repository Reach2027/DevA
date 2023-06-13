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

package com.reach.deva.data.task.di

import com.reach.deva.core.common.android.CoroutineScopeQualifier
import com.reach.deva.core.common.android.coroutineScopesModule
import com.reach.deva.core.common.jvm.CoroutineDispatcherQualifier
import com.reach.deva.core.common.jvm.coroutineDispatchersModule
import com.reach.deva.core.database.di.databaseModule
import com.reach.deva.data.task.local.DefaultTaskLocalSource
import com.reach.deva.data.task.local.TaskLocalSource
import com.reach.deva.data.task.repository.DefaultTaskRepository
import com.reach.deva.data.task.repository.TaskRepository
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val taskDataModule = module {

    includes(databaseModule, coroutineDispatchersModule, coroutineScopesModule)

    factory<TaskLocalSource> {
        DefaultTaskLocalSource(
            get(),
            get(),
            get(qualifier(CoroutineDispatcherQualifier.IO)),
            get(qualifier(CoroutineScopeQualifier.ApplicationScope)),
        )
    }

    factory<TaskRepository> {
        DefaultTaskRepository(get())
    }
}
