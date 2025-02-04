package ru.fefu.activitytracker.activities

import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.fragments.ActivityFlowFragment
import ru.fefu.activitytracker.fragments.ProfileFlowFragment

class BottomNavActivity : AppCompatActivity() {
    private lateinit var bottom_nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        if (savedInstanceState != null) {
            bottom_nav.selectedItemId = savedInstanceState.getInt("tabs", 1)
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, ActivityFlowFragment(), "activity")
                commit()
            }
        }
        bottom_nav = findViewById(R.id.bottom_nav_view)
        bottom_nav.setOnItemSelectedListener {
            if (it.itemId == R.id.navigation_activity && bottom_nav.selectedItemId == R.id.navigation_profile) {
                supportFragmentManager.beginTransaction().apply {
                    var fragment = supportFragmentManager.findFragmentByTag("activity")
                    if (fragment != null) this.show(fragment)
                    fragment = supportFragmentManager.findFragmentByTag("profile")
                    if (fragment != null) this.hide(fragment)
                    commit()
                }
            } else if (it.itemId == R.id.navigation_profile && bottom_nav.selectedItemId == R.id.navigation_activity) {
                supportFragmentManager.beginTransaction().apply {
                    var fragment = supportFragmentManager.findFragmentByTag("activity")
                    if (fragment != null)
                        this.hide(fragment!!)
                    fragment = supportFragmentManager.findFragmentByTag("profile")
                    if (fragment == null) add(
                        R.id.nav_host_fragment,
                        ProfileFlowFragment(),
                        "profile"
                    )
                    else this.show(fragment!!)
                    commit()
                }
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("tabs", bottom_nav.selectedItemId)
    }
}