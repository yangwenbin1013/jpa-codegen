import io.github.gcdd1993.jpa.codegen.CodeGenerator;
import io.github.gcdd1993.jpa.codegen.entity.UnloadWeightConfigEntity;
import org.junit.Test;

/**
 * TODO
 *
 * @author gaochen
 * Created on 2019/6/19.
 */
public class ApplicationTest {

    @Test
    public void generate() {

        new CodeGenerator("src/main/resources/example.properties")
                .registerRender("form")
                .registerRender("repository")
                .registerRender("service")
                .registerRender("controller")
                .clazzInclude(UnloadWeightConfigEntity.class)
                .generate();

    }
}
