import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.jupiter.api.Test;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class CrackAspose {
	@Test
	void crackAsposeWords() throws Exception {
		ClassPool.getDefault().insertClassPath("C:\\Users\\sunhu\\Downloads\\jar\\aspose-words-20.12-jdk17.jar");
		CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzZDZ");
		CtMethod zzZ4u = zzZJJClass.getDeclaredMethod("zzZ4n");
		CtMethod zzZ4t = zzZJJClass.getDeclaredMethod("zzZ4m");
		zzZ4u.setBody("{return 1;}");
		zzZ4t.setBody("{return 1;}");
		zzZJJClass.writeFile("C:\\Users\\sunhu\\Downloads\\Music");
	}
}
