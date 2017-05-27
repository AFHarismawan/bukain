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
import in.buka.app.R;
import in.buka.app.libs.utils.ProjectUtils;
import in.buka.app.libs.utils.ViewUtils;
import in.buka.app.models.Product;
import in.buka.app.models.Project;
import in.buka.app.models.User;
import in.buka.app.ui.activities.DetailCampaignProjectActivity;
import in.buka.app.ui.viewholders.ProductViewHolder;
import in.buka.app.ui.viewholders.ProjectDetailViewHolder;

import java.util.List;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class DetailProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DETAIL  = R.layout.project_main_layout;
    private static final int VIEW_TYPE_PRODUCT = R.layout.product_view;

    public static String KEY_ID = "id";
    private Context context;
    private Project project;
    private User creator;
    private List<Product> products;

    public DetailProjectAdapter(Context context, Project project, User creator, List<Product> products) {
        this.context = context;
        this.project = project;
        this.creator = creator;
        this.products = products;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_DETAIL : VIEW_TYPE_PRODUCT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        switch (viewType) {
            case VIEW_TYPE_DETAIL: return new ProjectDetailViewHolder(itemView);
            case VIEW_TYPE_PRODUCT: return new ProductViewHolder(itemView);
        }
        return new ProjectDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_DETAIL:
                ProjectDetailViewHolder detailHolder = (ProjectDetailViewHolder) holder;
                if (project.image != null) {
                    final int targetImageWidth = (int) (ViewUtils.getScreenWidthDp(context) * ViewUtils.getScreenDensity(context));
                    final int targetImageHeight = ProjectUtils.photoHeightFromWidthRatio(targetImageWidth);
                    detailHolder.projectImage.setMaxHeight(targetImageHeight);

                    Picasso.with(context)
                            .load(project.image)
                            .resize(targetImageWidth, targetImageHeight)
                            .centerCrop()
                            .placeholder(ContextCompat.getDrawable(context, R.drawable.gray_gradient))
                            .into(detailHolder.projectImage);
                }

                if (project.name != null) {
                    detailHolder.projectName.setText(project.name);
                }

                if (project.desc != null) {
                    detailHolder.sortDesc.setText(project.desc);
                }

                if (project.category != null) {
                    detailHolder.projectCategory.setText(project.category);
                }

                detailHolder.percentageFunded.setProgress(project.fundedPercent());

                detailHolder.backers.setText(project.backers());
                detailHolder.funded.setText(project.funded());
                detailHolder.deadline.setText(ProjectUtils.deadlineCountdownValue(project.deadline));
                detailHolder.deadlineUnit.setText(ProjectUtils.deadlineCountdownDetail(project.deadline, context));

                detailHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case VIEW_TYPE_PRODUCT:
                ProductViewHolder productHolder = (ProductViewHolder) holder;

                break;
        }
    }

    @Override
    public int getItemCount() {
        return products.size() + 1;
    }
}
