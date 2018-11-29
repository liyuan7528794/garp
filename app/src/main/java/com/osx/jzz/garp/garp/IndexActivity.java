package com.osx.jzz.garp.garp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osx.jzz.garp.adapter.TabsAdapter;
import com.osx.jzz.garp.rewrite.TabsView;

public class IndexActivity extends android.support.v4.app.Fragment {
    TabsView tabsView;
    TabsAdapter tabsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_index, null);
        tabsView = (TabsView) view.findViewById(R.id.tabsView);
        //设置适配器
        tabsView.setTabs("热门手游", "热门端游");
        tabsView.setOnTabsItemClickListener(new TabsView.OnTabsItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                System.out.println(position);
            }
        });
        return view;
    }
}
