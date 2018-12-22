package isfaaghyth.app.spinner.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

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

}
