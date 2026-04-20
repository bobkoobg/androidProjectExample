package com.example.androidprojectexample.data.remote.version

import com.example.androidprojectexample.data.remote.version.model.AndroidVersion
import com.example.androidprojectexample.data.remote.version.model.IosVersion
import com.example.androidprojectexample.data.remote.version.model.MinimumVersionResponse
import org.json.JSONObject

class VersionJsonParser {

    fun parse(jsonString: String): MinimumVersionResponse {
        val root = JSONObject(jsonString)

        val androidJson = root.getJSONObject("android")
        val iosJson = root.getJSONObject("ios")

        val android = AndroidVersion(
            minimum_recommended_version_code =
                androidJson.getInt("minimum_recommended_version_code"),
            minimum_required_version_code =
                androidJson.getInt("minimum_required_version_code")
        )

        val ios = IosVersion(
            minimum_recommended_version_code =
                iosJson.getString("minimum_recommended_version_code"),
            minimum_required_version_code =
                iosJson.getString("minimum_required_version_code")
        )

        return MinimumVersionResponse(
            android = android,
            ios = ios
        )
    }

}