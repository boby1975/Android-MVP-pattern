package boby.mvp_pattern.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar

class ViewUtils {
    fun showSnackBarAttention(text: String, snackView: View, mContext: Context) {
        try {
            //Snackbar.make(snackbarView, text, Snackbar.LENGTH_LONG).show();
            val snackBar = Snackbar.make(snackView, text, Snackbar.LENGTH_LONG)
            val sbView = snackBar.view
            sbView.setBackgroundColor(mContext.resources.getColor(R.color.notification_action_color_filter))

            val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
            textView.textSize = 15.0f
            snackBar.setAction(" ") {
                    snackBar.dismiss()
            }
            val textViewAction = sbView.findViewById<View>(R.id.snackbar_action) as TextView
            textViewAction.text = "" // clear the text to keep only the icon
            textViewAction.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_mtrl_chip_close_circle,
                0,
                0,
                0
            )
            //snackBar.view.setPadding(0, 0, 0, 0)
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}