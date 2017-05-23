package in.buka.app.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.buka.app.R;
import in.buka.app.libs.utils.ProjectUtils;
import in.buka.app.libs.utils.ViewUtils;
import in.buka.app.models.Project;
import in.buka.app.ui.viewholders.ProjectViewHolder;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class ActivityFeedAdapter extends RecyclerView.Adapter<ProjectViewHolder> {

    private Context context;
    private List<Project> list;

    public ActivityFeedAdapter(Context context, List<Project> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_card_view, parent, false);

        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        Project project = list.get(position);

        if (project.id != null) {
            holder.id = project.id;
        }

        if (project.image != null) {
            final int targetImageWidth = (int) (ViewUtils.getScreenWidthDp(context) * ViewUtils.getScreenDensity(context));
            final int targetImageHeight = ProjectUtils.photoHeightFromWidthRatio(targetImageWidth);
            holder.projectImage.setMaxHeight(targetImageHeight);

            Picasso.with(context)
                    .load(project.image)
                    .resize(targetImageWidth, targetImageHeight)
                    .centerCrop()
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.gray_gradient))
                    .into(holder.projectImage);
        }

        if (project.name != null) {
            holder.projectName.setText(project.name);
        }

        if (project.desc != null) {
            holder.projectDesc.setText(project.desc);
        }

        if (project.category != null) {
            holder.projectCategory.setText(project.category);
        }

        holder.percentageFunded.setProgress(project.fundedPercent());

        holder.backers.setText(project.backers());
        holder.funded.setText(project.funded());
        holder.deadline.setText(ProjectUtils.deadlineCountdownValue(project.deadline));
        holder.deadlineUnit.setText(ProjectUtils.deadlineCountdownDetail(project.deadline, context));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
