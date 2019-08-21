package harkor.mycryptocurrency;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class CryptoViewHolder extends GroupViewHolder {
    private TextView tagTw;
    private TextView amountTw;
    private ImageView arrow;

    public CryptoViewHolder(View itemView) {
        super(itemView);
        tagTw=itemView.findViewById(R.id.recycler_tag);
        amountTw=itemView.findViewById(R.id.recycler_amount);
        arrow=itemView.findViewById(R.id.recycler_arrow);
    }
    public void bind(Cryptocurrency cryptocurrency){
        tagTw.setText(cryptocurrency.getTitle());
        //TODO: set amount
    }
    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
