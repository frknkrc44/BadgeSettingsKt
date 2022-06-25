package org.blinksd.settings

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.widget.RadioGroup
import android.widget.Toast
import org.blinksd.settings.databinding.ActivityMainBinding
import java.util.*

class BadgeSettingsActivity : AppCompatActivity(),
    CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.badgeEnabled.isChecked = SystemUtils.isNotificationBadgingEnabled(contentResolver)
        binding.badgeEnabled.setOnCheckedChangeListener(this)

        binding.notificationAccessAlertText.text =
            String.format(binding.notificationAccessAlertText.text.toString(),
                SystemUtils.getLauncherName(this))

        val background = GradientDrawable()
        background.cornerRadius = resources.displayMetrics.density * 32
        background.color = ColorStateList.valueOf(SystemUtils.getAccentColor(this, true))

        binding.notificationAccessNotGranted.background = background

        binding.notificationAccessNotGranted.visibility =
            if (SystemUtils.isNotificationAccessEnabledForLauncher(contentResolver))
                View.GONE
            else
                View.VISIBLE

        binding.grantNotificationAccess.setOnClickListener(this)

        val optionWithNumber = CustomRadioButton.createOption(
            this, R.string.style_with_numbers, SystemUtils.BADGE_APP_ICON_WITH_NUMBER)
        val optionWithoutNumber = CustomRadioButton.createOption(
            this, R.string.style_without_numbers, SystemUtils.BADGE_APP_ICON_WITHOUT_NUMBER)
        binding.badgeTypeSelector.addView(optionWithNumber)
        binding.badgeTypeSelector.addView(optionWithoutNumber)
        binding.badgeTypeSelector.check(SystemUtils.getAppBadgeSetting(contentResolver))
        binding.badgeTypeSelector.setOnCheckedChangeListener { _: RadioGroup, selection: Int ->
            run{
                try {
                    SystemUtils.setAppBadgeSetting(contentResolver, selection)
                    Toast.makeText(this@BadgeSettingsActivity, R.string.snackbar_success, Toast.LENGTH_SHORT).show()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    Toast.makeText(this@BadgeSettingsActivity, R.string.snackbar_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.badgeTypeSelector.isEnabled = binding.badgeEnabled.isChecked
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        binding.badgeTypeSelector.isEnabled = isChecked

        try {
            SystemUtils.setNotificationBadgingEnabled(contentResolver, isChecked)
            Toast.makeText(this, R.string.snackbar_success, Toast.LENGTH_SHORT).show()
        } catch (e: Throwable) {
            e.printStackTrace()
            Toast.makeText(this, R.string.snackbar_fail, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
    }
}