package it.icemangp.shakenetworklog.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import it.icemangp.shakenetworklog.R
import it.icemangp.shakenetworklog.data.NetworkCall
import it.icemangp.shakenetworklog.data.NetworkLogManager
import it.icemangp.shakenetworklog.ui.adapter.NetworkCallListAdapter
import it.icemangp.shakenetworklog.ui.utils.UiUtils

class ActivityNetworkCallList : AppCompatActivity(), NetworkCallListAdapter.ItemClickListener {

    lateinit var adapter: NetworkCallListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_network_call_list)

        val toolbar = findViewById<MaterialToolbar>(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.snl_lib_name)
        }

        UiUtils.setupStatusBarAppearance(
            rootView = findViewById(R.id.recyclerView),
            window = this.window,
            statusBarColorView = findViewById(R.id.statusBarBackground),
            statusBarColorRes = R.color.colorPrimary
        )

        initUI()
    }

    private fun initUI() {
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                NetworkLogManager.data.clear()
                adapter.updateData(NetworkLogManager.data.toList())
                Snackbar.make(findViewById(R.id.main), "Clear done!", Snackbar.LENGTH_SHORT).show()
                true
            }

            R.id.refresh -> {
                adapter.updateData(NetworkLogManager.data.toList())
                Snackbar.make(findViewById(R.id.main), "Refresh done!", Snackbar.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter = NetworkCallListAdapter(NetworkLogManager.data.toList(), this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(item: NetworkCall) {
        val intent = Intent(this, ActivityNetworkCallDetail::class.java)
        intent.putExtra(ActivityNetworkCallDetail.NETWORK_CALL_ID, item.id)
        startActivity(intent)
    }
}