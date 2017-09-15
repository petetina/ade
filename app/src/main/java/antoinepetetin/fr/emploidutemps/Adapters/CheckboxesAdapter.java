package antoinepetetin.fr.emploidutemps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import antoinepetetin.fr.emploidutemps.Models.Group;
import antoinepetetin.fr.emploidutemps.R;

/**
 * Created by antoine on 17/12/16.
 */

public class CheckboxesAdapter extends ArrayAdapter<Group> {
    private ArrayList<Group> groupList;
    private List<Integer> selectedGroups;
    private Context context;

    public CheckboxesAdapter(Context context, int textViewResourceId, List<Group> groupList, List<Integer> selectedGroups) {
        super(context, textViewResourceId, groupList);
        this.context = context;
        this.groupList = new ArrayList<>();
        this.groupList.addAll(groupList);
        this.selectedGroups = selectedGroups;

    }

    private class ViewHolder {
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.group_info, null);

            holder = new ViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Group group = (Group) cb.getTag();
                    group.setSelected(cb.isChecked());

                    if(group.isSelected())
                        selectedGroups.add(group.getId());
                    else
                        selectedGroups.remove(Integer.valueOf(group.getId()));//On utilise le type Integer pour qu'il supprime l'objet et qu'il ne considère pas l'id comme un index de la liste
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group group = groupList.get(position);
        holder.checkBox.setText(group.getLibelle());
        holder.checkBox.setChecked(group.isSelected());
        holder.checkBox.setTag(group);

        return convertView;

    }

}
