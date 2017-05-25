package in.buka.app.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import in.buka.app.R;

/**
 * View Holder untuk Layout project_main_layout.xml
 */

public class ProjectDetailViewHolder extends RecyclerView.ViewHolder {
    public ImageView projectImage;
    public TextView
            projectName,
            creatorName,
            sortDesc,
            projectCategory,
            location,
            backers,
            funded,
            deadline,
            deadlineUnit;
    public ProgressBar percentageFunded;

    public ImageView avatar;
    public TextView avatarName, updateCount, commentCount, disclaimer;

    public ProjectDetailViewHolder(View itemView) {
        super(itemView);
        projectName = (TextView) itemView.findViewById(R.id.project_name);
        creatorName = (TextView) itemView.findViewById(R.id.creator_name);
        projectCategory = (TextView) itemView.findViewById(R.id.project_category);
        sortDesc = (TextView) itemView.findViewById(R.id.sort_description);
        location = (TextView) itemView.findViewById(R.id.location);

        projectImage = (ImageView) itemView.findViewById(R.id.project_image);

        backers = (TextView) itemView.findViewById(R.id.project_backers_count);
        funded = (TextView) itemView.findViewById(R.id.project_funded);
        deadline = (TextView) itemView.findViewById(R.id.project_deadline);
        percentageFunded = (ProgressBar) itemView.findViewById(R.id.percentage_funded);
        deadlineUnit = (TextView) itemView.findViewById(R.id.deadline_countdown_unit);

        avatar = (ImageView) itemView.findViewById(R.id.avatar);
        avatarName = (TextView) itemView.findViewById(R.id.avatar_name);
        updateCount = (TextView) itemView.findViewById(R.id.updates_count);
        commentCount = (TextView) itemView.findViewById(R.id.comments_count);
        disclaimer = (TextView) itemView.findViewById(R.id.project_disclaimer_text_view);
    }
}
