package drax.liuan.com.drax.vip;

import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import drax.liuan.com.drax.R;

public class VipDisplayAnimHelper {
    private LinearLayout llVipDisplayWrapper;
    private LinearLayout llVipDisplayContent;
    private TextView tvVipDisplayHint;
    private

    VipDisplayAnimHelper(@NonNull LinearLayout linearLayoutWrapper) {
        this.llVipDisplayWrapper = linearLayoutWrapper;
        this.llVipDisplayContent = this.llVipDisplayWrapper.findViewById(R.id.llVipDisplayContent);
        this.tvVipDisplayHint = this.llVipDisplayWrapper.findViewById(R.id.tvVipDisplayHint);


    }


    private void initAnimation() {

    }


}
