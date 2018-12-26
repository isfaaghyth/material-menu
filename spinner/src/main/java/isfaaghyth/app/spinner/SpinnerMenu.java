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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import isfaaghyth.app.spinner.adapter.DropdownAdapter;
import isfaaghyth.app.spinner.util.ItemContent;
import isfaaghyth.app.spinner.util.KeyboardUtils;
import isfaaghyth.app.spinner.util.MenuItemListener;

/**
 * Created by isfaaghyth on 22/12/18.
 * github: @isfaaghyth
 * a simple component to replace a traditional spinner.
 * by following the style of material components, this spinner can
 * help maximize utility in implementing the dropdown.
 *
 * The reason for developing the component is, because
 * the official components from Google for material components are
 * still not published, aka still in the planning stage
 * (read more: https://material.io/design/components/menus.html).
 */
public class SpinnerMenu extends RelativeLayout {

    private Context context;

    //view for main component display
    private TextView txtItem;
    private TextView txtSubItem;
    private ImageView icArrow;
    private ListView lstDropdown;
    private LinearLayout dropdownList;
    private EditText edtSearch;
    private View currentItem;

    //handles for item click events
    private MenuItemListener listener;

    //as a flag to find out whether this menu is clicked or not
    private boolean isClicked = false;

    private View createView(Context context) {
        View viewMain = LayoutInflater.from(context).inflate(R.layout.view_main, null);

        //root item
        currentItem = viewMain.findViewById(R.id.current_item);

        txtItem = viewMain.findViewById(R.id.txt_item);
        txtSubItem = viewMain.findViewById(R.id.txt_sub_item);
        lstDropdown = viewMain.findViewById(R.id.lst_menu);
        dropdownList = viewMain.findViewById(R.id.dropdown_list);
        edtSearch = viewMain.findViewById(R.id.edt_search);
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
     * insert all of data into dropdown list
     * @param items list of items
     * @param <T> the item type
     */
    public <T extends ItemContent> void setItems(List<T> items) {
        initCurrentItem(items.get(0));
        build(items);
    }

    public <T> void get(MenuItemListener<T> listener) {
        KeyboardUtils.hideSoftInput(getRootView(), getContext());
        this.listener = listener;
    }

    /**
     * as a flag to find out whether this menu is clicked or not,
     * this will also replace the arrow icon for the interactive aspect.
     */
    private void isCurrentClicked() {
        if (isClicked) {
            dropdownList.setVisibility(View.GONE);
            icArrow.setImageResource(R.mipmap.ic_arrow_drop_down);
            isClicked = false;
        } else {
            dropdownList.setVisibility(View.VISIBLE);
            icArrow.setImageResource(R.mipmap.ic_arrow_drop_up);
            isClicked = true;
        }
    }

    /**
     * set adapters for dropdown menus and display lists to limit items to be displayed.
     * @param items list of items
     * @param <T> the item type
     */
    private <T extends ItemContent> void build(final List<T> items) {
        final DropdownAdapter<T> adapter = new DropdownAdapter<>(items, context);

        //handle when the item is clicked through the dropdown menu
        adapter.setListener(new MenuItemListener<T>() {
            @Override
            public void onClick(T item) {
                currentItem(item);

                //change view
                isCurrentClicked();

                //hide keyboard
                KeyboardUtils.hideSoftInput(getRootView(), getContext());

                //reset adapter
                edtSearch.setText("");
                adapter.toDefault();

                listener.onClick(item);
            }
        });

        lstDropdown.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    adapter.toDefault();
                } else if (s.toString().length() > 3) {
                    adapter.filter(s.toString());
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }
        });

        //maximum of 5 items displayed
        int MAX_ITEMS_DISPLAYED = 5;
        if (adapter.getCount() > MAX_ITEMS_DISPLAYED) {
            View item = adapter.getView(0, null, lstDropdown);
            item.measure(0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, MAX_ITEMS_DISPLAYED * item.getMeasuredHeight()
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
     * display data based on selected items
     * @param item the current data
     */
    private void currentItem(ItemContent item) {
        txtItem.setTypeface(null, Typeface.BOLD);
        txtItem.setText(item.menuItem());

        if (!item.menuSubItem().isEmpty()) {
            txtSubItem.setText(item.menuSubItem());
        } else {
            txtSubItem.setVisibility(View.GONE);
        }

        currentItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCurrentClicked();
            }
        });
    }
}
