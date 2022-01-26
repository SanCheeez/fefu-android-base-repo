package ru.fefu.activitytracker

import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.fefu.activitytracker.fragments.ActivityFragment
import ru.fefu.activitytracker.fragments.ProfileFragment

class BottomNavActivity : AppCompatActivity() {
private lateinit var bottom_nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        if (savedInstanceState != null) {
            bottom_nav.selectedItemId = savedInstanceState.getInt("tabs", 1)
        } else {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.nav_host_fragment, ActivityFragment(), "activity")
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
                    addToBackStack("activity")
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
                        ProfileFragment(),
                        "profile"
                    )
                    else this.show(fragment!!)
                    addToBackStack("profile")
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