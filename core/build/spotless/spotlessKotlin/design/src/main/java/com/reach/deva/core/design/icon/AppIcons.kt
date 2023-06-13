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

package com.reach.deva.core.design.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.outlined.GTranslate
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material.icons.rounded.GTranslate
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material.icons.rounded.RemoveDone
import androidx.compose.material.icons.rounded.Settings

object AppIcons {
    val Todo = Icons.Outlined.NearMe
    val TodoSelected = Icons.Rounded.NearMe

    val Add = Icons.Rounded.Create
    val Done = Icons.Rounded.Check

    val DoneAll = Icons.Rounded.DoneAll
    val RemoveDoneAll = Icons.Rounded.RemoveDone

    val Translate = Icons.Outlined.GTranslate
    val TranslateSelected = Icons.Rounded.GTranslate

    val Close = Icons.Rounded.Close

    val Back = Icons.Rounded.ArrowBackIosNew

    val Setting = Icons.Rounded.Settings
}
