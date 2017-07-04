package com.twiceyuan.autoform.sample.form;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.twiceyuan.autoform.FormItemEntity;
import com.twiceyuan.autoform.ResultWatcher;
import com.twiceyuan.autoform.provider.LayoutProvider;
import com.twiceyuan.autoform.sample.R;

import java.util.List;

/**
 * Created by twiceYuan on 2017/7/4.
 * <p>
 * 下拉选择框表单元素
 */
public class SelectorLayoutProvider extends LayoutProvider {

    private TextView            mTvLabel;
    private Spinner             mSpSelector;
    private PositionValueMapper mPositionValueMapper;

    @Override
    public int layoutId() {
        return R.layout.form_item_selector;
    }

    @Override
    public void bindData(FormItemEntity field) {
        mTvLabel.setText(field.label);
    }

    public void setData(final List<String> selections) {
        mSpSelector.setAdapter(new ArrayAdapter<String>(
                mSpSelector.getContext(),
                R.layout.spinner_default,
                selections
        ) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater
                            .from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(selections.get(position));
                return convertView;
            }
        });
    }

    public void setPositionValueMapper(PositionValueMapper positionValueMapper) {
        mPositionValueMapper = positionValueMapper;
    }

    @Override
    public void resultWatcher(final ResultWatcher watcher) {
        mSpSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mPositionValueMapper != null) {
                    watcher.updateResult(mPositionValueMapper.map(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void initView(View view) {
        mTvLabel = (TextView) view.findViewById(R.id.tv_label);
        mSpSelector = (Spinner) view.findViewById(R.id.sp_selector);
    }

    public Spinner getSpSelector() {
        return mSpSelector;
    }

    public TextView getTvLabel() {
        return mTvLabel;
    }

    public interface PositionValueMapper {
        Object map(int position);
    }
}
