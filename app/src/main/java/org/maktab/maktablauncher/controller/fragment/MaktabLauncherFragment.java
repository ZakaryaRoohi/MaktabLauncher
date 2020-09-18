package org.maktab.maktablauncher.controller.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab.maktablauncher.R;
import org.maktab.maktablauncher.utils.PackageUtils;

import java.util.List;

public class MaktabLauncherFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LauncherAdapter mAdapter;

    public MaktabLauncherFragment() {
        // Required empty public constructor
    }

    public static MaktabLauncherFragment newInstance() {
        MaktabLauncherFragment fragment = new MaktabLauncherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_maktab_launhcer, container, false);

        findViews(view);
        initViews();
        updateUI();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_maktab_launcher);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateUI() {
        List<ResolveInfo> activities = PackageUtils.getLauncherActivities(getContext());

        if (mAdapter == null) {
            mAdapter = new LauncherAdapter(activities);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class LauncherHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private ResolveInfo mResolveInfo;

        public LauncherHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.txtview_label);
            mTextViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComponentName componentName = PackageUtils.getComponentName(mResolveInfo);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(componentName);

                    startActivity(intent);
                }
            });
        }

        public void bindActivity(ResolveInfo launcherActivity) {
            mResolveInfo = launcherActivity;

            String activityLabel =
                    mResolveInfo.loadLabel(getActivity().getPackageManager()).toString();
            mTextViewTitle.setText(activityLabel);
        }
    }

    private class LauncherAdapter extends RecyclerView.Adapter<LauncherHolder> {

        private List<ResolveInfo> mActivities;

        public List<ResolveInfo> getActivities() {
            return mActivities;
        }

        public void setActivities(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        public LauncherAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        @NonNull
        @Override
        public LauncherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_row_item, parent, false);

            return new LauncherHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LauncherHolder holder, int position) {
            ResolveInfo launcherActivity = mActivities.get(position);
            holder.bindActivity(launcherActivity);
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }
}