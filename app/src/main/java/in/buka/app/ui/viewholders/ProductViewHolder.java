package in.buka.app.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import in.buka.app.R;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView productName, productDesc, price, stokHabis, sisaBarang, backer, delivery, deliveryTo;

    public ProductViewHolder(View itemView) {
        super(itemView);
        productName = (TextView) itemView.findViewById(R.id.product_title_text_view);
        productDesc = (TextView) itemView.findViewById(R.id.product_description_text_view);
        price = (TextView) itemView.findViewById(R.id.product_price);
        stokHabis = (TextView) itemView.findViewById(R.id.product_all_gone_text_view);
        sisaBarang = (TextView) itemView.findViewById(R.id.product_limit_and_remaining_text_view);
        backer = (TextView) itemView.findViewById(R.id.product_backers_text_view);
        delivery = (TextView) itemView.findViewById(R.id.product_estimated_delivery_date_text_view);
        deliveryTo = (TextView) itemView.findViewById(R.id.product_shipping_summary_text_view);
    }
}