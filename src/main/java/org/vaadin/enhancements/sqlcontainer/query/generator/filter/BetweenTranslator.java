/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.enhancements.sqlcontainer.query.generator.filter;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Between;
import org.vaadin.enhancements.sqlcontainer.query.generator.StatementHelper;

public class BetweenTranslator implements FilterTranslator {

    @Override
    public boolean translatesFilter(Filter filter) {
        return filter instanceof Between;
    }

    @Override
    public String getWhereStringForFilter(Filter filter, StatementHelper sh) {
        Between between = (Between) filter;
        sh.addParameterValue(between.getStartValue());
        sh.addParameterValue(between.getEndValue());
        return QueryBuilder.quote(between.getPropertyId()) + " BETWEEN ? AND ?";
    }

}
