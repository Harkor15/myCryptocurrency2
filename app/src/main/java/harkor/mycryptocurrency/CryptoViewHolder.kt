package harkor.mycryptocurrency

import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

import android.view.animation.Animation.RELATIVE_TO_SELF

class CryptoViewHolder(itemView: View) : GroupViewHolder(itemView) {
    private val tagTw: TextView
    private val amountTw: TextView
    private val arrow: ImageView

    init {
        tagTw = itemView.findViewById(R.id.recycler_tag)
        amountTw = itemView.findViewById(R.id.recycler_amount)
        arrow = itemView.findViewById(R.id.recycler_arrow)
    }

    fun bind(cryptocurrency: Cryptocurrency) {
        tagTw.text = cryptocurrency.title
        //TODO: set amount
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(360f, 180f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180f, 360f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }
}
