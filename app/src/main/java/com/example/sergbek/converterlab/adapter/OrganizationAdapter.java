package com.example.sergbek.converterlab.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.model.Organization;
import com.example.sergbek.converterlab.utils.Utils;
import com.example.sergbek.converterlab.view.OrganizationView;

import java.util.ArrayList;
import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.MyViewHolder> implements Filterable {
    private List<Organization> mOrganizations;
    private List<Organization> mArrayList;

    public OrganizationAdapter(List<Organization> organizations) {
        mOrganizations = organizations;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recycler, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Organization organization = mOrganizations.get(position);
        holder.mOrganizationView.setOrganization(organization);
        holder.mOrganizationView.getTvTitle().setText(organization.getTitle());
        if (organization.getCityId().equalsIgnoreCase(organization.getRegionId())) {
            holder.mOrganizationView.getTvRegion().setVisibility(View.GONE);
            holder.mOrganizationView.getTvCity().setText(organization.getCityId());
        } else {
            holder.mOrganizationView.getTvCity().setText(organization.getCityId());
            holder.mOrganizationView.getTvRegion().setText(organization.getRegionId());
            holder.mOrganizationView.getTvRegion().setVisibility(View.VISIBLE);
        }

        if (organization.getPhone() != null)
            holder.mOrganizationView.getTvPhone().setText("Тел.: " + Utils.formatPhoneNumber(organization.getPhone()));
        else
            holder.mOrganizationView.getTvPhone().setText("");
        if (!organization.getAddress().isEmpty())
            holder.mOrganizationView.getTvAddress().setText("Адрес: " + organization.getAddress());
        else
            holder.mOrganizationView.getTvAddress().setText("");
    }


    @Override
    public int getItemCount() {
        return mOrganizations.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter();
    }


    public class Filter extends android.widget.Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults oReturn = new FilterResults();
            List<Organization> results = new ArrayList<>();
            if (mArrayList == null)
                mArrayList = mOrganizations;
            if (constraint != null) {
                if (mArrayList != null & mArrayList.size() > 0) {
                    for (final Organization organization : mArrayList) {
                        if (organization.getTitle().toLowerCase().contains(constraint.toString().toLowerCase()))
                            results.add(organization);
                        else if (organization.getCityId().toLowerCase().contains(constraint.toString().toLowerCase()))
                            results.add(organization);
                        else if (organization.getRegionId().toLowerCase().contains(constraint.toString().toLowerCase()))
                            results.add(organization);
                    }
                }
                oReturn.values = results;
            }
            return oReturn;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mOrganizations = (ArrayList<Organization>) results.values;
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private OrganizationView mOrganizationView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mOrganizationView = (OrganizationView) itemView.findViewById(R.id.view);
        }
    }
}

