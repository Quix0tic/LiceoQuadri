package com.bortolan.iquadriv2.ui.notizie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.ui.notizie.adapter.QuadrinewsAdapter
import kotlinx.android.synthetic.main.activity_circolari.*

class QuadrinewsActivity : AppCompatActivity() {
    val adapter = QuadrinewsAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadrinews)
        title = "Quadrinews"
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.isShowMore = false
        adapter.setData(DB.getInstance(this).quadri().getQuadrinewsSync(999))

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
