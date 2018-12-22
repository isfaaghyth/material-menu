package isfaaghyth.app.spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import isfaaghyth.app.spinner.adapter.DropdownAdapter;
import isfaaghyth.app.spinner.util.ItemContent;
import isfaaghyth.app.spinner.util.MenuItemListener;

/**
 * Created by isfaaghyth on 22/12/18.
 * github: @isfaaghyth
 */
public class SpinnerMenu extends RelativeLayout {

    private Context context;

    /**
     * each views of view_main
     */
    private TextView txtItem;
    private TextView txtSubItem;
    private ImageView icArrow;
    private ListView lstDropdown;
    private View currentItem;

    private MenuItemListener listener;

    /**
     * flag to show up/down of dropdown list
     */
    private boolean isCurrentClicked = false;

    private View createView(Context context) {
        View viewMain = LayoutInflater.from(context).inflate(R.layout.view_main, null);

        //root view (= current item)
        currentItem = viewMain.findViewById(R.id.current_item);

        txtItem = viewMain.findViewById(R.id.txt_item);
        txtSubItem = viewMain.findViewById(R.id.txt_sub_item);
        lstDropdown = viewMain.findViewById(R.id.lst_menu);
        icArrow = viewMain.findViewById(R.id.ic_arrow);

        return viewMain;
    }

    public SpinnerMenu(Context context) {
        super(context);
        this.context = context;
        addView(createView(context));
    }

    public SpinnerMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        addView(createView(context));
    }

    /**
     * insert all of data into listView
     * @param items
     */
    public void setItems(List<ItemContent> items) {
        build(items);
        initCurrentItem(items.get(0));
    }

    public void get(MenuItemListener listener) {
        this.listener = listener;
    }

    /**
     * check if the current item clicked or not
     */
    private void isCurrentClicked() {
        if (isCurrentClicked) {
            lstDropdown.setVisibility(View.GONE);
            icArrow.setImageResource(R.mipmap.ic_arrow_drop_down);
            isCurrentClicked = false;
        } else {
            lstDropdown.setVisibility(View.VISIBLE);
            icArrow.setImageResource(R.mipmap.ic_arrow_drop_up);
            isCurrentClicked = true;
        }
    }

    private void build(List<ItemContent> items) {
        DropdownAdapter adapter = new DropdownAdapter(items, context);
        adapter.setListener(new MenuItemListener() {
            @Override
            public void onClick(ItemContent item) {
                currentItem(item);
                isCurrentClicked();
                listener.onClick(item);
            }
        });
        lstDropdown.setAdapter(adapter);

        currentItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCurrentClicked();
            }
        });
    }

    private void initCurrentItem(final ItemContent item) {
        ItemContent currentItem = new ItemContent() {
            @Override public String menuItem() {
                return item.menuItem();
            }

            @Override public String menuSubItem() {
                return item.menuSubItem();
            }

            @Override public int dataId() {
                return 0;
            }
        };
        currentItem(currentItem);
    }

    /**
     * set current item
     * @param item
     */
    private void currentItem(ItemContent item) {
        txtItem.setText(item.menuItem());
        txtSubItem.setText(item.menuSubItem());
    }
}
