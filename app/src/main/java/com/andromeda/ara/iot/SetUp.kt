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

package com.andromeda.ara.iot

import android.app.Activity

class SetUp {
    fun setUp(key:String, Url:String, act:Activity){
        var url = Url
        if(url.endsWith("/")) url = url.removeSuffix("/")
        if (!url.endsWith("/api")) url = "$url/api"
        val sharedPreferences = act.getSharedPreferences("iot", 0)
        val edit = sharedPreferences.edit()
        edit.putString("url", url)
        edit.putString("key", key)
        edit.apply()
        CacheData().main(act)
        IotRequest.testPing()
    }
}