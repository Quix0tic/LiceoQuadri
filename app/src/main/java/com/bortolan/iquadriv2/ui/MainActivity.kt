package com.bortolan.iquadriv2.ui

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.bortolan.iquadriv2.PreferenceManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.ui.asl.LoginFragment
import com.bortolan.iquadriv2.ui.asl.StageFragment
import com.bortolan.iquadriv2.ui.calendario.CalendarioFragment
import com.bortolan.iquadriv2.ui.classi.ClassiFragment
import com.bortolan.iquadriv2.ui.notizie.NotizieFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val viewModel by lazy {
        ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_asl -> {
                fragment = if (PreferenceManager.isConnectedToMasterstage(this)) {
                    StageFragment()
                } else {
                    LoginFragment()
                }
            }
            R.id.nav_notizie -> {
                fragment = NotizieFragment()
            }
            R.id.nav_calendario -> fragment = CalendarioFragment()
            R.id.nav_classi -> fragment = ClassiFragment()
        }

        viewModel.selectedItem.value = item.itemId
        fragment?.let { showFragment(supportFragmentManager, it) }
        drawer.closeDrawer(Gravity.START)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        actionBarDrawerToggle.syncState()
        navigation.setNavigationItemSelectedListener(this)

        navigation.setCheckedItem(viewModel.selectedItem.value ?: R.id.nav_notizie)
        onNavigationItemSelected(navigation.checkedItem!!)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(Gravity.START) -> drawer.closeDrawer(Gravity.START)
            supportFragmentManager.backStackEntryCount > 1 -> supportFragmentManager.popBackStack()
            else -> super.onBackPressed()
        }
    }


    companion object {
        fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            fragmentManager
                    .beginTransaction()
                    //.setCustomAnimations()
                    .addToBackStack(null)
                    .replace(R.id.container, fragment)
                    .commit()
        }
    }
}
