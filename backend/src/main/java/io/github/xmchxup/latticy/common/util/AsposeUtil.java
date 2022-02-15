package io.github.xmchxup.latticy.common.util;

import com.aspose.words.Font;
import com.aspose.words.*;
import io.github.xmchxup.latticy.bo.ScoreTableBO;
import io.github.xmchxup.latticy.model.OptionDO;
import io.github.xmchxup.latticy.vo.QuestionVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class AsposeUtil {
	private static double totalRowWidth = 0.0;
	private static double titleRowHeight = 0.0;
	private static double scoreListCellWidth = 0.0;

	private static int contentNum = 1;

	public static ResponseEntity<byte[]> scorecardToWord(ScoreTableBO data) throws Exception {

		// Create a Document object
		Document doc = new Document();
		// Create a DocumentBuilder object
		DocumentBuilder builder = new DocumentBuilder(doc);
		generateTwoCommonPage(builder);
		builder.insertBreak(BreakType.PAGE_BREAK);
		generateGuideTable(builder, data);
		builder.getRowFormat().setHeight(titleRowHeight);
//		builder.insertBreak(BreakType.PAGE_BREAK);
//		generateJudgeTable(builder, data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		try {


			String filename = data.getScorecardDO().getTitle() + ".docx";
			headers.setContentDispositionFormData("attachment", new String(filename.getBytes("UTF-8"),
					"ISO-8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//			String fileName = URLEncoder.encode(data.getScorecardDO().getTitle() + ".docx", "UTF-8");
//			headers.setContentDispositionFormData("attachment", fileName);

			doc.save(outputStream, SaveFormat.DOCX);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.CREATED);
	}

	private static void generateTwoCommonPage(DocumentBuilder builder) throws Exception {
		//		打印前两页
		Font font = builder.getFont();
		font.setSize(43);
		font.setBold(true);
		font.setColor(Color.BLACK);
		font.setName("华文行楷");
//		builder.insertImage("./assets/images/logo.png");

		builder.writeln();
		builder.writeln();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.writeln("请输入自己的信息\n电子信息学院\n");


		font.setSize(27);
		font.setName("华文新魏");
		builder.writeln("毕业设计成绩\n评定手册\n");

		font.setSize(14);
		font.setName("仿宋_GB2312");
		builder.getParagraphFormat().setLineSpacing(15);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.writeln("题    目 _________________________");
		builder.writeln("姓    名 _________________________");
		builder.writeln("学    号 _________________________");
		builder.writeln("专业班级  ________________________");
		builder.writeln("校外指导教师 _____________________");
		builder.writeln("校内指导老师 _____________________");
		builder.writeln();
		builder.writeln();
		builder.writeln("年       月       日");

		builder.insertBreak(BreakType.PAGE_BREAK);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		font.setSize(16);
		font.setName("宋体");
		builder.writeln("说明：");
		font.setSize(12);
		font.setBold(false);
		builder.writeln("\t一、毕业设计成绩按两个部分评定，即：");
		builder.writeln("\t1、学生毕业设计环节进行情况分（40分），由指导教师根据学生的完成情况确定，并必须在学生参加答辩前给出；");
		builder.writeln("\t2、毕业设计质量及答辩分（60分），由答辩小组根据学生毕业设计质量及答辩情况给出。");
		builder.writeln("\t二、毕业设计评分按优秀、良好、中等、及格和不及格五级评分。评分时，把各项所得分数全部加起来，按100-90分为“优秀”、89-80" +
				"分为“良好”、79-70分为“中等”、69-60分为“及格”、60分以下为“不及格”的标准折合成五级分制。侥弘廖");
		builder.writeln("\t三、本评分表各项目提供了四种选项（即A、B、C、D" +
				"）及其对应的评分标准，教师在评分时，可借鉴这四种选项的评分标准，根据实际情况给出相应的区间分数。");
		builder.writeln("\t四、补答辩记录表在学生一次答辩不及格的情况下使用，且补答辩后的成绩只能以“及格”和“不及格”两类记载。");

		builder.getParagraphFormat().clearFormatting();
	}

	private static void generateGuideTable(DocumentBuilder builder, ScoreTableBO data) throws Exception {
		generateCommonTitle(builder, data.getGuideCard().getTitle());
//		内容
//		选项列表
		List<QuestionVO> questions = data.getGuideCard().getQuestions();
		Map<String, String> guideAnswers = data.getGuideAnswers();

		for (QuestionVO question : questions) {
			String score = guideAnswers.get(String.valueOf(question.getId()));
			if (StringUtils.isEmpty(score)) {
				score = "0";
			}
			generateContent(builder, question, score);
			contentNum++;
		}
		contentNum = 1;

//		表尾
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(false);


		builder.getRowFormat().setHeight(40);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.write("合计");

		builder.insertCell();
		Double guideScore = data.getScorecardDO().getGuideScore();
		if (guideScore % 1 == 0) {
			builder.write(String.valueOf(guideScore.intValue()));
		} else {
			builder.write(guideScore.toString());
		}
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
	}

	private static void generateJudgeTable(DocumentBuilder builder, ScoreTableBO data) throws Exception {
		generateCommonTitle(builder, data.getJudgeCard().getTitle());

		//		内容
//		选项列表

		List<QuestionVO> questions = data.getJudgeCard().getQuestions();
		Map<String, String> judgeAnswers = data.getJudgeAnswers();
		for (QuestionVO question : questions) {
			String score = judgeAnswers.get(String.valueOf(question.getId()));
			if (StringUtils.isEmpty(score)) {
				score = "0";
			}
			generateContent(builder, question, score);
			contentNum++;
		}
		contentNum = 1;


		//		表尾
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(false);


		builder.getRowFormat().setHeight(40);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.write("合计: ");

		builder.insertCell();
		Double judgeScore = data.getScorecardDO().getJudgeScore();
		if (judgeScore % 1 == 0) {
			builder.write(String.valueOf(judgeScore.intValue()));
		} else {
			builder.write(judgeScore.toString());
		}

		builder.endRow();


		builder.getRowFormat().setHeight(200);
		builder.insertCell();
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.DEFAULT);
		builder.writeln();
		Double score = data.getScorecardDO().getScore();
		if (score % 1 == 0) {
			builder.writeln("总成绩：" + score.intValue());
		} else {
			builder.writeln("总成绩：" + score);
		}


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
		List<String> guideTeacherNames = data.getGuideTeacherNames();
		for (int i = 1; i <= 5; i++) {
			content += "<tr style=\"width:180;height:48;\">\n" +
					"    <td style=\"width:30;border: 1px solid black;\">" + i + "</td>\n" +
					"    <td style=\"width:150;border: 1px solid black;\">" +
//					guideTeacherNames.get(i - 1) +
					"</td>\n" + "  </tr>\n";
		}
		builder.insertHtml(html + content + "</table>");

// 内部表格
		builder.endRow();

		builder.getRowFormat().setHeight(210);
		builder.insertCell();
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.DEFAULT);
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.getCellFormat().setWidth(totalRowWidth / 2);
		String guideAdvise = "";
		if (data.getScorecardDO().getGuideAdvise() != null)
			guideAdvise = data.getScorecardDO().getGuideAdvise();
		builder.writeln("答辩小组评语：" + guideAdvise);
		for (int i = 0; i < 6; i++) {
			builder.writeln();
		}
		builder.writeln("答辩等级：" + data.getScorecardDO().getLevel());
		builder.writeln();
		builder.writeln();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
		builder.getParagraphFormat().setRightIndent(40);
		builder.writeln("答辩组长（签名）：");
		builder.writeln();
		builder.writeln("年   月   日");

		builder.insertCell();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		String comment = "";
		if (data.getScorecardDO().getComment() != null)
			comment = data.getScorecardDO().getComment();
		builder.writeln("答辩委员会意见：" + comment);
		for (int i = 0; i < 6; i++) {
			builder.writeln();
		}
		builder.writeln("等级：" + data.getScorecardDO().getLevel());
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
	}

	private static void generateCommonTitle(DocumentBuilder builder, String title) throws Exception {

		// Specify font formatting
		Font font = builder.getFont();
		font.setSize(18);
		font.setBold(true);
		font.setColor(Color.BLACK);
		font.setName("宋体");

//		Title
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
//		稍微处理下标题
		if (title.contains("模板")) {
			title = title.replaceAll("模板", "");
		}
		builder.writeln(title + "记录");


		font.setSize(12);
		font.setBold(false);

//		表1
		Table table = builder.startTable();
//		表头
		builder.insertCell();
		table.autoFit(AutoFitBehavior.AUTO_FIT_TO_WINDOW);
		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
		builder.write("分项评分要点");
		totalRowWidth = builder.getCellFormat().getWidth();


		builder.insertCell();
		scoreListCellWidth = builder.getCellFormat().getWidth();
		builder.getCellFormat().setWidth(40);

		builder.write("评分");
		totalRowWidth += builder.getCellFormat().getWidth();
		titleRowHeight = builder.getRowFormat().getHeight();
		builder.endRow();

		builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
		builder.getRowFormat().setHeight(120);
		builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
	}

	private static void generateContent(DocumentBuilder builder, QuestionVO question, String score) {
		final String optionAlpha = "ABCD";

		builder.insertCell();
		builder.getCellFormat().setWidth(scoreListCellWidth);
		builder.getCellFormat().setVerticalAlignment(VerticalAlignment.TOP);
		builder.getFont().setSize(10.5);
		builder.getFont().setBold(true);
		builder.writeln(contentNum + "、" + question.getTitle() + "（计" + question.getMaxScore() + "分）");
		builder.getFont().setBold(false);
		List<OptionDO> options = question.getOptions();
		for (int i = 0; i < options.size(); i++) {
			OptionDO option = options.get(i);

			builder.writeln(optionAlpha.charAt(i) + option.getContent() +
					"(" + option.getScore() + "分)");
		}

//		评分
		builder.insertCell();
		builder.getCellFormat().setWidth(40);
		builder.getFont().setSize(18);
		builder.getFont().setBold(true);


		builder.write(score);
		builder.endRow();
	}
}
