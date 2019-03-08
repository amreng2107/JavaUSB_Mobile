package de.jservice.kidsgard.Components;

import com.google.common.collect.Lists;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author AmrReda
 * @param <T>
 */

public abstract class ObjectTableModel<T> extends AbstractTableModel {

    protected List<T> entities = Lists.newArrayList();

    public abstract String[] getColumnLabels();

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    public int getColumnCount() {
        return getColumnLabels().length;
    }

    @Override
    public String getColumnName(int column) {
        return getColumnLabels()[column];
    }

    public void addEntity(T entity) {
        entities.add(entity);
        fireTableDataChanged();
    }

    public void addEntities(List<T> entities) {
        this.entities.addAll(entities);
        fireTableDataChanged();
    }

    public T getEntityByRow(int rowIndex) {
        return entities.get(rowIndex);
    }

    public void removeRow(int row) {
        entities.remove(row);
        fireTableDataChanged();
    }

    public void clear() {
        entities.clear();
    }
}
    
