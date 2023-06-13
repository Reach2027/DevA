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

package com.reach.deva.feature.translate

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow

class MlTranslator {

    private val _availableModels = MutableStateFlow<List<String>>(emptyList())
    val availableModels = _availableModels.asStateFlow()

    private val _targetText = MutableSharedFlow<String>()
    val targetText = _targetText.asSharedFlow()

    private val _translatorMessage = MutableSharedFlow<String>()
    val translatorMessage = _translatorMessage.asSharedFlow()

    private val translator: Translator

    private val modelManager = RemoteModelManager.getInstance()

    init {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.CHINESE)
            .build()
        translator = Translation.getClient(options)

        getDownloadedModels()
    }

    suspend fun translate(content: String) {
        callbackFlow {
            translator.translate(content).addOnCompleteListener { trySend(it) }
            awaitClose { }
        }.collect { task ->
            if (task.isSuccessful) {
                _targetText.emit(task.result)
            } else {
                task.exception?.let { e ->
                    e.message?.let { _translatorMessage.emit("translation failure, $it") }
                }
            }
        }
    }

    fun close() {
        translator.close()
    }

    suspend fun downloadLanguage(langStr: String) {
        val model = TranslateRemoteModel.Builder(langStr).build()
        val conditions = DownloadConditions.Builder().build()
        callbackFlow {
            modelManager.download(model, conditions).addOnCompleteListener { trySend(it) }
            awaitClose { }
        }.collect { task ->
            if (task.isSuccessful) {
                getDownloadedModels()
                _translatorMessage.emit("Download $langStr successful")
            } else {
                task.exception?.let { e ->
                    e.message?.let { _translatorMessage.emit("Download $langStr failed, $it") }
                }
            }
        }
    }

    private fun getDownloadedModels() {
        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                _availableModels.value = models.sortedBy { it.language }.map { it.language }
            }
    }
}
