package isfaaghyth.app.spinner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import isfaaghyth.app.spinner.R;
import isfaaghyth.app.spinner.util.ItemContent;
import isfaaghyth.app.spinner.util.MenuItemListener;

/**
 * Created by isfaaghyth on 22/12/18.
 * github: @isfaaghyth
 */
public class DropdownAdapter extends ArrayAdapter<ItemContent> {

    private Context context;
    private List<ItemContent> items;
    private MenuItemListener listener;

    public DropdownAdapter(List<ItemContent> items, Context context) {
        super(context, R.layout.item_menu, items);
        this.context = context;
        this.items = items;
    }

    public void setListener(MenuItemListener listener) {
        this.listener = listener;
    }

    @NonNull @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ItemContent data = items.get(position);
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

    static class ViewHolder {
        RelativeLayout rootItem;
        TextView txtItem;
        TextView txtSubItem;
    }
}
