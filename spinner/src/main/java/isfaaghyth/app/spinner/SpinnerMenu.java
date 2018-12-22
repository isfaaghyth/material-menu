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
package isfaaghyth.app.spinner;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private boolean isClicked = false;

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
    public <T extends ItemContent> void setItems(List<T> items) {
        build(items);
        initCurrentItem(items.get(0));
    }

    public <T> void get(MenuItemListener<T> listener) {
        this.listener = listener;
    }

    /**
     * check if the current item clicked or not
     */
    private void isCurrentClicked() {
        if (isClicked) {
            lstDropdown.setVisibility(View.GONE);
            icArrow.setImageResource(R.mipmap.ic_arrow_drop_down);
            isClicked = false;
        } else {
            lstDropdown.setVisibility(View.VISIBLE);
            icArrow.setImageResource(R.mipmap.ic_arrow_drop_up);
            isClicked = true;
        }
    }

    private <T extends ItemContent> void build(List<T> items) {
        DropdownAdapter adapter = new DropdownAdapter<>(items, context);
        adapter.setListener(new MenuItemListener<T>() {
            @Override
            public void onClick(T item) {
                currentItem(item);
                isCurrentClicked();
                listener.onClick(item);
            }
        });

        currentItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCurrentClicked();
            }
        });

        lstDropdown.setAdapter(adapter);

        if (adapter.getCount() > 5) {
            View item = adapter.getView(0, null, lstDropdown);
            item.measure(0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight())
            );
            lstDropdown.setLayoutParams(params);
        }
    }

    private void initCurrentItem(final ItemContent item) {
        ItemContent currentItem = new ItemContent() {
            @Override public String menuItem() {
                return item.menuItem();
            }
            @Override public String menuSubItem() {
                return item.menuSubItem();
            }
        };
        currentItem(currentItem);
    }

    /**
     * set current item
     * @param item
     */
    private void currentItem(ItemContent item) {
        txtItem.setTypeface(null, Typeface.BOLD);
        txtItem.setText(item.menuItem());
        if (!item.menuSubItem().isEmpty()) {
            txtSubItem.setText(item.menuSubItem());
        } else {
            txtSubItem.setVisibility(View.GONE);
        }
    }
}
