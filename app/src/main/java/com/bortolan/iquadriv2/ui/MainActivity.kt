package com.bortolan.iquadriv2.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.transition.Fade
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.bortolan.iquadriv2.PreferenceManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.ui.asl.LoginFragment
import com.bortolan.iquadriv2.ui.asl.StageFragment
import com.bortolan.iquadriv2.ui.calendario.CalendarioFragment
import com.bortolan.iquadriv2.ui.classi.ClassiFragment
import com.bortolan.iquadriv2.ui.notizie.NotizieFragment
import com.bortolan.iquadriv2.ui.voti.VotiLogin
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
            R.id.nav_voti -> {
                fragment = if (PreferenceManager.isConnectedToSpaggiari(this)) {
                    null
                } else {
                    VotiLogin()
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
        drawer.closeDrawer(Gravity.LEFT)
        return true
    }

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_bar)


        bottom_bar.setNavigationOnClickListener {
            drawer.openDrawer(Gravity.LEFT)
        }
        navigation.setNavigationItemSelectedListener(this)
        navigation.setCheckedItem(viewModel.selectedItem.value ?: R.id.nav_notizie)
        onNavigationItemSelected(navigation.checkedItem!!)

        updateNavigationBadges()
    }

    fun updateNavigationBadges() {

        val secondaryColor = TypedValue()
        theme.resolveAttribute(android.R.attr.textColorSecondary, secondaryColor, true)

        val countUnreadNews = DB.getInstance(this).quadri().countUnreadCircolari(PreferenceManager.getLastCheckedNews(this))
        setBadge(navigation.menu.findItem(R.id.nav_notizie), countUnreadNews)

        setLinkable(navigation.menu.findItem(R.id.nav_annuario), secondaryColor.data)
        setLinkable(navigation.menu.findItem(R.id.nav_certificazioni), secondaryColor.data)
        setLinkable(navigation.menu.findItem(R.id.nav_youtube), secondaryColor.data)
        setLinkable(navigation.menu.findItem(R.id.nav_progetti), secondaryColor.data)
    }

    private fun setBadge(item: MenuItem, s: Int) {
        val action = item.actionView as TextView

        action.text = if (s > 0) s.toString() else ""
        action.gravity = Gravity.CENTER_VERTICAL
        action.setTypeface(null, BOLD)
    }

    private fun setLinkable(item: MenuItem, color: Int){

        (item.actionView as ImageView).setImageResource(R.drawable.ic_insert_link_black_16dp)
        (item.actionView as ImageView).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
    }

    override fun setTitle(title: CharSequence?) {
        collapsing_toolbar.title = title
    }

    override fun setTitle(titleId: Int) {
        title = getString(titleId)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(Gravity.LEFT) -> drawer.closeDrawer(Gravity.LEFT)
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
