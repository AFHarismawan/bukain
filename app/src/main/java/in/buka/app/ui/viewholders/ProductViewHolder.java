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

    public ImageView productImage;
    public TextView productCategory, productName, productDesc, backers, funded, deadline, deadlineUnit;
    public ProgressBar percentageFunded;

    public ProductViewHolder(View itemView) {
        super(itemView);
    }
}
