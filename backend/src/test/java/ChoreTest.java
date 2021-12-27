import io.github.xmchxup.latticy.model.ScorecardDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class ChoreTest {
	@Test
	void supTest() {
		String sup = "{\n" +
				"   \"id\":29,\n" +
				"   \"title\":\"AI游戏设计的评分表\",\n" +
				"   \"comment\":null,\n" +
				"   \"level\":\"不及格\",\n" +
				"   \"score\":42.0,\n" +
				"   \"guide_card_id\":1,\n" +
				"   \"judge_card_id\":5,\n" +
				"   \"topic_id\":16,\n" +
				"   \"guide_score\":12.0,\n" +
				"   \"guide_advise\":\"6666666666666666666\",\n" +
				"   \"judge_score\":30.0,\n" +
				"   \"judge_advise\":\"55555555555\",\n" +
				"   \"judge_answers\":\"10:5,11:5,12:5,13:5,14:5,15:5,\",\n" +
				"   \"guide_answers\":\"1:2,2:2,3:2,4:2,5:2,6:2,\",\n" +
				"   \"supplement\":null\n" +
				"}";
		System.out.println(sup);
		ScorecardDO scorecardDO = new ScorecardDO();
		ScorecardDO copyDO = new ScorecardDO();
		BeanUtils.copyProperties(scorecardDO, copyDO);
		scorecardDO.setSupplement("123");
		System.out.println(copyDO.getSupplement());
	}

	@Test
	public void myMap() {
		String[] split = "1:3,2:2,3:3,4:2,5:4,6:4,".split(",");
		for (String s : split) {
			System.out.println(s);
		}
	}

	@Test
	void SbTest() {
		String a = "XXXXX-补答辩";
		System.out.println(a.indexOf("-补答辩"));
		System.out.println(a.indexOf("-补答辩123123"));
		System.out.println(a.replace("-补答辩", "-补答辩"));
	}
}
