/*
 * Copyright (c) 2020. Fulton Browne
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.andromeda.ara.client.iot

import com.andromeda.ara.client.models.FeedModel
import com.andromeda.ara.client.models.IotState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.content

class Actions {
    suspend fun getAll(): ArrayList<FeedModel> {
        return get("")

    }
    suspend fun get(id:String): ArrayList<FeedModel> {
        val link =
        if (id != "" && !id.startsWith("/")) "/$id"
        else id
        val toReturn = arrayListOf<FeedModel>()
        val request = IotRequest.getRequest("/states")
        Json.parseJson(request).jsonArray.forEach {
            try {
                val jsonObject = it.jsonObject
                val attributes = jsonObject.get("attributes")!!.jsonObject
                val name = attributes.get("friendly_name")!!.content
                val id = jsonObject.get("entity_id")!!.content
                val description = jsonObject.get("state")!!.content
                toReturn.add(FeedModel(description, id, name, "", "", false))
            }
            catch (e:Exception){
            }

        }
        return toReturn

    }
    @OptIn(UnstableDefault::class)
    fun edit(id: String, getNewInputs: GetNewInputs){
        val attributesMap = mutableMapOf<String, String>()
        GlobalScope.launch {
            val request = IotRequest.getRequest("/states/$id")
            val jsonObject = Json.parseJson(request).jsonObject
            try {
                val attributes = jsonObject.get("attributes")!!.jsonObject
                println(attributes)
                attributes.content.entries.forEach {
                    attributesMap[it.key] = it.value.content
                }
                val description = jsonObject.get("state")!!.content
                val stateAll = IotState(
                    state = description,
                    context = null,
                    attributes = attributesMap
                )
                val fromHaOutput = IotStateInfo.fromHaOutput(
                    stateAll
                )
                val text: String
                text = if (fromHaOutput.size == 1) when (fromHaOutput[0]) {
                    -1 -> {
                        getNewInputs.text()
                    }
                    0 ->{
                        getNewInputs.toggle(true)
                        "on"
                    }
                    1 ->{
                        getNewInputs.toggle(false)
                        "off"
                    }
                    else ->  getNewInputs.text()
                }
                else getNewInputs.text()
                IotStateInfo.onPressed(id, text, stateAll)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}