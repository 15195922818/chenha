package com.ai.web;

import java.util.Vector;

public class extVector<E> extends Vector<E>
{
	@Override
	public synchronized E remove(int index) {
		modCount++;
		if (index >= elementCount)
		    throw new ArrayIndexOutOfBoundsException(index);
		Object oldValue = elementData[index];

		int numMoved = elementCount - index - 1;
		if (numMoved > 0)
		    System.arraycopy(elementData, index+1, elementData, index,
				     numMoved);
		elementData[--elementCount] = null; // Let gc do its work

		return (E)oldValue;
	}
}
