package br.com.stone.posandroid.posdeeplink

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.stone.posandroid.posdeeplink.activation.ActivationFragment
import br.com.stone.posandroid.posdeeplink.cancel.CancelFragment
import br.com.stone.posandroid.posdeeplink.payment.PaymentFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        selectFragment(R.id.nav_cancel)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        selectFragment(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun selectFragment(@IdRes id: Int) {
        nav_view.setCheckedItem(id)
        when (id) {
            R.id.nav_cancel -> {
                setFragment(CancelFragment())
            }
            R.id.nav_payment -> {
                setFragment(PaymentFragment())
            }
            R.id.nav_activation -> {
                setFragment(ActivationFragment())
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}
