package in.buka.app.ui.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.buka.app.R;
import in.buka.app.ui.activities.DetailProjectActivity;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static String KEY_ID = "id";
    
    public String id;
    public Context context;
    public ImageView projectImage;
    public TextView projectCategory, projectName, projectDesc, backers, funded, deadline, deadlineUnit;
    public ProgressBar percentageFunded;

    public ProjectViewHolder(View itemView) {
        super(itemView);
        projectImage = (ImageView) itemView.findViewById(R.id.project_image);
        projectCategory = (TextView) itemView.findViewById(R.id.project_category);
        projectName = (TextView) itemView.findViewById(R.id.project_name);
        projectDesc = (TextView) itemView.findViewById(R.id.project_desc);
        backers = (TextView) itemView.findViewById(R.id.project_backers_count);
        funded = (TextView) itemView.findViewById(R.id.project_funded);
        deadline = (TextView) itemView.findViewById(R.id.project_deadline);
        percentageFunded = (ProgressBar) itemView.findViewById(R.id.percentage_funded);
        deadlineUnit = (TextView) itemView.findViewById(R.id.deadline_countdown_unit);
    }

    @Override
    public void onClick(View v) {
        Intent project = new Intent(context, DetailProjectActivity.class);
        Bundle send = new Bundle();
        send.putString(KEY_ID, id);
        project.putExtras(send);
        context.startActivity(project);
    }
}
