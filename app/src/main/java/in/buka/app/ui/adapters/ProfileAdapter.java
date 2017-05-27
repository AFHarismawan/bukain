package in.buka.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import in.buka.app.ui.activities.DetailProjectActivity;
import in.buka.app.ui.viewholders.ProjectCardViewHolder;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProjectCardViewHolder> {

    public static String KEY_ID = "id";
    private Context context;
    private List<Project> list;

    public ProfileAdapter(Context context, List<Project> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ProjectCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_card_view, parent, false);

        return new ProjectCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProjectCardViewHolder holder, int position) {
        final Project project = list.get(position);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, DetailProjectActivity.class);
                Bundle send = new Bundle();
                send.putString(KEY_ID, project.id);
                detail.putExtras(send);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
