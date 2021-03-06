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

package com.andromeda.ara.skills

import com.andromeda.ara.client.models.SkillsModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.IOException


class Parse {
    fun parse(yml: String?): ArrayList<SkillsModel>? {
        print(yml)
        val classsss: Class<SkillsModel>? = SkillsModel::class.java
        return yamlArrayToObjectList(yml, classsss!!)
    }


    @Throws(IOException::class)
    fun <T> yamlArrayToObjectList(yaml: String?, tClass: Class<T>): ArrayList<T> {
        //val mapper = ObjectMapper()
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        val listType: CollectionType = mapper.typeFactory.constructCollectionType(ArrayList::class.java, tClass)

        return mapper.readValue(yaml, listType)!!
    }
    fun <T> yamlArrayToObject(yaml: String?, tClass: Class<T>): T {
        //val mapper = ObjectMapper()
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule() // jackson databind
        val listType: CollectionType = mapper.typeFactory.constructCollectionType(ArrayList::class.java, tClass)
        return mapper.readValue(yaml, tClass)
    }
}