package af.commons.widgets.lists;

import java.util.List;

import af.commons.widgets.validate.IntegerTextField;

public class AddRemoveJListInteger extends AddRemoveJList<Integer>{
    public AddRemoveJListInteger(Integer[] data, String name) {
        super(data, new IntegerTextField(name));
    }

    public AddRemoveJListInteger(List<Integer> data, String name) {
        super(data, new IntegerTextField(name));
    }
}
