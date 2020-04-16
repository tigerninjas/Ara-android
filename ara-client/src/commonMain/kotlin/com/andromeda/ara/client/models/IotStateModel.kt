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

package com.andromeda.ara.client.models


class IotStateModel(val buttons: ArrayList<HaButton>){
    companion object{
        const val OFF = 0
        const val ON = 1
        const val PLAY = 2
        const val PAUSE = 3
        fun fromHaOutput(state:String){
            val arrayList = arrayListOf<Int>()
            when {
                state.equals("on") -> {
                    arrayList.add(OFF)
                }
                state.equals("off") -> {arrayList.add(ON)}
                state.equals("play") -> {arrayList.add(PAUSE)}
            }

        }
    }
    data class HaButton(val text: Any, val newState:Int)
}