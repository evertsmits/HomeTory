package com.example.evertsmits.hometory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.evertsmits.hometory.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Evert Smits on 23/10/2017.
 * ExpListAdapter is an adapter used for the expanded list view
 */

public class ExpListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> cats;
    private Map<String, List<String>> items;


    /**
     * ExpListAdapter
     * Constructor for an ExpandedListAdapter with the following parameters
     *
     * @param context The context in which the exp list is used
     * @param cats    the category list that needs to be displayed i.e the parent list
     * @param items   the items list that needs to be displayed nested in the parent list. ie the child list
     */
    public ExpListAdapter(Context context, List<String> cats, Map<String, List<String>> items) {
        this.context = context;
        this.cats = cats;
        this.items = items;
    }

    /**
     * getGroupCount method to return the count of the parent list
     *
     * @return the amount of items in the parent list
     */
    @Override
    public int getGroupCount() {
        return cats.size();
    }

    /**
     * getChildrenCount method to return the amount of children in the children list
     *
     * @param i position in parent list
     * @return the amount of items in the child list of asked parent
     */
    @Override
    public int getChildrenCount(int i) {
        return items.get(cats.get(i)).size();
    }

    /**
     * getGroup method to return the current parent item
     *
     * @param i current position in the parent list
     * @return the parent item with the position i
     */
    @Override
    public Object getGroup(int i) {
        return cats.get(i);
    }

    /**
     * getChild method to return the current child within the specified parent
     *
     * @param i  current position in the parent list
     * @param i1 current position in the child list
     * @return the child item with the position
     */
    @Override
    public Object getChild(int i, int i1) {
        return items.get(cats.get(i)).get(i1);
    }

    /**
     * getGroupId method to return the parent id
     *
     * @param i current position in the parent list
     * @return the group id of the parent item at the specified position
     */
    @Override
    public long getGroupId(int i) {
        return i;
    }

    /**
     * getChildId method to return the child id
     *
     * @param i  current position in the parent list
     * @param i1 current position in the child list
     * @return the child id of the child item of the parent item at the specified position
     */
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    /**
     * hasStableIds method to return if the list has stable ids
     *
     * @return a boolean
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * getGroupView method returns a view using an inflated custom parent list xml file
     *
     * @param i         position parent list item
     * @param b         boolean
     * @param view      current view that the expanded list is within
     * @param viewGroup current ViewGroup
     * @return a view using the inflated custom parent list xml file
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String cat_title = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exp_list_parent, null);
        }

        TextView txtParent = view.findViewById(R.id.text_parent);
        txtParent.setText(cat_title);
        return view;
    }

    /**
     * getChildView method returns a view using an inflated custom child list xml file
     *
     * @param i         position parent list item
     * @param i1        position child list item
     * @param b         boolean
     * @param view      current view that the expanded list is within
     * @param viewGroup current viewGroup
     * @return a view using the inflated custom child list xml file
     */
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String item_amount = (String) getChild(i, i1);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exp_list_child, null);
        }
        TextView txtChild = view.findViewById(R.id.text_child);
        txtChild.setText(item_amount);
        return view;
    }

    /**
     * isChildSelectable returns a boolean whether the child is selectable
     *
     * @param i  position parent list item
     * @param i1 position child list item
     * @return a boolean whether the sentence stated above is true
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
