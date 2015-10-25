/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.dmn.engine.impl.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.camunda.bpm.dmn.engine.type.DataTypeTransformer;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.DateValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

/**
 * Transform values of type {@link Date} and {@link String} into
 * {@link DateValue} which contains date and time. A String should have the format
 * {@code yyyy-MM-dd'T'HH:mm:ss}.
 *
 * @author Philipp Ossler
 */
public class DateDataTypeTransformer implements DataTypeTransformer {

  protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

  @Override
  public TypedValue transform(Object value) throws IllegalArgumentException {
    if (value instanceof Date) {
      return Variables.dateValue((Date) value);

    } else if (value instanceof String) {
      Date date = transformString((String) value);
      return Variables.dateValue(date);

    } else {
      throw new IllegalArgumentException();
    }
  }

  protected Date transformString(String value) {
    try {
      return format.parse(value);
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

}
