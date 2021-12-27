import com.aspose.words.Font;
import com.aspose.words.*;
import org.junit.jupiter.api.Test;

import java.awt.*;


/**
 * https://apireference.aspose.com/words/java/com.aspose.words/CellFormat#HorizontalMerge
 * https://gitee.com/liuzy1988/aspose-words-crack
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class AsposeTest {
	@Test
	public void table() throws Exception {
		double totalRowWidth = 0.0;
		// Create a Document object
		Document doc = new Document();
		// Create a DocumentBuilder object
		DocumentBuilder builder = new DocumentBuilder(doc);
//		打印前两页
		Font font = builder.getFont();
		font.setSize(43);
		font.setBold(true);
		font.setColor(Color.BLACK);
		font.setName("华文行楷");
		builder.insertImage("./assets/images/logo.png");

		builder.writeln();
		builder.writeln();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.writeln("浙江工商职业技术学院\n电子信息学院\n");


		font.setSize(27);
		font.setName("华文新魏");
		builder.writeln("毕业设计成绩\n评定手册\n");

		font.setSize(14);
		font.setName("仿宋_GB2312");


		builder.getParagraphFormat().setLineSpacing(15);
		builder.writeln("题    目 _________________________");
		builder.writeln("姓    名 _________________________");
		builder.writeln("学    号 _________________________");
		builder.writeln("专业班级  ________________________");
//		builder.getParagraphFormat().setLeftIndent(13);
		builder.writeln("校外指导教师 _____________________");
		builder.writeln("校内指导老师 _____________________");
		builder.writeln();
		builder.writeln("年       月       日");



		builder.insertBreak(BreakType.PAGE_BREAK);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		font.setSize(16);
		font.setName("宋体");
		builder.writeln("说明：");
		font.setSize(12);
		builder.writeln("一、毕业设计成绩按两个部分评定，即：");
		builder.writeln("\t1、学生毕业设计环节进行情况分（40分），由指导教师根据学生的完成情况确定，并必须在学生参加答辩前给出；");
		builder.writeln("\t2、毕业设计质量及答辩分（60分），由答辩小组根据学生毕业设计质量及答辩情况给出。");
		builder.writeln("二、毕业设计评分按优秀、良好、中等、及格和不及格五级评分。评分时，把各项所得分数全部加起来，按100-90分为“优秀”、89-80" +
				"分为“良好”、79-70分为“中等”、69-60分为“及格”、60分以下为“不及格”的标准折合成五级分制。侥弘廖");
		builder.writeln("三、本评分表各项目提供了四种选项（即A、B、C、D" +
				"）及其对应的评分标准，教师在评分时，可借鉴这四种选项的评分标准，根据实际情况给出相应的区间分数。");
		builder.writeln("四、补答辩记录表在学生一次答辩不及格的情况下使用，且补答辩后的成绩只能以“及格”和“不及格”两类记载。");

		builder.getParagraphFormat().clearFormatting();

//		打印前两页
		builder.insertBreak(BreakType.PAGE_BREAK);
		// Specify font formatting
		font.setSize(18);
		font.setName("宋体");

//		Title
		builder.writeln("软件技术专业毕业设计指导教师评分记录");


		font.setSize(12);
		font.setBold(false);

//		表1
		Table table = builder.startTable();
//		表头
		builder.insertCell();
		table.autoFit(AutoFitBehavior.AUTO_FIT_TO_WINDOW);
		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
		builder.write("分项评分要点");
		totalRowWidth += builder.getCellFormat().getWidth();


		builder.insertCell();
		double scoreListCellWidth = builder.getCellFormat().getWidth();
		builder.getCellFormat().setWidth(40);

		builder.write("评分");
		totalRowWidth += builder.getCellFormat().getWidth();
		double titleRowHeight = builder.getRowFormat().getHeight();
		builder.endRow();


		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

//		内容
//		选项列表
		builder.getRowFormat().setHeight(120);
		builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
		builder.insertCell();
		builder.getCellFormat().setWidth(scoreListCellWidth);
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.TOP);
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(true);
		builder.writeln("１、资料检索、阅读及综述能力分（计５分）");
		builder.getFont().setBold(false);
		builder.writeln("A、资料查阅、翻译、阅读能力很强，有完整的资料综述报告　　　　　	（5）");
		builder.writeln("B、资料查阅、翻译、阅读能力较强，有文献综述报告　　　　　　　　	（4）");
		builder.writeln("C、资料查阅、翻译、阅读能力一般，进行了资料查阅总结　　　　　　	（3）");
		builder.write("D、资料查阅、翻译、阅读能力较差，没有进行资料查阅总结　　　　　	（0）");
//		评分
		builder.insertCell();
		builder.getCellFormat().setWidth(40);
		builder.getFont().setSize(18);
		builder.getFont().setBold(true);

		builder.write("50");
		builder.endRow();


//		表尾
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(false);


		builder.getRowFormat().setHeight(40);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.write("合计");

		builder.insertCell();
		builder.endRow();
//		签名
		builder.getRowFormat().setHeight(80);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.writeln();
		builder.writeln();
		builder.writeln();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
		builder.write("指导教师签名：              年    月    日   ");

		builder.endRow();

//		表1结束
		builder.endTable();

		builder.writeln();
		builder.writeln();
		builder.writeln();

//		表2
		builder.insertBreak(BreakType.PAGE_BREAK);
		Font font2 = builder.getFont();
		font2.setSize(18);
		font2.setBold(true);
		font2.setColor(Color.BLACK);
		font2.setName("宋体");

//		Title
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.writeln("软件技术专业毕业设计质量及答辩评分记录");


		font.setSize(12);
		font.setBold(false);


		Table table2 = builder.startTable();
//		表头

		builder.getRowFormat().setHeight(titleRowHeight);
		builder.insertCell();
		table2.autoFit(AutoFitBehavior.AUTO_FIT_TO_WINDOW);
		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
		builder.write("分项评分要点");
		totalRowWidth = builder.getCellFormat().getWidth();


		builder.insertCell();
		scoreListCellWidth = builder.getCellFormat().getWidth();
		builder.getCellFormat().setWidth(40);

		builder.write("评分");
		totalRowWidth += builder.getCellFormat().getWidth();
		builder.endRow();


		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

		//		内容
//		选项列表
		builder.getRowFormat().setHeight(120);
		builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
		builder.insertCell();
		builder.getCellFormat().setWidth(scoreListCellWidth);
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.TOP);
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(true);
		builder.writeln("1、毕业设计说明文档格式规范分（计5分）");
		builder.getFont().setBold(false);
		builder.writeln("A、格式规范，符合学校规定的设计说明文档打印格式要求\n" +
				"（1．封面2．任务书3．开题报告4．项目设计说明表项目意义和目标5．项目技术架构\n" +
				"6．项目设计7．项目实现8．项目特色9. 总结10．参考文献）  		（5）");
		builder.writeln("B、A中所列项目中有1项或2项不合格者                      	（3）");
		builder.writeln("C、A中所列项目中有3项到4项不合格者                      	（2）");
		builder.write("D、A中所列项目中超过5项不合格者                           	（1）");
//		评分
		builder.insertCell();
		builder.getCellFormat().setWidth(40);
		builder.getFont().setSize(18);
		builder.getFont().setBold(true);

		builder.write("50");
		builder.endRow();

		//		表尾
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(false);


		builder.getRowFormat().setHeight(40);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.write("合计");

		builder.insertCell();
		builder.endRow();


		builder.getRowFormat().setHeight(200);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.DEFAULT);
		builder.writeln();
		builder.writeln("总成绩");

		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2 * 0.4);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
		builder.write("答辩教师签字");

		builder.insertCell();
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.DEFAULT);
		builder.getCellFormat().setWidth(totalRowWidth / 2 * 0.6);
// 内部表格
		String html = "<table style=\"border-collapse:collapse;\">";
		String content = "";


		for (int i = 1; i <= 5; i++) {
			content += "<tr style=\"width:210;height:48;\">\n" +
					"    <td style=\"width:30;border: 1px solid black;\">" + i + "</td>\n" +
					"    <td style=\"width:180;border: 1px solid black;\"></td>\n" +
					"  </tr>\n";
		}
		builder.insertHtml(html + content + "</table>");

// 内部表格
		builder.endRow();

		builder.getRowFormat().setHeight(210);
		builder.insertCell();
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.DEFAULT);
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.writeln("答辩小组评语：");
		for (int i = 0; i < 6; i++) {
			builder.writeln();
		}
		builder.writeln("答辩等级：");
		builder.writeln();
		builder.writeln();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
		builder.getParagraphFormat().setRightIndent(40);
		builder.writeln("答辩组长（签名）：");
		builder.writeln();
		builder.writeln("年   月   日");

		builder.insertCell();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.writeln("答辩委员会意见：");
		for (int i = 0; i < 6; i++) {
			builder.writeln();
		}
		builder.writeln("等级：");
		builder.writeln();
		builder.writeln();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
		builder.getParagraphFormat().setRightIndent(40);
		builder.writeln("答辩委员会主任（签章）：");
		builder.writeln();
		builder.write("年   月   日");

		builder.endRow();

//		表2结束
		builder.endTable();
		// Save the document
		doc.save("test.docx");
	}

}
