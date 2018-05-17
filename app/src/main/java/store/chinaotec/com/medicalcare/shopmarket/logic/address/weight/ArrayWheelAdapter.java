package store.chinaotec.com.medicalcare.shopmarket.logic.address.weight;

import java.util.ArrayList;

/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 */
public class ArrayWheelAdapter<DistrictModel> implements WheelAdapter {

    /**
     * The default items length
     */
    public static final int DEFAULT_LENGTH = 5;

    // items
    private ArrayList<DistrictModel> items;
    // length
    private int length;

    /**
     * Constructor
     *
     * @param items  the items
     * @param length the max items length
     */
    public ArrayWheelAdapter(ArrayList<DistrictModel> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     *
     * @param items the items
     */
    public ArrayWheelAdapter(ArrayList<DistrictModel> items) {
        this(items, DEFAULT_LENGTH);
    }

    @Override
    public String getItem(int index) {
        String text;
        if (index >= 0 && index < items.size()) {
            if (items.get(index).toString().length() > 5) {
                text = items.get(index).toString().substring(0, 4) + "..";
            } else {
                text = items.get(index).toString();
            }
            return text;

        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int getMaximumLength() {
        return length;
    }

}
