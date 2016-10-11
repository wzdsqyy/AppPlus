/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wzdsqyy.applib.utils.helper;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Iterator;

public class MutiCheckHelper implements OnItemCheckedChangeListener {
    private ArrayList<OnItemCheckedChangeListener> onCheckedChangeListeners;
    private SparseArray<Boolean> checks;

    public MutiCheckHelper(int count) {
        checks = new SparseArray<>(count);
        onCheckedChangeListeners = new ArrayList<>();
    }

    public boolean isChecked(int possion) {
        return checks.get(possion, false);
    }

    public void addOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        boolean contains = onCheckedChangeListeners.contains(listener);
        if (!contains) {
            onCheckedChangeListeners.add(listener);
        }
    }

    public void removeOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        onCheckedChangeListeners.remove(listener);
    }


    public void notifyOnItemCheckedChangeListener(boolean isChecked) {
        Iterator<OnItemCheckedChangeListener> iterator = onCheckedChangeListeners.iterator();
        while (iterator.hasNext()) {
            OnItemCheckedChangeListener listener = iterator.next();
            listener.onCheckedChanged(isChecked);
        }
    }

    @Override
    public void onCheckedChanged(boolean isChecked) {
        for (int i = 0; i < checks.size(); i++) {
            checks.put(i, isChecked);
        }
        notifyOnItemCheckedChangeListener(isChecked);
    }
}