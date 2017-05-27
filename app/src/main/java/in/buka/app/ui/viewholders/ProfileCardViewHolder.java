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

public class ProfileCardViewHolder extends RecyclerView.ViewHolder {

    public ImageView profileImage;
    public TextView profileName;
    public ProgressBar percentageFunded;

    public ProfileCardViewHolder(View itemView) {
        super(itemView);
        profileImage = (ImageView) itemView.findViewById(R.id.profile_card_image);
        profileName = (TextView) itemView.findViewById(R.id.profile_card_name);
        percentageFunded = (ProgressBar) itemView.findViewById(R.id.percentage_funded);
    }
}