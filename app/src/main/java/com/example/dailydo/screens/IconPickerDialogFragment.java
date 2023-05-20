package com.example.dailydo.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dailydo.R;

import java.util.ArrayList;
import java.util.List;

public class IconPickerDialogFragment extends DialogFragment {

    private OnIconSelectedListener listener;

    public interface OnIconSelectedListener {
        void onIconSelected(int iconId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Выберите категорию");

        // Получаем массив идентификаторов иконок и массив названий иконок из ресурсов
        TypedArray iconIds = getResources().obtainTypedArray(R.array.icon_ids);
        String[] iconNames = getResources().getStringArray(R.array.icon_names);

        // Создаем список элементов иконок
        List<IconItem> iconItems = new ArrayList<>();
        for (int i = 0; i < iconIds.length(); i++) {
            int iconId = iconIds.getResourceId(i, -1);
            String iconName = iconNames[i];
            iconItems.add(new IconItem(iconId, iconName));
        }

        // Освобождаем ресурсы TypedArray
        iconIds.recycle();

        // Создаем адаптер для списка элементов иконок
        IconAdapter adapter = new IconAdapter(requireContext(), iconItems);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Получаем выбранный идентификатор иконки из адаптера
                IconItem selectedIcon = adapter.getItem(which);
                if (selectedIcon != null) {
                    int selectedIconId = selectedIcon.getIconId();

                    // Передаем выбранный идентификатор иконки в колбэк
                    if (listener != null) {
                        listener.onIconSelected(selectedIconId);
                    }
                }
            }
        });

        return builder.create();
    }

    public void setOnIconSelectedListener(OnIconSelectedListener listener) {
        this.listener = listener;
    }

    private static class IconItem {
        private int iconId;
        private String iconName;

        public IconItem(int iconId, String iconName) {
            this.iconId = iconId;
            this.iconName = iconName;
        }

        public int getIconId() {
            return iconId;
        }

        public String getIconName() {
            return iconName;
        }
    }

    private static class IconAdapter extends ArrayAdapter<IconItem> {

        private LayoutInflater inflater;

        public IconAdapter(@NonNull Context context, @NonNull List<IconItem> icons) {
            super(context, 0, icons);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_item_icon, parent, false);
                ViewHolder holder = new ViewHolder(view);
                view.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) view.getTag();
            IconItem iconItem = getItem(position);
            if (iconItem != null) {
                holder.iconImageView.setImageResource(iconItem.getIconId());
                holder.iconNameTextView.setText(iconItem.getIconName());
            }

            return view;
        }

        private static class ViewHolder {
            ImageView iconImageView;
            TextView iconNameTextView;

            public ViewHolder(View itemView) {
                iconImageView = itemView.findViewById(R.id.iconImageView);
                iconNameTextView = itemView.findViewById(R.id.iconNameTextView);
            }
        }
    }
}
