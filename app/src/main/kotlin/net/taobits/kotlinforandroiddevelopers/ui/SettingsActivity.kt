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
        val DEFAULT_ZIP = 94043L
    }
    val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    var zipCode: Long by Preference<Long>(this, ZIP_CODE, DEFAULT_ZIP)

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

class Preference<T: Any>(val context: Context, val name: String, val default: T) : ReadWriteProperty<Any?, T>  {

    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    private fun findPreference(name: String, default: T): T = with(prefs) {
        when(default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("Type ${default::class.simpleName} cant be saved into Preferences")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(prefs.edit()){
        when(value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("Type ${value::class.simpleName} cant be saved into Preferences")
        }.apply()
    }
}