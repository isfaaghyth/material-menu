/**
 * Copyright (c) 2018, isfaaghyth.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package isfaaghyth.app.spinner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import isfaaghyth.app.spinner.R;
import isfaaghyth.app.spinner.util.ItemContent;
import isfaaghyth.app.spinner.util.MenuItemListener;

/**
 * Created by isfaaghyth on 22/12/18.
 * github: @isfaaghyth
 */
public class DropdownAdapter<T extends ItemContent> extends ArrayAdapter<T> {

    private List<T> items;
    private MenuItemListener<T> listener;

    public DropdownAdapter(List<T> items, Context context) {
        super(context, R.layout.item_menu, items);
        this.items = items;
    }

    public void setListener(MenuItemListener<T> listener) {
        this.listener = listener;
    }

    @NonNull @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final T data = items.get(position);

        View itemView = convertView;
        ViewHolder viewHolder;

        if (itemView == null) {
           viewHolder = new ViewHolder();
           LayoutInflater inflater = LayoutInflater.from(parent.getContext());
           itemView = inflater.inflate(R.layout.item_menu, parent, false);
           viewHolder.rootItem = itemView.findViewById(R.id.root_item);
           viewHolder.txtItem = itemView.findViewById(R.id.txt_item);
           viewHolder.txtSubItem = itemView.findViewById(R.id.txt_sub_item);
           itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }

        //set value
        if (!data.menuItem().isEmpty()) {
            viewHolder.txtItem.setText(data.menuItem());
        } else {
            viewHolder.txtItem.setVisibility(View.GONE);
        }

        if (!data.menuSubItem().isEmpty()) {
            viewHolder.txtSubItem.setText(data.menuSubItem());
        } else {
            viewHolder.txtSubItem.setVisibility(View.GONE);
        }

        viewHolder.rootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(data);
            }
        });

        return itemView;
    }


    @NonNull @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<T> FilteredArrList = new ArrayList<>();

                if (constraint.length() == 0) {
                    results.count = items.size();
                    results.values = items;
                } else {
                    String query = constraint.toString().toLowerCase();
                    for (int i = 0; i < items.size(); i++) {
                        T item = items.get(i);
                        String data = item.menuItem();
                        if (data.contains(query)) {
                            FilteredArrList.add(item);
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                Filter.FilterResults filterResults = new FilterResults();
//                List<T> data = new ArrayList<>();
//                for (T item: data) {
//                    if (item.menuItem().equalsIgnoreCase(constraint.toString())) {
//                        data.add(item);
//                    }
//                }
//                filterResults.values = data;
//                filterResults.count = data.size();
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                notifyDataSetChanged();
//            }
//        };
    }

    private static class ViewHolder {
        private RelativeLayout rootItem;
        private TextView txtItem;
        private TextView txtSubItem;
    }
}
