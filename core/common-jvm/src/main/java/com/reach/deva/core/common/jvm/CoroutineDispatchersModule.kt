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

package com.reach.deva.core.common.jvm

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

enum class CoroutineDispatcherQualifier {
    IO,
    Default,
}

val coroutineDispatchersModule = module {

    single<CoroutineDispatcher>(qualifier(CoroutineDispatcherQualifier.IO)) { Dispatchers.IO }

    single<CoroutineDispatcher>(qualifier(CoroutineDispatcherQualifier.Default)) { Dispatchers.Default }
}
