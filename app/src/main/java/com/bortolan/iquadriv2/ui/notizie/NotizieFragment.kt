package com.bortolan.iquadriv2.ui.notizie


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.PreferenceManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.api.SyncHelper
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.ui.notizie.activity.CircolariActivity
import com.bortolan.iquadriv2.ui.MainActivity
import com.bortolan.iquadriv2.ui.notizie.activity.QuadrinewsActivity
import com.bortolan.iquadriv2.ui.notizie.adapter.CircolariAdapter
import com.bortolan.iquadriv2.ui.notizie.adapter.NewsAdapter
import com.bortolan.iquadriv2.ui.notizie.adapter.QuadrinewsAdapter
import kotlinx.android.synthetic.main.fragment_notizie.*
import java.util.*

//TODO: "Mostra altro"
class NotizieFragment : Fragment() {

    private val newsAdapter = NewsAdapter()
    private val circolariAdapter = CircolariAdapter(this::onCircolariMoreClick)
    private val quadrinewsAdapter = QuadrinewsAdapter(this::onQuadrinewsMoreClick)

    private val viewModel by lazy {
        ViewModelProviders.of(activity!!)[NotizieViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notizie, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Notizie"
        setHasOptionsMenu(true)

        news_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        circolari_recycler.layoutManager = LinearLayoutManager(context)
        quadrinews_recycler.layoutManager = LinearLayoutManager(context)

        news_recycler.adapter = newsAdapter
        circolari_recycler.adapter = circolariAdapter
        quadrinews_recycler.adapter = quadrinewsAdapter

        circolariAdapter.provider = object : CircolariAdapter.Provider {
            override fun getFilter(): Set<String> {
                return setOf("Studenti", "ATA", "Docenti", "Genitori")
            }

            override fun getChecked(): Set<String> {
                return PreferenceManager.getCircolariFilter(context!!)
            }

            override fun onFilterChanged(filter: Set<String>) {
                PreferenceManager.setCircolariFilter(context!!, filter)
                notifyFilterChanged()
            }
        }

        syncNews()
        markAsRead()

        viewModel.getNotizie(context!!).observe(this, Observer { it ->
            newsAdapter.setData(it)
        })
        viewModel.getQuadrinews(context!!).observe(this, Observer { it ->
            quadrinewsAdapter.setData(it)
        })
        notifyFilterChanged()

        view.postOnAnimation {
            scrollView.scrollTo(0, savedInstanceState?.getInt("scrollY", 0) ?: 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("scrollY", scrollView.scrollY)
    }

    fun markAsRead() {
        Handler().postDelayed({
            if (context != null) {
                PreferenceManager.setLastCheckedNews(context!!, Date())
                (activity as MainActivity).updateNavigationBadges()
            }
        }, 5000)
    }

    fun syncNews() {
        SyncHelper.syncNotizie(context!!)
        SyncHelper.syncCircolari(context!!)
        SyncHelper.syncQuadrinews(context!!)
    }

    private fun onCircolariMoreClick(holder: RecyclerView.ViewHolder) {
        val options = ActivityOptionsCompat.makeClipRevealAnimation(circolari_recycler, 0, circolari_recycler.top - scrollView.scrollY, circolari_recycler.width, circolari_recycler.height)
        startActivity(Intent(activity!!, CircolariActivity::class.java), options.toBundle())
    }

    private fun onQuadrinewsMoreClick(holder: RecyclerView.ViewHolder) {
        startClipRevealActivity(Intent(activity!!, QuadrinewsActivity::class.java), scrollView, quadrinews_recycler)
    }

    private fun startClipRevealActivity(intent: Intent, parent: NestedScrollView, child: View) {
        val startY = Math.max(0, child.top - parent.scrollY)
        val bottom = Math.min(parent.height, child.bottom - parent.scrollY)
        val options = ActivityOptionsCompat.makeClipRevealAnimation(child, 0, startY, child.width, bottom - startY)
        startActivity(intent, options.toBundle())
    }

    private fun notifyFilterChanged() {
        circolariAdapter.setData(DB.getInstance(context!!).quadri().getCircolariSync(3, PreferenceManager.getCircolariFilter(context!!)))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.options_notizie, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.filter) {
        }
        return super.onOptionsItemSelected(item)
    }
}
