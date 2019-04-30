package org.camunda.bpm.dmn.engine.el;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.dmn.engine.impl.el.JuelElProvider;
import org.camunda.bpm.dmn.engine.impl.spi.el.ElExpression;
import org.camunda.bpm.dmn.engine.impl.spi.el.ElProvider;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Test;

/**
 * @author MPlukas
 *
 */
public class JuelElProviderTest {

    protected JuelElProvider createJuelElProvider() {
        return new JuelElProvider();
    }

    @Test
    public void testJuelResolvesNestedProperty() {
      ElProvider elProvider = createJuelElProvider();
      ElExpression elExpression = elProvider.createExpression("${a.b}");

      Map<String, Object> mapVar = new HashMap<>(1);
      mapVar.put("b", "B_FROM_MAP");
      Object val = elExpression.getValue(Variables.createVariables().putValue("a", mapVar).putValue("b", "B_FROM_CONTEXT").asVariableContext());

      assertThat(val).isEqualTo("B_FROM_MAP");
    }

    @Test
    public void testJuelResolvesListIndex() {
      ElProvider elProvider = createJuelElProvider();
      ElExpression elExpression = elProvider.createExpression("${a[0]}");

      List<String> listVar = new ArrayList<>(1);
      listVar.add("0_FROM_LIST");
      Object val = elExpression.getValue(Variables.createVariables().putValue("a", listVar).asVariableContext());

      assertThat(val).isEqualTo("0_FROM_LIST");
    }

}
