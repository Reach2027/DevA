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

package com.reach.deva.core.common.android

import com.reach.deva.core.common.jvm.CoroutineDispatcherQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

enum class CoroutineScopeQualifier {
    ApplicationScope,
}

val coroutineScopesModule = module {

    single<CoroutineScope>(qualifier(CoroutineScopeQualifier.ApplicationScope)) {
        val dispatcher: CoroutineDispatcher = get(qualifier(CoroutineDispatcherQualifier.Default))
        CoroutineScope(SupervisorJob() + dispatcher)
    }
}