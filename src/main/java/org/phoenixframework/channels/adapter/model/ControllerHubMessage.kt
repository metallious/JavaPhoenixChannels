package de.suitepad.suitetv.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ahmed on 12/15/17.
 */
data class ControllerHubMessage (@SerializedName("action") val action : Action,
                                 @SerializedName("value") val value : Value) {

    enum class Action(val actionCode : String?) {
        BUTTON_CLICK("click"),
        BUTTON_PRESS("press"),
        BUTTON_RELEASE("release"),
        VOLUME_CHANGE("set_volume"),
        CHANNEL_CHANGE("set_channel"),
        UNKNOWN(null)
    }

    enum class Value(var valueCode : String?) {
        INT("0"),
        VOLUME_UP("v_+"),
        VOLUME_DOWN("v_-"),
        MUTE_TOGGLE("v"),
        POWER_ON("p_+"),
        POWER_OFF("p_-"),
        POWER_TOGGLE("p"),
        UNKNOWN(null);

        fun getIntegerValue() : Int? {
            if (this == INT) {
                return valueCode?.toIntOrNull()
            }
            return null
        }
    }

    fun isValid() : Boolean {
        if (action == Action.UNKNOWN || value == Value.UNKNOWN)
            return false
        if (action == Action.VOLUME_CHANGE && value != Value.INT) {
            return false
        }
        return true
    }

}