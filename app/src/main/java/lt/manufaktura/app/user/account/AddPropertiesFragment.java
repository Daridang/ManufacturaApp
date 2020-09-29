package lt.manufaktura.app.user.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentAddPropertiesBinding;


public class AddPropertiesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public AddPropertiesFragment() {
        // Required empty public constructor
    }

    public static AddPropertiesFragment newInstance() {
        return new AddPropertiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAddPropertiesBinding fapb = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_properties, container, false
        );

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(requireActivity(),
                        R.array.data_types, android.R.layout.simple_spinner_item
                );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        fapb.dataTypeSpinnerId.setAdapter(adapter);
        fapb.dataTypeSpinnerId.setOnItemSelectedListener(this);

        return fapb.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}