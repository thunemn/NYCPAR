package com.example.nycpar.utils

import com.example.nycpar.api.TrailResponseItem

object TextUtils {

    fun getSurfaceTopogText(trail: TrailResponseItem): String {
        return if(trail.surface.isNullOrEmpty() && trail.topog.isNullOrEmpty()) {
            "Surface: Unknown"
        } else {
            "Surface: ${if (!trail.surface.isNullOrEmpty()) "${trail.surface}," else ""} ${if (!trail.topog.isNullOrEmpty()) "${trail.topog}" else ""}"
        }
    }

    fun getDifficultyText(trail: TrailResponseItem): String {
        return if(trail.difficulty.isNullOrEmpty()) {
            "Difficulty: Unknown"
        } else {
            "Difficulty: ${trail.difficulty}"
        }
    }

    fun getWidthText(trail: TrailResponseItem): String {
        return if(trail.widthFT.isNullOrEmpty()) {
            "Width: Unknown"
        } else {
            "Width: ${trail.widthFT}"
        }
    }

    fun getClassText(trail: TrailResponseItem): String {
        return if(trail.trailClass.isNullOrEmpty()) {
            "Class: Unknown"
        } else {
            "Class: ${trail.trailClass}"
        }
    }

    fun getTrailMarkersText(trail: TrailResponseItem): String {
        return if(trail.trailMarkersInstalled.isNullOrEmpty()) {
            "Trail Markers: Unknown"
        } else {
            "Trail Markers: ${trail.trailMarkersInstalled}"
        }
    }

    fun capitalizeFirstLetter(text: String): String {
        return text.substring(0, 1).uppercase() + text.substring(1).lowercase()
    }
}