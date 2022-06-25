package org.blinksd.settings

import android.R
import android.content.ContentResolver
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.provider.Settings

class SystemUtils {
    companion object {
        /*
        - To enable badging
        su -c settings put secure notification_badging 1

        - To disable badging
        su -c settings put secure notification_badging 0

        - To change App icon badges show without number:
        su -c settings put secure badge_app_icon_type 1

        - To change App icon badges show with number:
        su -c settings put secure badge_app_icon_type 0
         */
        private const val BADGE_APP_ICON_TYPE = "badge_app_icon_type"
        private const val NOTIFICATION_BADGING = "notification_badging"
        private const val LAUNCHER_PKG_NAME = "com.sec.android.app.launcher"
        const val BADGE_APP_ICON_WITH_NUMBER = 0
        const val BADGE_APP_ICON_WITHOUT_NUMBER = 1
        private const val NOTIFICATION_BADGING_DISABLED = 0
        private const val NOTIFICATION_BADGING_ENABLED = 1

        fun setAppBadgeSetting(cr: ContentResolver, newValue: Int) {
            if(newValue > BADGE_APP_ICON_WITHOUT_NUMBER || newValue < BADGE_APP_ICON_WITH_NUMBER) return
            Settings.Secure.putInt(cr, BADGE_APP_ICON_TYPE, newValue)
        }

        fun getAppBadgeSetting(cr: ContentResolver) :Int {
            val ret = Settings.Secure.getInt(cr, BADGE_APP_ICON_TYPE, BADGE_APP_ICON_WITH_NUMBER)
            return if(ret > BADGE_APP_ICON_WITHOUT_NUMBER || ret < BADGE_APP_ICON_WITH_NUMBER)
                BADGE_APP_ICON_WITH_NUMBER
            else
                ret
        }

        fun setNotificationBadgingEnabled(cr: ContentResolver, newValue: Boolean) {
            Settings.Secure.putInt(cr, NOTIFICATION_BADGING, if (newValue) NOTIFICATION_BADGING_ENABLED  else NOTIFICATION_BADGING_DISABLED)
        }

        fun isNotificationBadgingEnabled(cr: ContentResolver): Boolean {
            return Settings.Secure.getInt(cr, NOTIFICATION_BADGING, NOTIFICATION_BADGING_DISABLED) >= NOTIFICATION_BADGING_ENABLED
        }

        fun isNotificationAccessEnabledForLauncher(cr: ContentResolver): Boolean {
            return Settings.Secure.getString(cr,"enabled_notification_listeners").contains(LAUNCHER_PKG_NAME)
        }

        fun getLauncherName(context: Context): String {
            return try {
                context.packageManager.getApplicationInfo(LAUNCHER_PKG_NAME, 0).name
            } catch(e: Throwable) {
                "One UI Home"
            }
        }

        fun getAccentColor(context: Context, trans: Boolean): Int {
            return (if(Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                context.getColor(android.R.color.system_accent1_500)
            } else {
                val arr: TypedArray = context.obtainStyledAttributes(
                    0, intArrayOf(
                        R.attr.colorAccent
                    )
                )
                arr.getColor(0, context.getColor(R.color.holo_blue_light)).also { arr.recycle() }
            }) - if(trans) 0x44000000 else 0
        }
    }
}