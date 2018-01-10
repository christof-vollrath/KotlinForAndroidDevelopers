package net.taobits.kotlinforandroiddevelopers.ui
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import net.taobits.kotlinforandroiddevelopers.R
import org.jetbrains.anko.find
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SettingsActivity : AppCompatActivity() {

    companion object {
        val ZIP_CODE = "zipCode"
        val DEFAULT_ZIP = 9403L
    }
    val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    var zipCode: Long by LongPreference(this, ZIP_CODE, DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cityCode.setText(zipCode.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        zipCode = cityCode.text.toString().toLong()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> { onBackPressed(); true }
        else -> false
    }
}

class LongPreference(val context: Context, val name: String, val default: Long) : ReadWriteProperty<Any?, Long> {

    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return prefs.getLong(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        prefs.edit().putLong(name, value).apply()
    }

}