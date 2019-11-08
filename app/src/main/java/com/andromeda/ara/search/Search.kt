/*
 * Copyright (c) 2019. Fulton Browne
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

package com.andromeda.ara.search

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.andromeda.ara.R
import com.andromeda.ara.skills.Parse
import com.andromeda.ara.skills.RunActions
import com.andromeda.ara.util.ApiOutputToRssFeed
import com.andromeda.ara.util.RssFeedModel
import com.andromeda.ara.voice.TTS
import java.util.*

class Search {
    fun main(mainval: String, log:String,lat:String, ctx:Context, act:Activity): ArrayList<RssFeedModel> {

        var outputList: ArrayList<RssFeedModel> = java.util.ArrayList()
        var local = SkillsSearch().search(mainval, ctx)
        var lat:Double = 0.0
        var log: Double = 0.0
        val locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                lat = location.latitude
                log = location.longitude
            }


        }
        if (local[0] != "" && mainval != ""){
            val parsed = Parse().parse(local[0])
            RunActions().doIt(parsed, mainval.replace(local[1]+ " ", ""), ctx, act)

        }
        else{

        outputList.add(RssFeedModel("", "", "", "",""))
        //search ara server
        var searchMode1 = mainval.toLowerCase(Locale("en"))
        searchMode1 = searchMode1.replace(" ", "%20")
        val test1 = AraSearch().arrayOfOutputModels(searchMode1, log.toString(), lat.toString())
        outputList = ApiOutputToRssFeed().main(test1)
        println(outputList[0].out)
        if(test1?.get(0)?.exes  != ""){
            val parsed = Parse().parse(test1?.get(0)?.exes)
            RunActions().doIt(parsed, mainval, ctx, act)
        }



            println(R.string.done_search)
        }


        return outputList
    }
}
