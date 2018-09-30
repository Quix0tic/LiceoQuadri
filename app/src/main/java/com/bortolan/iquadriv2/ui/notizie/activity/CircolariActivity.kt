package com.bortolan.iquadriv2.ui.notizie.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bortolan.iquadriv2.PreferenceManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.ui.notizie.adapter.CircolariAdapter
import kotlinx.android.synthetic.main.activity_circolari.*

class CircolariActivity : AppCompatActivity() {

    private val adapter = CircolariAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circolari)

        title = "Circolari"
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.setData(DB.getInstance(this).quadri().getCircolariSync(999, PreferenceManager.getCircolariFilter(this)))
        adapter.provider = object : CircolariAdapter.Provider {
            override fun getFilter(): Set<String> {
                return setOf("Studenti", "ATA", "Docenti", "Genitori")
            }

            override fun getChecked(): Set<String> {
                return PreferenceManager.getCircolariFilter(this@CircolariActivity)
            }

            override fun onFilterChanged(filter: Set<String>) {
                PreferenceManager.setCircolariFilter(this@CircolariActivity, filter)
                adapter.setData(DB.getInstance(this@CircolariActivity).quadri().getCircolariSync(999, filter))
            }
        }
        adapter.isShowMore = false

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.fade_out_with_slide)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setTitle(title: CharSequence?) {
        collapsing_toolbar.title = title
    }

}
