package org.af.commons.widgets.tables;

import java.awt.Component;

public interface DFPanelIF {

	int indexOfTabComponent(Component tabComponent);

	String getTitleAt(int i);

	void removeLayer(int i);

}
